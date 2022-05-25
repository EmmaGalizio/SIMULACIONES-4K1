package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

public abstract class Evento implements Comparable<Evento>{

    protected float momentoEvento;

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
