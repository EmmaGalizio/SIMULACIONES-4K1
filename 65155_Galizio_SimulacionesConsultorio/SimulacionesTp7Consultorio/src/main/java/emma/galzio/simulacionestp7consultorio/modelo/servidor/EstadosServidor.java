package emma.galzio.simulacionestp7consultorio.modelo.servidor;

import lombok.Data;

import java.util.Objects;

@Data
public class EstadosServidor {

    public static final String SERVIDOR_OCUPADO = "Ocupado";
    public static final String SERVIDOR_LIBRE = "Libre";
    public static final String SERVIDOR_FUERA_HORARIO = "Fuera de Horario";

    private static EstadosServidor servidorLibre;
    private static EstadosServidor servidorOcupado;
    private static EstadosServidor servidorFueraHorario;
    private String estado;


    private EstadosServidor(String estado){
        this.estado = estado;
    }

    public static EstadosServidor getInstanceLibre(){
        if(servidorLibre == null) servidorLibre = new EstadosServidor(SERVIDOR_LIBRE);
        return servidorLibre;
    }

    public static EstadosServidor getInstanceOcupado(){
        if(servidorOcupado == null) servidorOcupado = new EstadosServidor(SERVIDOR_OCUPADO);
        return servidorOcupado;
    }

    public static EstadosServidor getInstanceFueraHorario(){
        if(servidorFueraHorario == null) servidorFueraHorario = new EstadosServidor(SERVIDOR_FUERA_HORARIO);
        return servidorFueraHorario;
    }

    public boolean estaLibre(){
        return this.equals(servidorLibre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //Debería cortar acá si está libre.
        if (o == null || getClass() != o.getClass()) return false;
        EstadosServidor that = (EstadosServidor) o;
        return estado.equals(that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estado);
    }

    public String toString(){
        return estado;
    }

}
