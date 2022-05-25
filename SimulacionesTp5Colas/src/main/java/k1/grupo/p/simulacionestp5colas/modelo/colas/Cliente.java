package k1.grupo.p.simulacionestp5colas.modelo.colas;

import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;

@Data
public class Cliente {

    private int numeroCliente;
    private float horaLlegadaCaseta;
    private float horaLlegadaNave;
    private float horaLlegadaOficina;
    private float horaInicioAtencionCaseta;
    private float horaInicioAtencionNave;
    private float horaInicioAtencionOficina;
    private EstadoCliente estado;
    private Servidor servidorActual;

}
