package emma.galzio.simulacionestp7consultorio.modelo.cliente;

import java.util.Hashtable;
import java.util.Map;

public class EstadoCliente {

    private static final String ESPERANDO_SECRETARIA = "Esperando Secretaria";
    private static final String ATENCION_SECRETARIA = "Siendo Atendido Secretaria";
    private static final String ESPERANDO_TECNICO = "Esperando Técnico";
    private static final String ATENCION_TECNICO = "Siendo Atendido Técnico";

    private static Map<String, EstadoCliente> estados;
    private String estado;

    private EstadoCliente(String estado){
        this.estado = estado;
    }

    public static EstadoCliente getInstanceEsperandoSecretaria(){
        if(estados == null) estados = new Hashtable<>();
        return estados.computeIfAbsent("ES", k -> new EstadoCliente(ESPERANDO_SECRETARIA));
    }
    public static EstadoCliente getInstanceAtencionSecretaria(){
        if(estados == null) estados = new Hashtable<>();
        return estados.computeIfAbsent("SAS", k -> new EstadoCliente(ATENCION_SECRETARIA));
    }
    public static EstadoCliente getInstanceEsperandoTecnico(){
        if(estados == null) estados = new Hashtable<>();
        return estados.computeIfAbsent("ET", k -> new EstadoCliente(ESPERANDO_TECNICO));
    }
    public static EstadoCliente getInstanceAtencionTecnico(){
        if(estados == null) estados = new Hashtable<>();
        return estados.computeIfAbsent("SAT", k -> new EstadoCliente(ATENCION_TECNICO));
    }


}
