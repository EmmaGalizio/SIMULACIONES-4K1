package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter@Setter
public abstract class Evento implements Comparable<Evento> {

    protected double momentoEvento;
    protected double tiempoHastaEvento;
    //Con el paciente es suficiente, no hace falta una referencia al servidor acá, ni en Paciente porque
    //El servidor que está atendiendo al paciente depende del evento que se está procesando.
    protected Paciente paciente;
    protected String nombreEvento;


    public abstract VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                                       IGeneradorRandom generadorRandom,
                                                       Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                                       ParametrosConsultorio parametrosConsultorio,
                                                       TSBHeap<Evento> heapEventos);

    public void calcularMomentoEvento(double momentoActual, float presicion){
        presicion = (presicion >= 1) ? presicion : 4;
        momentoEvento = tiempoHastaEvento + momentoActual;
        //momentoEvento = (float) truncar(momentoEvento, 4);
        momentoEvento =  CommonFunc.round(momentoEvento,4);
    }

    public double truncar(double f, float precision){
        double multiplicador = (int)Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

    @Override
    public int compareTo(Evento evento) {
        return Double.compare(momentoEvento, evento.getMomentoEvento());
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
        finAtencionSecretaria.setRandomAtencionSecretaria(randomBase.getRandom());
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosSecretaria(), randomBase);

        parametrosConsultorio.setRandomBaseCUSecretaria(variableAleatoria.getSiguienteRandomBase());
        double tiempoAtencion = variableAleatoria.getRandomGenerado();
        tiempoAtencion = CommonFunc.round(tiempoAtencion,4);
        finAtencionSecretaria.setTiempoHastaEvento(tiempoAtencion);
        finAtencionSecretaria.calcularMomentoEvento(this.momentoEvento,
                                            parametrosConsultorio.getParametrosSecretaria().getPrecision());

        return finAtencionSecretaria;
    }

    protected final EventoFinEstudio calcularFinEstudio(ParametrosConsultorio parametrosConsultorio,
                                                        Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        EventoFinEstudio finEstudio = new EventoFinEstudio();


        int precision = 4;

        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.NORMAL_CONVOLUCION);
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setMedia(parametrosConsultorio.getMediaAtTecnico());
        parametrosCambioDistribucion.setDesvEst(parametrosConsultorio.getDesvEstTecnico());
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosTecnico().getPrecision());
        finEstudio.setRandomFinEstudio(parametrosConsultorio.getRandomBaseCUTecnico().getRandom());
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosTecnico(),
                parametrosConsultorio.getRandomBaseCUTecnico());

        parametrosConsultorio.setRandomBaseCUTecnico(variableAleatoria.getSiguienteRandomBase());
        double tiempoAtencion = variableAleatoria.getRandomGenerado();
        tiempoAtencion = Math.abs(tiempoAtencion);
        tiempoAtencion = CommonFunc.round(tiempoAtencion,4);
        double momentoFinAtencion = tiempoAtencion + this.momentoEvento;
        //momentoFinAtencion = (float)truncar(momentoFinAtencion, precision);
        momentoFinAtencion = CommonFunc.round(momentoFinAtencion,4);
        finEstudio.setTiempoHastaEvento(tiempoAtencion);
        finEstudio.setMomentoEvento(momentoFinAtencion);

        return finEstudio;

    }

}
