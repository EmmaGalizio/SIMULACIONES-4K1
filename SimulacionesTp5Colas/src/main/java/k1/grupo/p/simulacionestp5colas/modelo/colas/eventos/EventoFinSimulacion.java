package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;

public class EventoFinSimulacion extends Evento{

    public EventoFinSimulacion() {
        this.setNombreEvento("Fin Simulación");
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
        vectorEstadoActual.setReloj(this.momentoEvento);
        vectorEstadoActual.setNombreEvento(this.nombreEvento);
        this.actualizarAcumuladorTiempoLibre(vectorEstadoActual);
        //Faltaría ver si algún otro acumulador se tiene que actualizar, mepa que no

        return vectorEstadoActual;
    }
    private void actualizarAcumuladorTiempoLibre(VectorEstadoITV vectorEstadoITV){

        vectorEstadoITV.acumularTiempoLibreEmpleadosCaseta(null);
        vectorEstadoITV.acumularTiempoLibreEmpleadosNave(null);
        vectorEstadoITV.acumularTiempoLibreEmpleadosOficina(null);
    }
    //Cambio en un evento
    //Otro cambio en evento

    @Override
    public Object clone() {
        EventoFinSimulacion evento = new EventoFinSimulacion();
        evento.setMomentoEvento(super.getMomentoEvento());
        return evento;
    }
}
