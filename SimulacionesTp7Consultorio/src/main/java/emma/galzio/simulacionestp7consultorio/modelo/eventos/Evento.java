package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter@Setter
public abstract class Evento implements Comparable<Evento> {

    protected float momentoEvento;
    protected float tiempoHastaEvento;
    //Con el paciente es suficiente, no hace falta una referencia al servidor acá, ni en Paciente porque
    //El servidor que está atendiendo al paciente depende del evento que se está procesando.
    protected Paciente paciente;
    protected String nombreEvento;


    public abstract VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                                       IGeneradorRandom generadorRandom,
                                                       Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                                       ParametrosConsultorio parametrosConsultorio,
                                                       TSBHeap<Evento> heapEventos);

    public void calcularMomentoEvento(float momentoActual, float presicion){
        presicion = (presicion >= 1) ? presicion : 4;
        momentoEvento = tiempoHastaEvento + momentoActual;
        momentoEvento = (float) truncar(momentoEvento, presicion);
    }

    public double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

    @Override
    public int compareTo(Evento evento) {
        return Float.compare(momentoEvento, evento.getMomentoEvento());
    }

    protected final EventoFinAtencionSecretaria calcularFinAtencionSecretaria(ParametrosConsultorio parametrosConsultorio,
                                                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        EventoFinAtencionSecretaria finAtencionSecretaria = new EventoFinAtencionSecretaria();

        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.UNIFORME);
        float a = parametrosConsultorio.getUnifASecretaria();
        float b = parametrosConsultorio.getUnifBSecretaria();
        Pseudoaleatorio randomBase = parametrosConsultorio.getRandomBaseCUSecretaria();
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setUnifA(a);
        parametrosCambioDistribucion.setUnifB(b);
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosSecretaria(),
                randomBase);

        parametrosConsultorio.setRandomBaseCUSecretaria(variableAleatoria.getSiguienteRandomBase());
        float tiempoAtencion = variableAleatoria.getRandomGenerado();
        finAtencionSecretaria.setTiempoHastaEvento(tiempoAtencion);
        finAtencionSecretaria.calcularMomentoEvento(this.momentoEvento,
                                            parametrosConsultorio.getParametrosSecretaria().getPrecision());

        return finAtencionSecretaria;
    }

    protected final EventoFinEstudio calcularFinEstudio(ParametrosConsultorio parametrosConsultorio,
                                                        Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        EventoFinEstudio finEstudio = new EventoFinEstudio();


        int precision = parametrosConsultorio.getParametrosTecnico().getPrecision();

        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.UNIFORME);
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setUnifA(parametrosConsultorio.getUnifATecnico());
        parametrosCambioDistribucion.setUnifB(parametrosConsultorio.getUnifBTecnico());
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosTecnico().getPrecision());

        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosTecnico(),
                parametrosConsultorio.getRandomBaseCUTecnico());

        parametrosConsultorio.setRandomBaseCUTecnico(variableAleatoria.getSiguienteRandomBase());
        float tiempoAtencion = variableAleatoria.getRandomGenerado();
        float momentoFinAtencion = tiempoAtencion + this.momentoEvento;
        momentoFinAtencion = (float)truncar(momentoFinAtencion, precision);
        finEstudio.setRandomFinEstudio(variableAleatoria.getSiguienteRandomBase().getRandom());
        finEstudio.setTiempoHastaEvento(tiempoAtencion);
        finEstudio.setMomentoEvento(momentoFinAtencion);

        return finEstudio;

    }

}
