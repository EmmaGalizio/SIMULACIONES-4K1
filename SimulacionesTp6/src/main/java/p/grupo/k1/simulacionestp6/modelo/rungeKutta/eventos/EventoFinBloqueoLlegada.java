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

        List<ResultadoRungeKutta> ecDifSiguienteAtaque = new LinkedList<>();
        randomCUBase = EventoLlegadaAtaque.calcularLlegadaSiguienteAtaque(randomCUBase,generadorRandom,
                                                parametrosGenerador, ecDifSiguienteAtaque);

        ResultadoRungeKutta resultadoRungeKutta = ecDifSiguienteAtaque
                                                    .get(ecDifSiguienteAtaque.size()-1);

        float tiempoHastaLlegada = resultadoRungeKutta.getXm() * 9;
        EventoLlegadaAtaque siguienteAtaque = new EventoLlegadaAtaque(tiempoHastaLlegada + this.momentoEvento);
        siguienteAtaque.setTiempoHastaLlegada(tiempoHastaLlegada);
        heapEventos.add(siguienteAtaque);

        estadoActual.setFinBloqueoLlegada(null);
        estadoActual.setLlegadaAtaque(siguienteAtaque);

        //La llegada del proximo evento se va a calcular acá


        estadoActual.setSiguientePseudoCU(randomCUBase);
        return estadoActual;
    }

    @Override
    public Object clone(){
        EventoFinBloqueoLlegada nuevoEvento = new EventoFinBloqueoLlegada();
        nuevoEvento.setNombreEvento(this.getNombreEvento());
        nuevoEvento.setMomentoEvento(this.getMomentoEvento());
        return nuevoEvento;
    }

    public float getT0() {
        return t0;
    }

    public void setT0(float t0) {
        this.t0 = t0;
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

        float Lm;
        float h = 0.01f;
        float t = 0;
        float k1,k2,k3,k4;
        float Lmp1 = this.t0;

        //DL/dt = -(L/(0.8f*t²))-L
        List<ResultadoRungeKutta> ecDiferencial = new LinkedList<>();
        ResultadoRungeKutta resultadoRungeKutta;

        do{
            Lm = Lmp1;

            resultadoRungeKutta = new ResultadoRungeKutta();
            resultadoRungeKutta.setXm(t);
            resultadoRungeKutta.setYm(Lm);

            float tsq = t*t;
            tsq = truncar(tsq,presicion); //t square
            k1 = -(Lm/(0.8f*tsq)) - Lm;
            k1 = truncar(k1, presicion);
            resultadoRungeKutta.setK1(k1);
            tsq = (t+(h/2))* (t+(h/2));
            tsq = truncar(tsq,presicion);
            k2 = -((Lm+ (0.5f*h*k1))/(0.8f*tsq)) - (Lm+ (0.5f*h*k1));
            k2 = truncar(k2,presicion);
            k3 = -((Lm+ (0.5f*h*k2))/(0.8f*tsq)) - (Lm+ (0.5f*h*k2));
            k3 = truncar(k3,presicion);
            tsq = (t+(h))* (t+(h));
            tsq = truncar(tsq,presicion);
            k4 = -((Lm+ (h*k3))/(0.8f*tsq)) - (Lm+ (h*k3));
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
        this.momentoEvento = this.t0 + (t*5);
        this.momentoEvento = truncar(this.momentoEvento, presicion);

        return  ecDiferencial;
    }
    private float truncar(float f, float presicion){
        int multiplicador = (int)Math.pow(10, presicion);
        int aux = (int)(f * multiplicador);
        return (float)aux / multiplicador;
    }
}
