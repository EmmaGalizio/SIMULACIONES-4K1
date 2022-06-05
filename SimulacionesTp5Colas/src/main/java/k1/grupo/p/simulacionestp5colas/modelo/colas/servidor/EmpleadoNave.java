package k1.grupo.p.simulacionestp5colas.modelo.colas.servidor;

public class EmpleadoNave extends Servidor{

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
}
