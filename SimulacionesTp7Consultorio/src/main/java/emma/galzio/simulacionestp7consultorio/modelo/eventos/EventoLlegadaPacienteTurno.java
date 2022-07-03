package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.EstadoCliente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteTurno;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
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
        estadoActual.incrementarLlegadasTurno();
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);

        EventoLlegadaPacienteTurno siguienteLlegada = calcularSiguienteLlegada(estadoActual,
                parametrosConsultorio, generadoresVariableAleatoria);
        estadoActual.setLlegadaPacienteTurno(siguienteLlegada);
        heapEventos.add(siguienteLlegada);

        if(estadoActual.llegadaEstudioPospuesta() && estadoActual.llegadaTurnoPospuesta()
                && estadoActual.getSecretaria().estaLibre() && estadoActual.getTecnico().estaLibre()){
            EventoFinJornada eventoFinJornada = new EventoFinJornada();
            float momentoFinJornada = estadoActual.getMomentoInicioJornada() + (13*60.0f);
            momentoFinJornada = (float)truncar(momentoFinJornada,
                    parametrosConsultorio.getParametrosSecretaria().getPrecision());
            eventoFinJornada.setMomentoEvento(momentoFinJornada);
            estadoActual.setFinJornada(eventoFinJornada);
            heapEventos.add(eventoFinJornada);
        }

        if(estadoActual.getCantLlegadasTurno() >= parametrosConsultorio.getTurnosDisponiblesDiario()){
            return estadoActual;
        }
        //Generación del evento de fin de atención de secretaria si corresponde
        PacienteTurno pacienteTurno = new PacienteTurno();
        pacienteTurno.setId(estadoActual.getCantLlegadasTurno());
        pacienteTurno.setMomentoLlegada(this.momentoEvento);

        estadoActual.agregarPaciente(pacienteTurno);

        if(!estadoActual.getSecretaria().estaLibre()){
            pacienteTurno.setEstado(EstadoCliente.getInstanceEsperandoSecretaria());
            estadoActual.agregarPacienteColaSecretaria(pacienteTurno);
        }else{
            pacienteTurno.setEstado(EstadoCliente.getInstanceAtencionSecretaria());
            estadoActual.getSecretaria().ocupar();

            EventoFinAtencionSecretaria finAtencionSecretaria = calcularFinAtencionSecretaria(parametrosConsultorio,
                                                                generadoresVariableAleatoria);
            finAtencionSecretaria.setPaciente(pacienteTurno);
            estadoActual.setFinAtencionSecretaria(finAtencionSecretaria);
            heapEventos.add(finAtencionSecretaria);
        }
        return estadoActual;
    }

    private EventoLlegadaPacienteTurno calcularSiguienteLlegada(VectorEstadoClinica estadoActual,
                                                                ParametrosConsultorio parametrosConsultorio,
                                                                Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        int presicion = parametrosConsultorio.getParametrosSecretaria().getPrecision();

        EventoLlegadaPacienteTurno siguienteLlegada = new EventoLlegadaPacienteTurno();
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosConsultorio.getLambdaLlegadaTurno());
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.EXP_NEG);
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosLlegadaTurno(),
                parametrosConsultorio.getRandomBaseCULlegadaTurno());
        parametrosConsultorio.setRandomBaseCULlegadaTurno(variableAleatoria.getSiguienteRandomBase());
        float tiempoLlegada = variableAleatoria.getRandomGenerado();
        float momentoLlegada = this.momentoEvento + tiempoLlegada;
        momentoLlegada = (float) truncar(momentoLlegada, presicion);
        float finalDia = (estadoActual.getDia()-1)*24*60.0f + (13*60.0f);
        if(momentoLlegada >= finalDia){
            float inicioSiguienteDia = estadoActual.getDia()*24*60.0f + (60*8.0f);
            inicioSiguienteDia = (float)truncar(inicioSiguienteDia, presicion);
            momentoLlegada = inicioSiguienteDia + tiempoLlegada;
            momentoLlegada = (float) truncar(momentoLlegada, presicion);
        }
        siguienteLlegada.setTiempoHastaEvento(tiempoLlegada);
        siguienteLlegada.setMomentoEvento(momentoLlegada);
        siguienteLlegada.setRandomLlegadaPacienteTurno(variableAleatoria.getSiguienteRandomBase().getRandom());
        return siguienteLlegada;
    }

    public double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }
}
