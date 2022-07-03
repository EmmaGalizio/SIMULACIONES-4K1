package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.EstadoCliente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

import java.util.Map;

@Data
public class EventoFinAtencionSecretaria extends Evento{

    private float randomAtencionSecretaria;

    @Override
    public VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                              IGeneradorRandom generadorRandom,
                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                              ParametrosConsultorio parametrosConsultorio,
                                              TSBHeap<Evento> heapEventos) {

        VectorEstadoClinica estadoActual = (VectorEstadoClinica) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.calcularDiaYMinutos();
        estadoActual.setNombreEvento("Fin At. Secretaria");
        //No debería hacer falta, en el estadoAnterior la secretaria va a estar siempre
        //Ocupada.
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);

        if(!estadoActual.tieneColaSecretaria()){
            estadoActual.getSecretaria().liberar();
            estadoActual.setFinAtencionSecretaria(null);
        } else{
            Paciente siguientePaciente = estadoActual.obtenerSiguientePacienteEnColaSecretaria();
            siguientePaciente.setEstado(EstadoCliente.getInstanceAtencionSecretaria());
            estadoActual.getSecretaria().ocupar();

            EventoFinAtencionSecretaria finAtencionSecretaria = calcularFinAtencionSecretaria(parametrosConsultorio,
                                                                                        generadoresVariableAleatoria);
            finAtencionSecretaria.setPaciente(siguientePaciente);
            estadoActual.setFinAtencionSecretaria(finAtencionSecretaria);
            heapEventos.add(finAtencionSecretaria);
        }

        //Falta la parte en la que se fija si el paciente tiene turno o no.
        //Si no tiene turno directamente sale del sistema, no se genera ningún otro evento
        //Y se marca de alguna forma que se debe eliminar al paciente.
        //Si tiene turno entonces se fija si el técnico está ocupado.
        if(paciente.tieneTurno()){
            //ghghhfhgfh
            if(!estadoActual.getTecnico().estaLibre()){
                paciente.setEstado(EstadoCliente.getInstanceEsperandoTecnico());
                estadoActual.agregarPacienteColaTecnico(paciente);
                PacienteEstudio pacienteEstudio = (PacienteEstudio) paciente;
                pacienteEstudio.setMomentoLlegadaColaTecnico(this.momentoEvento);
            }else{

                paciente.setEstado(EstadoCliente.getInstanceAtencionTecnico());
                PacienteEstudio pacienteEstudio = (PacienteEstudio) paciente;
                pacienteEstudio.setMomentoLlegadaColaTecnico(this.momentoEvento);
                pacienteEstudio.setMomentoInicioAtencionTecnico(this.momentoEvento);
                estadoActual.acumularTiempoEsperaTecnico(pacienteEstudio,
                                                        parametrosConsultorio.getParametrosSecretaria().getPrecision());
                estadoActual.acumularTiempoEsperaColaTecnico(pacienteEstudio,
                                                        parametrosConsultorio.getParametrosSecretaria().getPrecision());

                //Calculo fin atencion tecnico.
                EventoFinEstudio finEstudio = calcularFinEstudio(parametrosConsultorio, generadoresVariableAleatoria);
                finEstudio.setPaciente(paciente);
                estadoActual.setFinEstudio(finEstudio);
                heapEventos.add(finEstudio);
            }

        }else{
            //Puede darse el caso de que el paciente actual sea el último paciente del día,
            //Que no haya más nadie para realizarse el estudio, y que el técnico ya esté libre.
            //Por eso hay que fijarse si el tiempo actual ya superó el horario de cierre y si el
            //técnico está libre, si se dan estos dos casos, quiere decir que ya es momento de cerrar,
            //Termina la jornada laboral, por lo que hay que crear el evento de fin de jornada.

            //En las llegadas, en la parte donde verifíca si el momento de la próxima llegada se pasa
            //para el día siguiente, ahí es necesario fijarse si la secretaria y el técnico están libres.
            //Si la siguiente llegada para ambos tipos de pacientes se pasan para el otro día,y tanto el técnico
            //Como la secretaria están libres, se crea el evento de fin de jornada con la hora exacta de cierre (13 hs).

            //Al pasarse del horario de cierre, puede darse el caso de que pasadas las 13 el técnico esté ocupado
            //Pero la secretaria esté libre, por lo que en vez de calcular el tiempo laboral total con los
            //días simulados multiplicados por 13*60, es necesario llevar un acumulador del tiempo que se trabajó
            //durante esos días. Si es posible un double;
            estadoActual.setPacienteAtencionFinalizada(paciente);
            estadoActual.incrementarAtencionFinalizada();

            if(estadoActual.esFinDeJornada() && estadoActual.getTecnico().estaLibre() &&
                        estadoActual.getSecretaria().estaLibre()){
                EventoFinJornada eventoFinJornada = new EventoFinJornada();
                float momentoFinJornada = estadoActual.getMomentoInicioJornada() + (13*60.0f);
                momentoFinJornada = (float)truncar(momentoFinJornada,
                        parametrosConsultorio.getParametrosSecretaria().getPrecision());
                eventoFinJornada.setMomentoEvento(momentoFinJornada);
                eventoFinJornada.setNombreEvento("Final de Jornada");
                estadoActual.setFinJornada(eventoFinJornada);
                heapEventos.add(eventoFinJornada);

            }
        }

        return estadoActual;
    }
}
