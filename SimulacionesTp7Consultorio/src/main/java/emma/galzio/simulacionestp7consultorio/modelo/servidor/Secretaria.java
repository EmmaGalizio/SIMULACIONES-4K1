package emma.galzio.simulacionestp7consultorio.modelo.servidor;

public class Secretaria extends Servidor{

    @Override
    public Object clone() {
        Secretaria nuevoServidor = new Secretaria();
        nuevoServidor.setPacienteActual(this.pacienteActual);
        //nuevoServidor.setCola(new ArrayDeque<>(cola));
        nuevoServidor.setEstado(this.estado);
        return nuevoServidor;
    }

}
