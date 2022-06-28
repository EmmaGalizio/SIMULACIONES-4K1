package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public abstract class Evento {

    protected float momentoEvento;
    protected float tiempoHastaEvento;

    //Agregar metodo procesar evento

    public void calcularMomentoEvento(float momentoActual, float presicion){
        presicion = (presicion >= 1) ? presicion : 4;
        momentoEvento = tiempoHastaEvento + momentoActual;
        momentoEvento = (float) truncar(momentoEvento, presicion);
    }

    public double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

}
