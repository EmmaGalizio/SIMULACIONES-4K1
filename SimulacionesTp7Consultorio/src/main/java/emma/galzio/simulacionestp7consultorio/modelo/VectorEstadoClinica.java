package emma.galzio.simulacionestp7consultorio.modelo;

import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.*;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Secretaria;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Tecnico;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import lombok.Data;

import java.util.*;

@Data
public class VectorEstadoClinica {

    private double reloj;
    private int dia;
    private double minutoDelDia;
    private String nombreEvento;
    private double momentoInicioJornada;
    private EventoLlegadaPacienteTurno llegadaPacienteTurno;
    private EventoLlegadaPacienteEstudio llegadaPacienteEstudio;
    private EventoFinAtencionSecretaria finAtencionSecretaria;
    private EventoFinEstudio finEstudio;
    private EventoFinJornada finJornada;
    private EventoInicioJornada inicioJornada;
    //Un evento de corte para indicar que cierra a las 13?? O espera a que se vaya el último paciente y cierra?
    //Igual que si llega un paciente después de las 13 hs, calculo que lo tiene que rechazar
    private Queue<Paciente> colaSecretaria;
    private Queue<Paciente> colaTecnico;
    private Secretaria secretaria;
    private Tecnico tecnico;
    private int cantLlegadasTurno;
    private int cantLlegadasEstudio;
    private int cantLlegadasTurnoDiaActual;
    private int cantLlegadasEstudioDiaActual;
    private int cantAtencionFinalizadas;
    private int cantEstudiosFinalizados;
    private double acumuladorTiempoJornadasLaborales;
    private double acumuladorPermanenciaConTurno;
    private double acumuladorTiempoTotalEsperaTecnico;
    private double acumuladorTiempoColaTecnico;
    private double acumuladorTiempoLibreSecretaria;
    private List<Paciente> pacientes;

    private Paciente pacienteAtencionFinalizada;

    //Al no mostrar los pacientes, no debería hacer falta clonar la lista de pacientes.
    //Al procesarlos no importa si se modifica el estado del paciente en el estado anterior.
    //Si bien no habria mucha diferencia en la memoria, porque nunca va a haber más de 30 pacientes al mismo tiempo
    //En el sistema, y no se mantienen más de dos vectores de estado a la vez, excepto por los que están en la lista a mostrar.


    @Override
    public Object clone(){

        VectorEstadoClinica nuevoVector = new VectorEstadoClinica();
        nuevoVector.setSecretaria((Secretaria)secretaria.clone());
        nuevoVector.setTecnico((Tecnico) tecnico.clone());
        nuevoVector.setLlegadaPacienteEstudio(llegadaPacienteEstudio);
        nuevoVector.setLlegadaPacienteTurno(llegadaPacienteTurno);
        nuevoVector.setFinAtencionSecretaria(finAtencionSecretaria);
        nuevoVector.setFinEstudio(finEstudio);
        nuevoVector.setFinJornada(finJornada);
        nuevoVector.setInicioJornada(inicioJornada);
        nuevoVector.setColaSecretaria(new ArrayDeque<>(colaSecretaria));
        nuevoVector.setColaTecnico(new ArrayDeque<>(colaTecnico));
        nuevoVector.setPacientes(pacientes);
        nuevoVector.setMomentoInicioJornada(momentoInicioJornada);
        nuevoVector.setAcumuladorTiempoColaTecnico(acumuladorTiempoColaTecnico);
        nuevoVector.setAcumuladorPermanenciaConTurno(acumuladorPermanenciaConTurno);
        nuevoVector.setAcumuladorTiempoLibreSecretaria(acumuladorTiempoLibreSecretaria);
        nuevoVector.setAcumuladorTiempoTotalEsperaTecnico(acumuladorTiempoTotalEsperaTecnico);
        nuevoVector.setAcumuladorTiempoJornadasLaborales(acumuladorTiempoJornadasLaborales);
        nuevoVector.setCantAtencionFinalizadas(cantAtencionFinalizadas);
        nuevoVector.setCantEstudiosFinalizados(cantEstudiosFinalizados);
        nuevoVector.setCantLlegadasEstudio(cantLlegadasEstudio);
        nuevoVector.setCantLlegadasTurno(cantLlegadasTurno);
        nuevoVector.setCantLlegadasEstudioDiaActual(cantLlegadasEstudioDiaActual);
        nuevoVector.setCantLlegadasTurnoDiaActual(cantLlegadasTurnoDiaActual);
        return nuevoVector;
    }

    public void calcularDiaYMinutos(){
        dia = (int)(reloj / (60*24)) + 1;
        minutoDelDia = reloj % (60*24);
        minutoDelDia = CommonFunc.round(minutoDelDia,4);
    }

    public Paciente obtenerSiguientePacienteEnColaSecretaria(){
        if(colaSecretaria == null) return null;
        return colaSecretaria.poll();
    }

    public boolean tieneColaSecretaria(){
        return colaSecretaria != null && !colaSecretaria.isEmpty();
    }

