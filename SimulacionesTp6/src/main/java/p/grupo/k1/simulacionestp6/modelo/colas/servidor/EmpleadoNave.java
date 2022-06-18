package p.grupo.k1.simulacionestp6.modelo.colas.servidor;

import lombok.Data;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.EventoFinAtencion;

@Data
public class EmpleadoNave extends Servidor{

    private EventoFinAtencion atencionDetenida;

    @Override
    public Servidor clone() {
        EmpleadoNave empleadoNave = new EmpleadoNave();
        empleadoNave.setEstado(this.estado);
        empleadoNave.setId(this.id);
        empleadoNave.setClienteActual(this.clienteActual);
        empleadoNave.setMomentoLiberacion(this.momentoLiberacion);
        empleadoNave.setNombre(this.nombre);
        return empleadoNave;
    }

    public boolean estaBloqueado() {
        return estado.estadoBloqueado();
    }
}
