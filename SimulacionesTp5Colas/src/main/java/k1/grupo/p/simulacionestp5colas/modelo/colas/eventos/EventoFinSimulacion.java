package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
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
                                   ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinSimulacion evento = new EventoFinSimulacion();
        evento.setMomentoEvento(super.getMomentoEvento());
        return evento;
    }
}
