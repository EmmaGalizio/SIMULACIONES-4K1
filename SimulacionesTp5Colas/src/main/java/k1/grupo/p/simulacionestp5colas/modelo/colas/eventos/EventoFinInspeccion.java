package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

@Data
public class EventoFinInspeccion extends Evento{

    private Pseudoaleatorio randomFinInspeccion;
    private float tiempoFinInspeccion;

    public EventoFinInspeccion() {
        this.setNombreEvento("Fin Inspección");
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   Pseudoaleatorio randomCUBase, IGeneradorRandom generadorRandom,
                                   ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinInspeccion evento = new EventoFinInspeccion();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomFinInspeccion((Pseudoaleatorio) randomFinInspeccion.clone());
        evento.setTiempoFinInspeccion(tiempoFinInspeccion);
        return evento;
    }
}
