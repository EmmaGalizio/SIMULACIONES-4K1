package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import lombok.Data;

@Data
public class EventoFinInspeccion extends Evento{

    private float randomFinInspeccion;
    private float tiempoFinInspeccion;

    @Override
    VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   ParametrosCambioDistribucion parametrosCambioDistribucion,
                                   Pseudoaleatorio randomCUBase, IGeneradorRandom generadorRandom,
                                   ICambioDistribucion generadorVariableAleatoria) {
        return null;
    }
}
