package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.VaribaleAleatoria;
import k1.grupo.p.simulacionestp5colas.modelo.colas.Cliente;
import k1.grupo.p.simulacionestp5colas.modelo.colas.EstadoCliente;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EstadoServidor;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

@Data
public class EventoLlegadaCliente extends Evento{

    private Pseudoaleatorio randomProxLlegada;
    private float tiempoHastaProxLlegada;

    public EventoLlegadaCliente() {
        this.setNombreEvento("Llegada Cliente");
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   Pseudoaleatorio randomCUBase,
                                   IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                   ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV vectorEstadoActual = (VectorEstadoITV) estadoAnterior.clone();
        vectorEstadoActual.setNombreEvento(this.nombreEvento);
        vectorEstadoActual.setReloj(momentoEvento);
        vectorEstadoActual.incremetarLlegadaVehiculos();

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpLlegadasClientes());
        VaribaleAleatoria tiempoProximaLlegada = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion
                                                                                            ,parametrosGenerador,randomCUBase);
        randomCUBase = tiempoProximaLlegada.getSiguienteRandomBase();
        //---------------------------------------------------------------
        EventoLlegadaCliente proximaLlegada = new EventoLlegadaCliente();
        proximaLlegada.setRandomProxLlegada(randomCUBase);
        proximaLlegada.setTiempoHastaProxLlegada(tiempoProximaLlegada.getRandomGenerado());
        proximaLlegada.setMomentoEvento(vectorEstadoActual.getReloj()+proximaLlegada.getTiempoHastaProxLlegada());
        //En este caso se debe actualizar la referencia al objeto correspondiente al siguiente evento de llegada en el vector de estado
        vectorEstadoActual.setProximaLlegadaCliente(proximaLlegada);
        heapEventos.add(proximaLlegada);
        //--------------------------------------------------------------------------------
        Cliente cliente = new Cliente();
        cliente.setHoraLlegadaCaseta(vectorEstadoActual.getReloj());
        vectorEstadoActual.agregarCliente(cliente);
        cliente.setNumeroCliente(vectorEstadoActual.getContadorVehiculos());

        Servidor empleadoCasetaAtendiendo = this.obtenerEmpleadoLibre(vectorEstadoActual);

        if(empleadoCasetaAtendiendo == null){
            //El empleado está ocupado
            cliente.setEstado(EstadoCliente.getInstanceEsperandoCaseta());
            vectorEstadoActual.agregarClienteColaCaseta(cliente);

        }else{
            //Hay por lo menos un empleado libre
            cliente.setEstado(EstadoCliente.getInstanceAtencionCaseta());
            cliente.setHoraInicioAtencionCaseta(vectorEstadoActual.getReloj());
            cliente.setServidorActual(empleadoCasetaAtendiendo);
            empleadoCasetaAtendiendo.setEstado(EstadoServidor.getInstanceServidorOcupado());
            empleadoCasetaAtendiendo.setClienteActual(cliente);
            //El random unif 0-1 se actualizo arriba
            VaribaleAleatoria tiempoAtencionCaseta = generadorVariableAleatoria
                    .siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            randomCUBase = tiempoAtencionCaseta.getSiguienteRandomBase();
            EventoFinAtencionCaseta eventoFinAtencionCaseta = new EventoFinAtencionCaseta();
            eventoFinAtencionCaseta.setRandomTiempoAtencion(randomCUBase);
            eventoFinAtencionCaseta.setTiempoAtencion(tiempoAtencionCaseta.getRandomGenerado());
            eventoFinAtencionCaseta.setMomentoEvento(vectorEstadoActual.getReloj()+tiempoAtencionCaseta.getRandomGenerado());
            eventoFinAtencionCaseta.setCliente(cliente);
            //Se actualiza el evento de fin de atención de caseta del vector de estado correspondiente al empleado que está
            //atendiendo al cliente. Es decir, si el empleado tiene un id = i, se actualiza el elemento i-1 del vector de eventos
            //este vector de eventos es el que se va a mostrar.
            vectorEstadoActual.actualizarEventoFinAtencionCaseta(eventoFinAtencionCaseta,empleadoCasetaAtendiendo);
            vectorEstadoActual.acumularTiempoLibreEmpleadosCaseta(empleadoCasetaAtendiendo);
            heapEventos.add(eventoFinAtencionCaseta);
        }
        vectorEstadoActual.setSiguientePseudoCU(randomCUBase);
        return vectorEstadoActual;
    }


    private Servidor obtenerEmpleadoLibre(VectorEstadoITV vectorEstadoITV){

        for(Servidor empleadoCaseta:vectorEstadoITV.getEmpleadosCaseta()){
            if(empleadoCaseta.estaLibre()) return empleadoCaseta;
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoLlegadaCliente evento = new EventoLlegadaCliente();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomProxLlegada((Pseudoaleatorio) randomProxLlegada.clone());
        evento.setTiempoHastaProxLlegada(tiempoHastaProxLlegada);
        return evento;
    }
}
