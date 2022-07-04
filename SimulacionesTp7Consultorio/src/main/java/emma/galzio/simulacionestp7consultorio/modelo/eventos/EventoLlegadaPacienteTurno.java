package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.EstadoCliente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteTurno;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import lombok.Data;

import java.util.Map;

@Data
public class EventoLlegadaPacienteTurno extends Evento{

    private float randomLlegadaPacienteTurno;

    public EventoLlegadaPacienteTurno(){
        this.nombreEvento = "Llegada Paciente s/Turno";
    }

    @Override
    public VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior, IGeneradorRandom generadorRandom,
                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                              ParametrosConsultorio parametrosConsultorio,
                                              TSBHeap<Evento> heapEventos) {

        VectorEstadoClinica estadoActual = (VectorEstadoClinica) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.calcularDiaYMinutos();
        estadoActual.setNombreEvento(this.nombreEvento);
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);
        estadoActual.incrementarLlegadasTurno();

        EventoLlegadaPacienteTurno siguienteLlegada = calcularSiguienteLlegada(estadoActual,
                parametrosConsultorio, generadoresVariableAleatoria);
        estadoActual.setLlegadaPacienteTurno(siguienteLlegada);
        heapEventos.add(siguienteLlegada);

        //if(estadoActual.getCantLlegadasTurnoDiaActual() > parametrosConsultorio.getTurnosDisponiblesDiario()){
        //    return estadoActual;
        //}

        if(!(estadoActual.getCantLlegadasTurnoDiaActual() > parametrosConsultorio.getTurnosDisponiblesDiario())) {
            //Generación del evento de fin de atención de secretaria si corresponde
            PacienteTurno pacienteTurno = new PacienteTurno();
            pacienteTurno.setId(estadoActual.getCantLlegadasTurno());
            pacienteTurno.setMomentoLlegada(this.momentoEvento);

            estadoActual.agregarPaciente(pacienteTurno);

            if (!estadoActual.getSecretaria().estaLibre()) {
                pacienteTurno.setEstado(EstadoCliente.getInstanceEsperandoSecretaria());
                estadoActual.agregarPacienteColaSecretaria(pacienteTurno);
            } else {
                pacienteTurno.setEstado(EstadoCliente.getInstanceAtencionSecretaria());
                estadoActual.getSecretaria().ocupar();
                estadoActual.getSecretaria().setPacienteActual(pacienteTurno);

                EventoFinAtencionSecretaria finAtencionSecretaria = calcularFinAtencionSecretaria(parametrosConsultorio,
                        generadoresVariableAleatoria);
                finAtencionSecretaria.setPaciente(pacienteTurno);
                estadoActual.setFinAtencionSecretaria(finAtencionSecretaria);
                heapEventos.add(finAtencionSecretaria);
            }
        }
        if(estadoActual.llegadaEstudioPospuesta() && estadoActual.llegadaTurnoPospuesta()
                && estadoActual.getSecretaria().estaLibre() && estadoActual.getTecnico().estaLibre()){
            EventoFinJornada eventoFinJornada = new EventoFinJornada();
            double momentoFinJornada = estadoActual.getMomentoInicioJornada() + (5*60.0f);
            //momentoFinJornada = (float)truncar(momentoFinJornada,
            //        parametrosConsultorio.getParametrosSecretaria().getPrecision());
            momentoFinJornada = CommonFunc.round(momentoFinJornada,4);
            eventoFinJornada.setMomentoEvento(momentoFinJornada);
            estadoActual.setFinJornada(eventoFinJornada);
            heapEventos.add(eventoFinJornada);
        }
        return estadoActual;
    }

    private EventoLlegadaPacienteTurno calcularSiguienteLlegada(VectorEstadoClinica estadoActual,
                                                                ParametrosConsultorio parametrosConsultorio,
                                                                Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        int presicion = parametrosConsultorio.getParametrosSecretaria().getPrecision();

        EventoLlegadaPacienteTurno siguienteLlegada = new EventoLlegadaPacienteTurno();
        siguienteLlegada.setRandomLlegadaPacienteTurno(parametrosConsultorio.getRandomBaseCULlegadaTurno().getRandom());

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosConsultorio.getLambdaLlegadaTurno());
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.EXP_NEG);
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosLlegadaTurno(),
                parametrosConsultorio.getRandomBaseCULlegadaTurno());
        parametrosConsultorio.setRandomBaseCULlegadaTurno(variableAleatoria.getSiguienteRandomBase());
        double tiempoLlegada = variableAleatoria.getRandomGenerado();
        tiempoLlegada = CommonFunc.round(tiempoLlegada, 4);
        double momentoLlegada = this.momentoEvento + tiempoLlegada;
        //momentoLlegada = (float) truncar(momentoLlegada, 4);
        float finalDia = (estadoActual.getDia()-1)*24*60.0f + (13*60.0f);
        if(momentoLlegada >= finalDia){
            double inicioSiguienteDia = estadoActual.getDia()*24*60.0f + (60*8.0f);
            //inicioSiguienteDia = (float)truncar(inicioSiguienteDia, 4);
            inicioSiguienteDia = CommonFunc.round(inicioSiguienteDia,4);
            momentoLlegada = inicioSiguienteDia + tiempoLlegada;
            //momentoLlegada = (float) truncar(momentoLlegada, 4);
        }
        //momentoLlegada = (float) truncar(momentoLlegada, 4);
        momentoLlegada = CommonFunc.round(momentoLlegada, 4);
        siguienteLlegada.setTiempoHastaEvento(tiempoLlegada);
        siguienteLlegada.setMomentoEvento(momentoLlegada);

        return siguienteLlegada;
    }

    public double truncar(double f, float precision){
        double multiplicador = (int)Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }
}
