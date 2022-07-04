package emma.galzio.simulacionestp7consultorio.modelo.servidor;

import emma.galzio.simulacionestp7consultorio.modelo.cliente.Paciente;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.Queue;

@Data
public class Servidor {

    protected Paciente pacienteActual;
    protected EstadosServidor estado;
    //private Queue<Paciente> cola;


    @Override
    public Object clone() {
        Servidor nuevoServidor = new Servidor();
        nuevoServidor.setPacienteActual(this.pacienteActual);
        //nuevoServidor.setCola(new ArrayDeque<>(cola));
        nuevoServidor.setEstado(this.estado);
        return nuevoServidor;
    }

    public void liberar() {
        estado = EstadosServidor.getInstanceLibre();
    }
    public void ocupar(){
        estado = EstadosServidor.getInstanceOcupado();
    }

    public boolean estaLibre(){
        return estado.estaLibre();
    }

    /*
    public Paciente obtenerSiguientePacienteEnCola(){
        if(cola == null) return null;
        return cola.poll();
    }

    public boolean tieneCola(){
        return cola != null && !cola.isEmpty();
    }

    public void agregarPacienteCola(Paciente paciente){
        if(cola == null) cola = new ArrayDeque<>();
        cola.add(paciente);
    }
     */
}
