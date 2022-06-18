package p.grupo.k1.simulacionestp6.modelo.colas.eventos;

import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;

import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;

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
