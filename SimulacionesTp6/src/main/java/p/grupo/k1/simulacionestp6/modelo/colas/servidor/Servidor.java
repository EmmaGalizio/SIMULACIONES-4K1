package p.grupo.k1.simulacionestp6.modelo.colas.servidor;

import p.grupo.k1.simulacionestp6.modelo.colas.Cliente;
import lombok.Getter;
import lombok.Setter;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.Evento;

@Getter@Setter
public abstract class Servidor {

    protected EstadoServidor estado;
    protected Cliente clienteActual;
    protected String nombre;
    protected int id;
    protected float momentoLiberacion;

    public boolean estaLibre(){
        return estado.estaLibre();
    }

    @Override
    public abstract Servidor clone();

}