    public void agregarPacienteColaSecretaria(Paciente paciente){
        if(colaSecretaria == null) colaSecretaria = new ArrayDeque<>();
        colaSecretaria.add(paciente);
    }
    public Paciente obtenerSiguientePacienteEnColaTecnico(){
        if(colaTecnico == null) return null;
        return colaTecnico.poll();
    }

    public boolean tieneColaTecnico(){
        return colaTecnico != null && !colaTecnico.isEmpty();
    }

    public void agregarPacienteColaTecnico(Paciente paciente){
        if(colaTecnico == null) colaTecnico = new ArrayDeque<>();
        colaTecnico.add(paciente);
    }

    public void agregarPaciente(Paciente paciente){
        if(pacientes == null) pacientes = new LinkedList<>();
        pacientes.add(paciente);
    }
    public void liberarSecretaria(){
        secretaria.liberar();
    }


    public void liberarTecnico() {
        tecnico.liberar();
    }

    //Está mal, de esta forma
    public void acumularTiempoLibreSecretaria(VectorEstadoClinica estadoAnterior){
        if(estadoAnterior.getSecretaria().estaLibre()){
            acumuladorTiempoLibreSecretaria+= this.reloj - estadoAnterior.getReloj();
            //acumuladorTiempoLibreSecretaria = truncar(acumuladorTiempoLibreSecretaria,4);
            acumuladorTiempoLibreSecretaria = CommonFunc.round(acumuladorTiempoLibreSecretaria,4);
        }
    }

    public double truncar(double f, int precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

    public void incrementarLlegadasTurno(){

        cantLlegadasTurno++;
        cantLlegadasTurnoDiaActual++;
    }
    public void incrementerLlegadasEstudio(){
        cantLlegadasEstudio++;
        cantLlegadasEstudioDiaActual++;
    }
    public void incrementarAtencionFinalizada(){
        cantAtencionFinalizadas++;
    }
    public void incrementarEstudiosFinalizados(){
        cantEstudiosFinalizados++;
    }

    public void acumularTiempoEsperaTecnico(PacienteEstudio pacienteEstudio, int precision) {
        acumuladorTiempoTotalEsperaTecnico+= pacienteEstudio.getMomentoInicioAtencionTecnico()
                                                        - pacienteEstudio.getMomentoLlegada();
        //acumuladorTiempoTotalEsperaTecnico = truncar(acumuladorTiempoTotalEsperaTecnico,
         //                                               precision);
        acumuladorTiempoTotalEsperaTecnico = CommonFunc.round(acumuladorTiempoTotalEsperaTecnico,
                4);
    }
    public void acumularTiempoEsperaColaTecnico(PacienteEstudio pacienteEstudio, int precision){
        acumuladorTiempoColaTecnico+= pacienteEstudio.getMomentoInicioAtencionTecnico() -
                                        pacienteEstudio.getMomentoLlegadaColaTecnico();
        //acumuladorTiempoColaTecnico = truncar(acumuladorTiempoColaTecnico, precision);
        acumuladorTiempoColaTecnico = CommonFunc.round(acumuladorTiempoColaTecnico, 4);
    }

    public boolean esFinDeJornada() {
        return minutoDelDia >= (13*60.0f);
    }

    public boolean llegadaEstudioPospuesta(){

        if(llegadaPacienteTurno == null) return false;
        return llegadaPacienteTurno.getMomentoEvento() > (momentoInicioJornada + 5*60.0f);
    }
    public boolean llegadaTurnoPospuesta(){
        if(llegadaPacienteEstudio == null) return false;
        return llegadaPacienteEstudio.getMomentoEvento() > (momentoInicioJornada + 5*60.0f);
    }

    public boolean horarioFinJornadaExcedido() {
        return this.reloj >= (momentoInicioJornada + 5*60.0f);
    }


    public void destruirPaciente(Paciente paciente){

        if(paciente == null) return;
        Iterator<Paciente> iterator = pacientes.iterator();
        Paciente pacienteIterado;

        while(iterator.hasNext()){
            pacienteIterado = iterator.next();
            if(pacienteIterado.equals(paciente)) iterator.remove();
        }

    }

    public void acumularMinutosJornadaLaboral(ParametrosConsultorio parametrosConsultorio){
        if(finJornada == null) return;
        acumuladorTiempoJornadasLaborales+= this.reloj - this.momentoInicioJornada;
        int presicion = parametrosConsultorio.getParametrosTecnico().getPrecision();
        //acumuladorTiempoJornadasLaborales = truncar(acumuladorTiempoJornadasLaborales,presicion);
        acumuladorTiempoJornadasLaborales = CommonFunc.round(acumuladorTiempoJornadasLaborales,4);
    }

    public void reiniciarContadoresDiarios() {
        cantLlegadasEstudioDiaActual = 0;
        cantLlegadasTurnoDiaActual = 0;
    }

    public void acumularTiempoPermanenciaPacienteEstudio(PacienteEstudio paciente, int precision) {
        acumuladorPermanenciaConTurno+= this.reloj - paciente.getMomentoLlegada();
        //acumuladorPermanenciaConTurno = truncar(acumuladorPermanenciaConTurno, precision);
        acumuladorPermanenciaConTurno = CommonFunc.round(acumuladorPermanenciaConTurno, 4);
    }
}
