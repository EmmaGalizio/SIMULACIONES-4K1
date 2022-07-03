package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

import java.util.Map;

@Data
public class EventoFinEstudio extends Evento{

    private float randomFinEstudio;

    public EventoFinEstudio(){
        this.nombreEvento = "Fin Estudio";
    }

    @Override
    public VectorEstadoClinica procesarEvento(VectorEstadoClinica estadoAnterior,
                                              IGeneradorRandom generadorRandom,
                                              Map<String, ICambioDistribucion> generadoresVariableAleatoria,
                                              ParametrosConsultorio parametrosConsultorio,
                                              TSBHeap<Evento> heapEventos) {

        VectorEstadoClinica estadoActual = (VectorEstadoClinica) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.setNombreEvento(this.nombreEvento);
        estadoActual.calcularDiaYMinutos();
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);
        estadoActual.incrementarEstudiosFinalizados();
        estadoActual.incrementarAtencionFinalizada();
        estadoActual.setPacienteAtencionFinalizada(paciente);

        int precision = parametrosConsultorio.getParametrosTecnico().getPrecision();

        if(estadoActual.tieneColaTecnico()){
            estadoActual.getTecnico().ocupar();

            PacienteEstudio siguientePaciente = (PacienteEstudio) estadoActual.obtenerSiguientePacienteEnColaTecnico();
            siguientePaciente.setMomentoInicioAtencionTecnico(this.momentoEvento);

            estadoActual.acumularTiempoEsperaColaTecnico(siguientePaciente, precision);
            estadoActual.acumularTiempoEsperaTecnico(siguientePaciente,precision);

            EventoFinEstudio eventoFinEstudio = calcularFinEstudio(parametrosConsultorio, generadoresVariableAleatoria);
            eventoFinEstudio.setPaciente(siguientePaciente);

            estadoActual.setFinEstudio(eventoFinEstudio);
            heapEventos.add(eventoFinEstudio);
        } else{
            estadoActual.liberarTecnico();
            estadoActual.setFinEstudio(null);
            if(estadoActual.getSecretaria().estaLibre() && estadoActual.horarioFinJornadaExcedido()){
                EventoFinJornada eventoFinJornada = new EventoFinJornada();

                eventoFinJornada.setMomentoEvento(this.momentoEvento);
                estadoActual.setFinJornada(eventoFinJornada);
                heapEventos.add(eventoFinJornada);
            }
        }
        return estadoActual;
    }
}
