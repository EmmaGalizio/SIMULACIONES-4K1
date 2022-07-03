package emma.galzio.simulacionestp7consultorio.modelo.eventos;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;

import java.util.Map;

public class EventoInicioJornada extends Evento{

    public EventoInicioJornada(){
        this.nombreEvento = "Inicio Jornada";
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
        estadoActual.setInicioJornada(null);
        estadoActual.setFinJornada(null);
        estadoActual.setMomentoInicioJornada(this.momentoEvento);
        estadoActual.setNombreEvento(this.nombreEvento);
        estadoActual.getTecnico().liberar();
        estadoActual.getSecretaria().liberar();

        return estadoActual;
    }
}
