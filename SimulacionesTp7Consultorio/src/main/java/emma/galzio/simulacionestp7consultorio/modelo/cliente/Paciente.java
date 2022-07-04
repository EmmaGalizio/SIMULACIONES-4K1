package emma.galzio.simulacionestp7consultorio.modelo.cliente;

import emma.galzio.simulacionestp7consultorio.modelo.servidor.Servidor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Paciente {

    protected int id;
    protected EstadoCliente estado;
    protected float momentoLlegada;
    protected String tipoPaciente;

    public abstract boolean tieneTurno();

    public String getIdentificadorPaciente(){
        return tipoPaciente + "-"+id;
    }

}
