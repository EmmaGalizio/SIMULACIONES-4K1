package emma.galzio.simulacionestp7consultorio.modelo.cliente;

import emma.galzio.simulacionestp7consultorio.modelo.servidor.Servidor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Paciente {

    private int id;
    private EstadoCliente estado;
    private float momentoLlegada;

    public abstract boolean tieneTurno();

}
