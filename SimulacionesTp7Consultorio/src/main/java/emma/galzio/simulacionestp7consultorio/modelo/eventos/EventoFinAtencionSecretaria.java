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

    public EventoFinAtencionSecretaria(){
        this.nombreEvento = "Fin At. Secretaria";
    }

    @Override
    public VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                              IGeneradorRandom generadorRandom,
                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                              ParametrosConsultorio parametrosConsultorio,
                                              TSBHeap<Evento> heapEventos) {

        VectorEstadoClinica estadoActual = (VectorEstadoClinica) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.calcularDiaYMinutos();
        estadoActual.setNombreEvento(this.nombreEvento);
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
            estadoActual.getSecretaria().setPacienteActual(siguientePaciente);

            EventoFinAtencionSecretaria finAtencionSecretaria = calcularFinAtencionSecretaria(parametrosConsultorio,
                                                                                        generadoresVariableAleatoria);
            finAtencionSecretaria.setPaciente(siguientePaciente);
            estadoActual.setFinAtencionSecretaria(finAtencionSecretaria);
            heapEventos.add(finAtencionSecretaria);
        }

        if(paciente.tieneTurno()){
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
                estadoActual.getTecnico().ocupar();
                estadoActual.getTecnico().setPacienteActual(pacienteEstudio);
                //Calculo fin atencion tecnico.
                EventoFinEstudio finEstudio = calcularFinEstudio(parametrosConsultorio, generadoresVariableAleatoria);
                finEstudio.setPaciente(paciente);
                estadoActual.setFinEstudio(finEstudio);
                heapEventos.add(finEstudio);
            }

        }else{
            estadoActual.setPacienteAtencionFinalizada(paciente);
            estadoActual.incrementarAtencionFinalizada();

            if(estadoActual.llegadaTurnoPospuesta() && estadoActual.llegadaEstudioPospuesta() &&
                        estadoActual.getTecnico().estaLibre() &&
                        estadoActual.getSecretaria().estaLibre()){
                EventoFinJornada eventoFinJornada = new EventoFinJornada();
                float momentoFinJornada = (!estadoActual.esFinDeJornada()) ?
                                                        estadoActual.getMomentoInicioJornada() + (5*60.0f):
                                                        this.momentoEvento;
                momentoFinJornada = (float)truncar(momentoFinJornada,
                        parametrosConsultorio.getParametrosSecretaria().getPrecision());
                eventoFinJornada.setMomentoEvento(momentoFinJornada);
                estadoActual.setFinJornada(eventoFinJornada);
                heapEventos.add(eventoFinJornada);
            }
        }

        return estadoActual;
    }
}
