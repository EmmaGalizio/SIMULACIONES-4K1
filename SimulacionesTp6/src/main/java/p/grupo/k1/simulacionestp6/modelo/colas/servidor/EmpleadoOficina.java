package p.grupo.k1.simulacionestp6.modelo.colas.servidor;

public class EmpleadoOficina extends Servidor{

    @Override
    public Servidor clone() {
        EmpleadoOficina empleadoOficina = new EmpleadoOficina();
        empleadoOficina.setEstado(this.estado);
        empleadoOficina.setId(this.id);
        empleadoOficina.setClienteActual(this.clienteActual);
        empleadoOficina.setMomentoLiberacion(this.momentoLiberacion);
        empleadoOficina.setNombre(this.nombre);
        return empleadoOficina;
    }
}
