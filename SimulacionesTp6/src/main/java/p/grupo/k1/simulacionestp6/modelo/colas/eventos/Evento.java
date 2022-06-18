package p.grupo.k1.simulacionestp6.modelo.colas.eventos;

import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.colas.Cliente;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;

public abstract class Evento implements Comparable<Evento>{

    //Representa el momento en el que se va a producir el evento
    //Cada evento particular tendrá los campos necesarios para definir este atributo, pero
    //no deberá sobrescribir este ya que es el que se deberá usar para organizar
    //el heap de eventos
    protected float momentoEvento;
    protected String nombreEvento;
    protected Cliente cliente;

    public abstract VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                                   ParametrosGenerador parametrosGenerador,
                                                   Pseudoaleatorio randomCUBase,
                                                   IGeneradorRandom generadorRandom,
                                                    ParametrosItv parametrosItv,
                                                   ICambioDistribucion generadorVariableAleatoria,
                                                   TSBHeap<Evento> heapEventos);

    @Override
    /**
     * 10000000: Magic number, solo para multiplicar el momento de llegada por un valor lo suficientemente
     * grande como para que los decimales no sean tan relevantes para el calculo de cuál es menor
     */
    public int compareTo(Evento evento) {
        return Float.compare(momentoEvento,evento.getMomentoEvento());

    }

    public float getMomentoEvento() {
        return momentoEvento;
    }

    public void setMomentoEvento(float momentoEvento) {
        this.momentoEvento = momentoEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
