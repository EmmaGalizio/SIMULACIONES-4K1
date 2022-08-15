package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.EstadosServidor;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import lombok.Data;

import java.util.Map;

public class EventoFinJornada extends Evento{

    public EventoFinJornada(){
        this.nombreEvento = "Fin de Jornada";
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
        estadoActual.acumularTiempoLibreSecretaria(estadoAnterior);
        estadoActual.acumularMinutosJornadaLaboral(parametrosConsultorio);
        //estadoActual.reiniciarContadoresDiarios();
        estadoActual.getSecretaria().setEstado(EstadosServidor.getInstanceFueraHorario());
        estadoActual.getTecnico().setEstado(EstadosServidor.getInstanceFueraHorario());
        estadoActual.getTecnico().setPacienteActual(null);
        estadoActual.getSecretaria().setPacienteActual(null);
        estadoActual.setFinJornada(null);
        EventoInicioJornada siguienteInicioJornada = new EventoInicioJornada();
        double inicioJornada = (estadoActual.getDia()*24*60.0f) + (8*60.0f);
        //inicioJornada = (float)truncar(inicioJornada, parametrosConsultorio.getParametrosSecretaria().getPrecision());
        //inicioJornada = (float)truncar(inicioJornada, 4);
        inicioJornada = CommonFunc.round(inicioJornada,4);

        siguienteInicioJornada.setMomentoEvento(inicioJornada);
        estadoActual.setInicioJornada(siguienteInicioJornada);
        heapEventos.add(siguienteInicioJornada);

        return estadoActual;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }
}
