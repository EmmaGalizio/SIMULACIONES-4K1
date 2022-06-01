package k1.grupo.p.simulacionestp5colas.modelo.colas;

import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return getNumeroCliente() == cliente.getNumeroCliente() && Float.compare(cliente.getHoraLlegadaCaseta(), getHoraLlegadaCaseta()) == 0 && Float.compare(cliente.getHoraLlegadaNave(), getHoraLlegadaNave()) == 0 && Float.compare(cliente.getHoraLlegadaOficina(), getHoraLlegadaOficina()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroCliente(), getHoraLlegadaCaseta(), getHoraLlegadaNave(), getHoraLlegadaOficina());
    }

    @Override
    public Object clone(){
        Cliente cliente = new Cliente();
        cliente.setNumeroCliente(numeroCliente);
        cliente.setHoraLlegadaCaseta(horaLlegadaCaseta);
        cliente.setHoraLlegadaNave(horaLlegadaNave);
        cliente.setHoraLlegadaOficina(horaLlegadaOficina);
        cliente.setHoraInicioAtencionCaseta(horaInicioAtencionCaseta);
        cliente.setHoraInicioAtencionNave(horaInicioAtencionNave);
        cliente.setHoraInicioAtencionOficina(horaInicioAtencionOficina);
        cliente.setServidorActual(servidorActual);
        cliente.setEstado(estado);
        return cliente;
    }

    public boolean tieneId(int numeroCliente) {
        return this.numeroCliente == numeroCliente;
    }
}
