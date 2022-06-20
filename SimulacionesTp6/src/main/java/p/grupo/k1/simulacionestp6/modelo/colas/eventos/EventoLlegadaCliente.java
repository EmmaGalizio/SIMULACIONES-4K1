package p.grupo.k1.simulacionestp6.modelo.colas.eventos;

import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.modelo.ParametrosCambioDistribucion;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.VaribaleAleatoria;
import p.grupo.k1.simulacionestp6.modelo.colas.Cliente;
import p.grupo.k1.simulacionestp6.modelo.colas.EstadoCliente;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.EstadoServidor;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.Servidor;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;
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
        vectorEstadoActual.validarEliminacionLlegadaAtaque(estadoAnterior);

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpLlegadasClientes());
        parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
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
        //En teoría así como está, si llega un cliente, automáticamente calcula la siguiente llegada
        //Se fija si hay algún servidor libre, si no hay ninguno, se fija si la cola es menor o igual a 15
        //Si lo es, lo agrega a la cola y a la lista de clientes, sino simplemente se descarta.
        if(vectorEstadoActual.bloqueoLlegadasActivo()){
            vectorEstadoActual.incrementarClientesNoAtendidos();
            vectorEstadoActual.setSiguientePseudoCU(randomCUBase);
            return vectorEstadoActual;
        }

        Servidor empleadoCasetaAtendiendo = this.obtenerEmpleadoLibre(vectorEstadoActual);

        if(empleadoCasetaAtendiendo == null){
            //El empleado está ocupado
            cliente.setEstado(EstadoCliente.getInstanceEsperandoCaseta());
            if((vectorEstadoActual.getCantClientesColaCaseta() < 15)) {
                vectorEstadoActual.agregarCliente(cliente);
                vectorEstadoActual.agregarClienteColaCaseta(cliente);
            }else{
                vectorEstadoActual.incrementarClientesNoAtendidos();
            }

        }else{
            //Hay por lo menos un empleado libre
            vectorEstadoActual.agregarCliente(cliente);
            cliente.setEstado(EstadoCliente.getInstanceAtencionCaseta());
            cliente.setHoraInicioAtencionCaseta(vectorEstadoActual.getReloj());
            cliente.setServidorActual(empleadoCasetaAtendiendo);
            empleadoCasetaAtendiendo.setEstado(EstadoServidor.getInstanceServidorOcupado());
            empleadoCasetaAtendiendo.setClienteActual(cliente);
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServCaseta());
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
            //vectorEstadoActual.acumularTiempoLibreEmpleadosCaseta(empleadoCasetaAtendiendo);
            heapEventos.add(eventoFinAtencionCaseta);
        }
        cliente.setNumeroCliente(vectorEstadoActual.getContadorVehiculos());
        vectorEstadoActual.setSiguientePseudoCU(randomCUBase);
        vectorEstadoActual.acumularTiempoLibreServidores(estadoAnterior);
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
