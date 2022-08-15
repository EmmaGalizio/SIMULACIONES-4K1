package emma.galzio.simulacionestp7consultorio.modelo.servidor;

public class Tecnico extends Servidor{
    @Override
    public Object clone() {
        Tecnico nuevoServidor = new Tecnico();
        nuevoServidor.setPacienteActual(this.pacienteActual);
        //nuevoServidor.setCola(new ArrayDeque<>(cola));
        nuevoServidor.setEstado(this.estado);
        return nuevoServidor;
    }
}
