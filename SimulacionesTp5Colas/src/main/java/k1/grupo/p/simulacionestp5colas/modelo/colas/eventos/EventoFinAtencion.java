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
public class EventoFinAtencion extends Evento{

    //No utiliza un random porque creo
    private Pseudoaleatorio randomAtencionOficina;
    private float tiempoAtencionOficina;
    private Cliente clienteAtencionFinalizada;

    public EventoFinAtencion() {
        this.setNombreEvento("Fin At. Oficina");
    }

    //Este evento representa la salida del cliente de la oficina
    //Cuando se saca un evento del heap en el controlador se debe controlar
    //si corresponde con este tipo de evento, de ser así se debe activar una bandera
    //que indique que al final del procesamiento del siguiente evento se debe realizar el
    //mantenimiento de la lista de clientes.

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                          ParametrosGenerador parametrosGenerador,
                                          Pseudoaleatorio randomCUBase,
                                          IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                          ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV vectorEstadoActual = (VectorEstadoITV) estadoAnterior.clone();
        vectorEstadoActual.setReloj(this.momentoEvento);
        vectorEstadoActual.setNombreEvento(this.nombreEvento);
        Servidor oficinistaAnterior = cliente.getServidorActual();
        Cliente clienteActual = vectorEstadoActual.buscarClientePorId(cliente.getNumeroCliente());
        Servidor oficinista = vectorEstadoActual.buscarOficinistaPorId(oficinistaAnterior.getId());

        this.setClienteAtencionFinalizada(clienteActual);

        Cliente siguienteClienteCola = vectorEstadoActual.getSiguienteClienteColaOficina();
        if(siguienteClienteCola == null){
            oficinista.setEstado(EstadoServidor.getInstanceServidorLibre());
            oficinista.setMomentoLiberacion(this.momentoEvento);
            oficinista.setClienteActual(null);
            vectorEstadoActual.actualizarEventoFinAtencion(null,oficinista);
        } else{
            siguienteClienteCola = vectorEstadoActual.buscarClientePorId(siguienteClienteCola.getNumeroCliente());
            siguienteClienteCola.setEstado(EstadoCliente.getInstanceAtencionOficina());
            siguienteClienteCola.setHoraInicioAtencionOficina(this.momentoEvento);
            siguienteClienteCola.setServidorActual(oficinista);

            oficinista.setClienteActual(siguienteClienteCola);
            oficinista.setEstado(EstadoServidor.getInstanceServidorOcupado());

            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioOficina());
            parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());

            VaribaleAleatoria tiempoFinAtencionOficina = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            EventoFinAtencion eventoFinAtencion = new EventoFinAtencion();
            eventoFinAtencion.setRandomAtencionOficina(randomCUBase);
            eventoFinAtencion.setTiempoAtencionOficina(tiempoFinAtencionOficina.getRandomGenerado());
            eventoFinAtencion.setMomentoEvento(this.momentoEvento+eventoFinAtencion.getTiempoAtencionOficina());
            eventoFinAtencion.setCliente(siguienteClienteCola);
            heapEventos.add(eventoFinAtencion);

            vectorEstadoActual.actualizarEventoFinAtencion(eventoFinAtencion, oficinista);
            vectorEstadoActual.acumularTiempoColaOficina(siguienteClienteCola);
            vectorEstadoActual.acumularTiempoEsperaCola(siguienteClienteCola.getHoraLlegadaOficina());
            randomCUBase = tiempoFinAtencionOficina.getSiguienteRandomBase();
        }

        //El cliente directamente sale del sistema, así que no pasa a otra cola u otro servidor como en los demás eventos.
        vectorEstadoActual.acumularTiempoEsperaOficina(clienteActual);
        vectorEstadoActual.incremetarVehiculosAtencionFinalizada();
        vectorEstadoActual.acumularTiempoEnSistema(clienteActual);

        vectorEstadoActual.setSiguientePseudoCU(randomCUBase);
        return vectorEstadoActual;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinAtencion evento = new EventoFinAtencion();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomAtencionOficina((Pseudoaleatorio) randomAtencionOficina.clone());
        evento.setTiempoAtencionOficina(tiempoAtencionOficina);
        return evento;
    }

}
