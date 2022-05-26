package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;

public abstract class Evento implements Comparable<Evento>{

    //Representa el momento en el que se va a producir el evento
    //Cada evento particular tendrá los campos necesarios para definir este atributo, pero
    //no deberá sobrescribir este ya que es el que se deberá usar para organizar
    //el heap de eventos
    protected float momentoEvento;

    abstract VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                            ParametrosGenerador parametrosGenerador,
                                            ParametrosCambioDistribucion parametrosCambioDistribucion,
                                            Pseudoaleatorio randomCUBase,
                                            IGeneradorRandom generadorRandom,
                                            ICambioDistribucion generadorVariableAleatoria);

    @Override
    /**
     * 10000000: Magic number, solo para multiplicar el momento de llegada por un valor lo suficientemente
     * grande como para que los decimales no sean tan relevantes para el calculo de cuál es menor
     */
    public int compareTo(Evento evento) {
        return (int)(momentoEvento*10000000) - (int)(evento.getMomentoEvento()*10000000);
    }

    public float getMomentoEvento() {
        return momentoEvento;
    }

    public void setMomentoEvento(float momentoEvento) {
        this.momentoEvento = momentoEvento;
    }
}
