package emma.galzio.simulacionestp7consultorio.modelo;

import emma.galzio.simulacionestp7consultorio.modelo.cliente.Cliente;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoFinAtencionSecretaria;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoFinEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoLlegadaPacienteEstudio;
import emma.galzio.simulacionestp7consultorio.modelo.eventos.EventoLlegadaPacienteTurno;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Secretaria;
import emma.galzio.simulacionestp7consultorio.modelo.servidor.Tecnico;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
public class VectorEstadoClinica {

    private float reloj;
    private String nombreEvento;
    private EventoLlegadaPacienteTurno llegadaPacienteTurno;
    private EventoLlegadaPacienteEstudio llegadaPacienteEstudio;
    private EventoFinAtencionSecretaria finAtencionSecretaria;
    private EventoFinEstudio finEstudio;
    private Queue<Cliente> colaSecretaria;
    private Queue<Cliente> colaTecnico;
    private Secretaria secretaria;
    private Tecnico tecnico;

    private float acumuladorPermanenciaConTurno;
    private float acumuladorEsperaTecnico; //Calculo que tiene que incluir desde el momento en que llega al sistema hasta
    //que lo empieza a atender el técnico, incluyendo la espera en la cola de la caja y el tiempo de atención de la secretaria.

    private List<Cliente> clientes;


}
