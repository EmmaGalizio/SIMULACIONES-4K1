package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.EstadoCliente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

import java.util.Map;

@Data
public class EventoLlegadaPacienteEstudio extends Evento{

    private float randomLlegadaPacienteEstudio;
    @Override
    public VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                              IGeneradorRandom generadorRandom,
                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                              ParametrosConsultorio parametrosConsultorio,
                                              TSBHeap<Evento> heapEventos) {
        VectorEstadoClinica estadoActual = (VectorEstadoClinica) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.calcularDiaYMinutos();
        estadoActual.setNombreEvento("Llegada Paciente c/Turno");
        estadoActual.incrementerLlegadasEstudio();
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);

        EventoLlegadaPacienteEstudio siguienteLlegada = calcularSiguienteLlegada(estadoActual,
                parametrosConsultorio, generadoresVariableAleatoria);

        estadoActual.setLlegadaPacienteEstudio(siguienteLlegada);
        heapEventos.add(siguienteLlegada);


        PacienteEstudio pacienteEstudio = new PacienteEstudio();
        pacienteEstudio.setMomentoLlegada(this.momentoEvento);
        pacienteEstudio.setId(estadoActual.getCantLlegadasEstudio());
        estadoActual.agregarPaciente(pacienteEstudio);

        if(!estadoActual.getSecretaria().estaLibre()){
            pacienteEstudio.setEstado(EstadoCliente.getInstanceEsperandoSecretaria());
            estadoActual.agregarPacienteColaSecretaria(pacienteEstudio);
        } else{
            pacienteEstudio.setEstado(EstadoCliente.getInstanceAtencionSecretaria());
            estadoActual.getSecretaria().ocupar();
            estadoActual.getSecretaria().setPacienteActual(pacienteEstudio);

            EventoFinAtencionSecretaria finAtencionSecretaria = calcularFinAtencionSecretaria(parametrosConsultorio,
                                                                                    generadoresVariableAleatoria);
            finAtencionSecretaria.setPaciente(pacienteEstudio);
            estadoActual.setFinAtencionSecretaria(finAtencionSecretaria);
            heapEventos.add(finAtencionSecretaria);
        }
        if(estadoActual.llegadaEstudioPospuesta() && estadoActual.llegadaTurnoPospuesta()
                && estadoActual.getSecretaria().estaLibre() && estadoActual.getTecnico().estaLibre()){
            EventoFinJornada eventoFinJornada = new EventoFinJornada();
            float momentoFinJornada = estadoActual.getMomentoInicioJornada() + (5*60.0f);
            momentoFinJornada = (float)truncar(momentoFinJornada,
                    parametrosConsultorio.getParametrosSecretaria().getPrecision());
            eventoFinJornada.setMomentoEvento(momentoFinJornada);
            //eventoFinJornada.setNombreEvento("Final de Jornada");
            estadoActual.setFinJornada(eventoFinJornada);
            heapEventos.add(eventoFinJornada);
        }
        return estadoActual;
    }
    private EventoLlegadaPacienteEstudio calcularSiguienteLlegada(VectorEstadoClinica estadoActual,
                                                                ParametrosConsultorio parametrosConsultorio,
                                                                Map<String, ICambioDistribucion> generadoresVariableAleatoria){

        int presicion = parametrosConsultorio.getParametrosSecretaria().getPrecision();

        EventoLlegadaPacienteEstudio siguienteLlegada = new EventoLlegadaPacienteEstudio();
        siguienteLlegada.setRandomLlegadaPacienteEstudio(parametrosConsultorio.getRandomBaseCULlegadaEstudio().getRandom());

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosConsultorio.getLambdaLlegadaEstudio());
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        ICambioDistribucion generadorVariableAleatoria = generadoresVariableAleatoria
                .get(ConstantesCambioDistribucion.EXP_NEG);
        VariableAleatoria variableAleatoria = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosLlegadaEstudio(),
                parametrosConsultorio.getRandomBaseCULlegadaEstudio());
        parametrosConsultorio.setRandomBaseCULlegadaEstudio(variableAleatoria.getSiguienteRandomBase());

        float tiempoLlegada = variableAleatoria.getRandomGenerado();
        float momentoLlegada = this.momentoEvento + tiempoLlegada;
        momentoLlegada = (float) truncar(momentoLlegada, presicion);
        float finalDia = (estadoActual.getDia()-1)*24*60.0f + (13*60.0f);
        if(momentoLlegada >= finalDia || estadoActual.getCantLlegadasEstudioDiaActual() >= parametrosConsultorio.getTurnosDisponiblesDiario()){
            float inicioSiguienteDia = estadoActual.getDia()*24*60.0f + (60*8.0f);
            inicioSiguienteDia = (float)truncar(inicioSiguienteDia, presicion);
            momentoLlegada = inicioSiguienteDia + tiempoLlegada;
            momentoLlegada = (float) truncar(momentoLlegada, presicion);
        }
        siguienteLlegada.setTiempoHastaEvento(tiempoLlegada);
        siguienteLlegada.setMomentoEvento(momentoLlegada);
        return siguienteLlegada;
    }
}
