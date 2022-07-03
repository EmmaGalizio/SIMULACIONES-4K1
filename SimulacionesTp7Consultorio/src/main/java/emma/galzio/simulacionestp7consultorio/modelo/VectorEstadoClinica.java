package emma.galzio.simulacionestp7consultorio.modelo;

import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import emma.galzio.simulacionestp7consultorio.modelo.cliente.PacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.*;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Secretaria;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Tecnico;
import lombok.Data;

import java.util.*;

@Data
public class VectorEstadoClinica {

    private float reloj;
    private int dia;
    private float minutoDelDia;
    private String nombreEvento;
    private float momentoInicioJornada;
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
    private int cantAtencionFinalizadas;
    private int cantEstudiosFinalizados;
    private double acumuladorTiempoJornadasLaborales;
    private float acumuladorPermanenciaConTurno;
    private float acumuladorTiempoTotalEsperaTecnico;
    private float acumuladorTiempoColaTecnico;
    private float acumuladorTiempoLibreSecretaria;

    private Paciente pacienteAtencionFinalizada;

    //Al no mostrar los pacientes, no debería hacer falta clonar la lista de pacientes.
    //Al procesarlos no importa si se modifica el estado del paciente en el estado anterior.
    //Si bien no habria mucha diferencia en la memoria, porque nunca va a haber más de 30 pacientes al mismo tiempo
    //En el sistema, y no se mantienen más de dos vectores de estado a la vez, excepto por los que están en la lista a mostrar.
    private List<Paciente> pacientes;


    @Override
    public Object clone(){

        VectorEstadoClinica nuevoVector = new VectorEstadoClinica();
        nuevoVector.setAcumuladorTiempoColaTecnico(acumuladorTiempoColaTecnico);
        nuevoVector.setAcumuladorPermanenciaConTurno(acumuladorPermanenciaConTurno);
        nuevoVector.setAcumuladorTiempoLibreSecretaria(acumuladorTiempoLibreSecretaria);
        nuevoVector.setAcumuladorTiempoTotalEsperaTecnico(acumuladorTiempoTotalEsperaTecnico);
        nuevoVector.setSecretaria((Secretaria)secretaria.clone());
        nuevoVector.setTecnico((Tecnico) tecnico.clone());
        nuevoVector.setLlegadaPacienteEstudio(llegadaPacienteEstudio);
        nuevoVector.setLlegadaPacienteTurno(llegadaPacienteTurno);
        nuevoVector.setFinAtencionSecretaria(finAtencionSecretaria);
        nuevoVector.setFinEstudio(finEstudio);
        nuevoVector.setColaSecretaria(new ArrayDeque<>(colaSecretaria));
        nuevoVector.setColaTecnico(new ArrayDeque<>(colaTecnico));
        nuevoVector.setPacientes(pacientes);
        nuevoVector.setMomentoInicioJornada(momentoInicioJornada);
        return nuevoVector;
    }

    public void calcularDiaYMinutos(){
        dia = (int)(reloj / (60*24)) + 1;
        minutoDelDia = reloj % (60*24);
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
            acumuladorTiempoLibreSecretaria = (float)truncar(acumuladorTiempoLibreSecretaria,4);
        }
    }

    public double truncar(double f, int precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

    public void incrementarLlegadasTurno(){
        cantLlegadasTurno++;
    }
    public void incrementerLlegadasEstudio(){
        cantLlegadasEstudio++;
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
        acumuladorTiempoTotalEsperaTecnico = (float)truncar(acumuladorTiempoTotalEsperaTecnico,
                                                        precision);
    }
    public void acumularTiempoEsperaColaTecnico(PacienteEstudio pacienteEstudio, int precision){
        acumuladorTiempoColaTecnico+= pacienteEstudio.getMomentoInicioAtencionTecnico() -
                                        pacienteEstudio.getMomentoLlegadaColaTecnico();
        acumuladorTiempoColaTecnico = (float)truncar(acumuladorTiempoColaTecnico, precision);
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
        acumuladorTiempoJornadasLaborales = (float) truncar(acumuladorTiempoJornadasLaborales,presicion);
    }
}
