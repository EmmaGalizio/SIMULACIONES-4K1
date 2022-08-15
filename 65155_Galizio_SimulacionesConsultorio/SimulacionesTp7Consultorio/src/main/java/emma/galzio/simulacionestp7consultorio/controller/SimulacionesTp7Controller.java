package emma.galzio.simulacionestp7consultorio.controller;

import emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion.ICambioDistribucion;
import emma.galzio.simulacionestp7consultorio.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.*;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.estructurasDatos.TSBHeap;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.Evento;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoFinJornada;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoLlegadaPacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoLlegadaPacienteTurno;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Secretaria;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Tecnico;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SimulacionesTp7Controller {

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;
    @Autowired
    private Map<String, ICambioDistribucion> generadoresVariableAleatoria;

    public List<VectorEstadoClinica> generarSimulacion(ParametrosConsultorio parametrosConsultorio){

        if(parametrosConsultorio == null) throw new IllegalArgumentException("Debe indicar los parámetros de la simulación");
        if(!parametrosConsultorio.validarParametrosGeneradores())
                throw new IllegalArgumentException("Debe indicar los parámetros para generar los números aleatorios");

        float momentoCorte = ((parametrosConsultorio.getDiasASimular()-1)*24*60.0f) + (13*60.0f);
        //Corta apenas termine el día.

        TSBHeap<Evento> heapEventos = new TSBHeap<>();

        IGeneradorRandom generadorRandom = generadoresRandom
                                    .get(parametrosConsultorio.getParametrosSecretaria().getMetodoGeneradorRandom());
        ICambioDistribucion generadorExponencial = generadoresVariableAleatoria.get(ConstantesCambioDistribucion.EXP_NEG);

        VectorEstadoClinica vectorEstadoAnterior = generarVectorInicial(parametrosConsultorio,generadorExponencial,
                                                                            generadorRandom ,heapEventos);
        VectorEstadoClinica vectorEstadoActual;
        List<VectorEstadoClinica> resultadoSimulacion = new LinkedList<>();
        resultadoSimulacion.add(vectorEstadoAnterior);
        parametrosConsultorio.setRandomBaseCUTecnico(generadorRandom
                                            .siguientePseudoAleatoreo(parametrosConsultorio.getParametrosTecnico()));
        parametrosConsultorio.setRandomBaseCUSecretaria(generadorRandom
                .siguientePseudoAleatoreo(parametrosConsultorio.getParametrosSecretaria()));

        int filaInicial = parametrosConsultorio.getPrimeraFila();
        int filaFinal = filaInicial + parametrosConsultorio.getCantFilasMostrar();
        int cantEventos = 1;

        Evento eventoActual = heapEventos.remove();

        //do {
        while(true){
            vectorEstadoActual = eventoActual.procesarEvento(vectorEstadoAnterior,
                    generadorRandom,
                    generadoresVariableAleatoria,
                    parametrosConsultorio,
                    heapEventos);

            Paciente pacienteAtencionFinalizada = vectorEstadoActual.getPacienteAtencionFinalizada();
            if (pacienteAtencionFinalizada != null) vectorEstadoActual.destruirPaciente(pacienteAtencionFinalizada);
            if (cantEventos >= filaInicial && cantEventos <= filaFinal) {
                resultadoSimulacion.add(vectorEstadoActual);
            }
            vectorEstadoAnterior = vectorEstadoActual;
            cantEventos++;
            if((eventoActual.getMomentoEvento() >= momentoCorte && eventoActual instanceof EventoFinJornada) ||
                                                                                            heapEventos.isEmpty()){                break;
            }
            eventoActual = heapEventos.remove();
        }
        //}while(eventoActual.getMomentoEvento() <= momentoCorte && !(eventoActual instanceof EventoFinJornada));
        if(cantEventos > filaFinal) resultadoSimulacion.add(vectorEstadoActual);

        return resultadoSimulacion;
    }

    private VectorEstadoClinica generarVectorInicial(ParametrosConsultorio parametrosConsultorio,
                                                    ICambioDistribucion generadorVariableAleatoria,
                                                    IGeneradorRandom generadorRandom,
                                                    TSBHeap<Evento> heapEventos){

        VectorEstadoClinica vectorInicial = new VectorEstadoClinica();
        vectorInicial.setReloj((8*60.0f)); //Arranca a las 8 am del día 1;
        vectorInicial.setMomentoInicioJornada(vectorInicial.getReloj());
        vectorInicial.calcularDiaYMinutos();
        vectorInicial.setNombreEvento("Inicialización");
        vectorInicial.setColaTecnico(new ArrayDeque<>());
        vectorInicial.setColaSecretaria(new ArrayDeque<>());
        vectorInicial.setSecretaria(new Secretaria());
        vectorInicial.liberarSecretaria();
        vectorInicial.setTecnico(new Tecnico());
        vectorInicial.liberarTecnico();

        this.generarLlegadaPacienteTurno(vectorInicial, parametrosConsultorio, generadorVariableAleatoria,
                                                                                    generadorRandom, heapEventos);
        this.generarLlegadaPacienteEstudio(vectorInicial, parametrosConsultorio, generadorVariableAleatoria,
                                                                                        generadorRandom, heapEventos);



        return vectorInicial;
    }

    private void generarLlegadaPacienteTurno(VectorEstadoClinica vectorEstadoClinica,
                                             ParametrosConsultorio parametrosConsultorio,
                                             ICambioDistribucion generadorVariableAleatoria,
                                             IGeneradorRandom generadorRandom,
                                             TSBHeap<Evento> heapEventos){
        EventoLlegadaPacienteTurno primerLlegadaPacienteTurno = new EventoLlegadaPacienteTurno();
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        parametrosCambioDistribucion.setLambda(parametrosConsultorio.getLambdaLlegadaTurno());
        if(parametrosConsultorio.getRandomBaseCULlegadaTurno() == null){
            parametrosConsultorio.setRandomBaseCULlegadaTurno(generadorRandom
                                            .siguientePseudoAleatoreo(parametrosConsultorio.getParametrosLlegadaTurno()));
        }
        VariableAleatoria variableAleatoriaTurno = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosLlegadaTurno(),
                parametrosConsultorio.getRandomBaseCULlegadaTurno());

        primerLlegadaPacienteTurno.setTiempoHastaEvento(CommonFunc.round(variableAleatoriaTurno.getRandomGenerado(),4));
        primerLlegadaPacienteTurno.calcularMomentoEvento(8*60.0f,parametrosCambioDistribucion.getPresicion());
        //Actualizacion random llegada paciente sin turno
        primerLlegadaPacienteTurno.setRandomLlegadaPacienteTurno(parametrosConsultorio.getRandomBaseCULlegadaTurno()
                .getRandom());
        parametrosConsultorio.setRandomBaseCULlegadaTurno(variableAleatoriaTurno.getSiguienteRandomBase());

        heapEventos.add(primerLlegadaPacienteTurno);
        vectorEstadoClinica.setLlegadaPacienteTurno(primerLlegadaPacienteTurno);
    }
    private void generarLlegadaPacienteEstudio(VectorEstadoClinica vectorEstadoClinica,
                                             ParametrosConsultorio parametrosConsultorio,
                                             ICambioDistribucion generadorVariableAleatoria,
                                             IGeneradorRandom generadorRandom,
                                             TSBHeap<Evento> heapEventos){
        EventoLlegadaPacienteEstudio primerLlegadaPacienteEstudio = new EventoLlegadaPacienteEstudio();
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setPresicion(parametrosConsultorio.getParametrosSecretaria().getPrecision());
        parametrosCambioDistribucion.setLambda(parametrosConsultorio.getLambdaLlegadaEstudio());
        if(parametrosConsultorio.getRandomBaseCULlegadaEstudio() == null) {
            parametrosConsultorio.setRandomBaseCULlegadaEstudio(generadorRandom
                        .siguientePseudoAleatoreo(parametrosConsultorio.getParametrosLlegadaEstudio()));
        }
        VariableAleatoria variableAleatoriaEstudio = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,
                parametrosConsultorio.getParametrosLlegadaEstudio(),
                parametrosConsultorio.getRandomBaseCULlegadaEstudio());
        primerLlegadaPacienteEstudio.setTiempoHastaEvento(CommonFunc.round(variableAleatoriaEstudio.getRandomGenerado(),4));
        primerLlegadaPacienteEstudio.calcularMomentoEvento(8*60.f,parametrosCambioDistribucion.getPresicion());
        primerLlegadaPacienteEstudio.setRandomLlegadaPacienteEstudio(parametrosConsultorio
                .getRandomBaseCULlegadaEstudio().getRandom());
        parametrosConsultorio.setRandomBaseCULlegadaEstudio(variableAleatoriaEstudio.getSiguienteRandomBase());

        heapEventos.add(primerLlegadaPacienteEstudio);
        vectorEstadoClinica.setLlegadaPacienteEstudio(primerLlegadaPacienteEstudio);
    }

    public double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }



}
