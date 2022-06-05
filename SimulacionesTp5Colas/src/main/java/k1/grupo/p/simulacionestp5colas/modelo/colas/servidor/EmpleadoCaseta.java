package k1.grupo.p.simulacionestp5colas.modelo.colas.servidor;

public class EmpleadoCaseta extends Servidor{

    @Override
    public Servidor clone() {
        EmpleadoCaseta empleadoCaseta = new EmpleadoCaseta();
        empleadoCaseta.setEstado(this.estado);
        empleadoCaseta.setId(this.id);
        empleadoCaseta.setClienteActual(this.clienteActual);
        empleadoCaseta.setMomentoLiberacion(this.momentoLiberacion);
        empleadoCaseta.setNombre(this.nombre);
        return empleadoCaseta;
    }
}
