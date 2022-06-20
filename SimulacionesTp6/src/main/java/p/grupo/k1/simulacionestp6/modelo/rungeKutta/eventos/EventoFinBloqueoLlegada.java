package p.grupo.k1.simulacionestp6.modelo.rungeKutta.eventos;

import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.Evento;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoRungeKutta;

import java.util.LinkedList;
import java.util.List;

public class EventoFinBloqueoLlegada extends Evento {
    private float t0;
    private float duracionBloqueo;

    public EventoFinBloqueoLlegada(){
        this.nombreEvento = "Fin Bloqueo Llegadas";
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                          ParametrosGenerador parametrosGenerador,
                                          Pseudoaleatorio randomCUBase,
                                          IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                          ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV estadoActual = (VectorEstadoITV) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.setNombreEvento(this.nombreEvento);
        estadoActual.setLlegadaAtaque(null);

        List<ResultadoRungeKutta> ecDifSiguienteAtaque = new LinkedList<>();

        EventoLlegadaAtaque siguienteAtaque = new EventoLlegadaAtaque();
        randomCUBase = siguienteAtaque.calcularTiempoLlegadaSiguienteAtaque(randomCUBase,generadorRandom,
                parametrosGenerador, ecDifSiguienteAtaque);

        float tiempoHastaLlegada = siguienteAtaque.getTiempoHastaLlegada();
        //siguienteAtaque.setTiempoHastaLlegada((float)tiempoHastaLlegada);
        siguienteAtaque.setMomentoEvento((tiempoHastaLlegada + this.momentoEvento));
        siguienteAtaque.setRandomMomentoAtaque(randomCUBase.getRandom());
        heapEventos.add((EventoLlegadaAtaque)siguienteAtaque.clone());

        estadoActual.setFinBloqueoLlegada(null);
        estadoActual.setLlegadaAtaque(siguienteAtaque);
        estadoActual.acumularTiempoLibreServidores(estadoAnterior);
        //La llegada del proximo evento se va a calcular acá


        estadoActual.setSiguientePseudoCU(randomCUBase);
        return estadoActual;
    }

    @Override
    public Object clone(){
        EventoFinBloqueoLlegada nuevoEvento = new EventoFinBloqueoLlegada();
        nuevoEvento.setNombreEvento(this.getNombreEvento());
        nuevoEvento.setMomentoEvento(this.getMomentoEvento());
        nuevoEvento.setDuracionBloqueo(this.getDuracionBloqueo());
        return nuevoEvento;
    }

    public float getT0() {
        return t0;
    }

    public void setT0(float t0) {
        this.t0 = t0;
    }

    public float getDuracionBloqueo() {
        return duracionBloqueo;
    }

    public void setDuracionBloqueo(float duracionBloqueo) {
        this.duracionBloqueo = duracionBloqueo;
    }

    /***
     * Permite que cada vez que se dispare un evento del tipo EventoLlegadaAtaque, se calcule si el ataque
     * será a las llegadas o al servidor, una vez calculado se crea la instancia correspondiente y se utiliza
     * este método para calcular la ecuación diferencial que determina su duración.
     * @param presicion
     * @return Una lista en donde cada elemento representa una fila de la tabla del método de
     * Runge Kutta de cuarto orden.
     */
    public List<ResultadoRungeKutta> calcularFinEvento(float presicion) {


        //NO ANDA ESTA ECUACION PORONGA!!!!!!!!!!!!

        double Lm;
        double h = 0.01;
        h = truncar(h,2);
        double t = 0;
        double k1,k2,k3,k4;
        double Lmp1 = this.t0;
        Lmp1 = truncar(Lmp1, presicion);

        //DL/dt = -(L/(0.8f*t²))-L
        List<ResultadoRungeKutta> ecDiferencial = new LinkedList<>();
        ResultadoRungeKutta resultadoRungeKutta;

        do{
            Lm = Lmp1;
            Lm = truncar(Lm, presicion);
            resultadoRungeKutta = new ResultadoRungeKutta();
            resultadoRungeKutta.setXm(t);
            resultadoRungeKutta.setYm(Lm);

            double tsq = t*t;
            tsq = truncar(tsq,presicion); //t square
            k1 = -((Lm/0.8)*tsq) - Lm;
            k1 = truncar(k1, presicion);
            resultadoRungeKutta.setK1(k1);
            tsq = (t+(h/2))* (t+(h/2));
            tsq = truncar(tsq,presicion);
            k2 = -(((Lm+ (0.5*h*k1))/0.8)*tsq) - (Lm+ (0.5*h*k1));
            k2 = truncar(k2,presicion);
            k3 = -(((Lm+ (0.5*h*k2))/0.8)*tsq) - (Lm+ (0.5*h*k2));
            k3 = truncar(k3,presicion);
            tsq = (t+(h))* (t+(h));
            tsq = truncar(tsq,presicion);
            k4 = -(((Lm+ (h*k3))/0.8)*tsq) - (Lm+ (h*k3));
            k4 = truncar(k4, presicion);
            resultadoRungeKutta.setK2(k2);
            resultadoRungeKutta.setK3(k3);
            resultadoRungeKutta.setK4(k4);
            Lmp1 = Lm + (h/6)*(k1+ (2*k2)+(2*k3)+k4);
            Lmp1 = truncar(Lmp1,presicion);
            resultadoRungeKutta.setYmp1(Lmp1);
            t = t+h;
            resultadoRungeKutta.setXmp1(t);

            ecDiferencial.add(resultadoRungeKutta);
        }while(Math.abs(Lm - Lmp1) >= 1);

        t = ecDiferencial.get(ecDiferencial.size()-1).getXm();
        this.duracionBloqueo = (float)t*5;
        this.momentoEvento = (float)(this.t0 + (t*5));
        this.momentoEvento = (float)truncar(this.momentoEvento, presicion);

        return  ecDiferencial;
    }
    private double truncar(double f, float presicion){
        double multiplicador = Math.pow(10, presicion);
        int aux = (int)(f * multiplicador);
        //return (double)aux / multiplicador;
        return Math.round(f*multiplicador)/multiplicador;
    }
}
