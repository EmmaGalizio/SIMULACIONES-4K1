package k1.grupo.p.simulacionestp5colas.modelo.colas;


import java.util.Hashtable;
import java.util.Map;

public class EstadoCliente {

    public static final String ESPERANDO_CASETA = "Esperando Caseta";
    public static final String SIENDO_ATENDIDO_CASETA = "Siendo Atendido Caseta";
    public static final String ESPERANDO_NAVE = "Esperando Nave";
    public static final String SIENDO_ATENDIDO_NAVE = "Siendo Atendido Nave";
    public static final String ESPERANDO_OFICINA = "Esperando Oficina";
    public static final String SIENDO_ATENDIDO_OFICINA = "Siendo Atendido Oficina";
    public static final String ATENCION_FINALIZADA = "Atencion Finalizada";


    private Map<String, EstadoCliente> estadosPosiblesMap;

    private String estado;

    private EstadoCliente(String estado){
        this.estado = estado;
    }

    private void inicializarMap(){
        if(estadosPosiblesMap == null) estadosPosiblesMap = new Hashtable<>();
    }

    public EstadoCliente getInstanceEsperandoCaseta(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("EC")){
            estadosPosiblesMap.put("EC", new EstadoCliente(ESPERANDO_CASETA));
        }
        return estadosPosiblesMap.get("EC");
    }
    public EstadoCliente getInstanceEsperandoNave(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("EN")){
            estadosPosiblesMap.put("EN", new EstadoCliente(ESPERANDO_NAVE));
        }
        return estadosPosiblesMap.get("EN");
    }

    public EstadoCliente getInstanceEsperandoOficina(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("EO")){
            estadosPosiblesMap.put("EO", new EstadoCliente(ESPERANDO_OFICINA));
        }
        return estadosPosiblesMap.get("EO");
    }
    public EstadoCliente getInstanceAtencionCaseta(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("SAC")){
            estadosPosiblesMap.put("SAC", new EstadoCliente(SIENDO_ATENDIDO_CASETA));
        }
        return estadosPosiblesMap.get("SAC");
    }
    public EstadoCliente getInstanceAtencionNave(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("SAN")){
            estadosPosiblesMap.put("SAN", new EstadoCliente(SIENDO_ATENDIDO_NAVE));
        }
        return estadosPosiblesMap.get("SAN");
    }

    public EstadoCliente getInstanceAtencionOficina(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("SAO")){
            estadosPosiblesMap.put("SAO", new EstadoCliente(SIENDO_ATENDIDO_OFICINA));
        }
        return estadosPosiblesMap.get("SAO");
    }

    public EstadoCliente getInstanceAtencionFinalizada(){
        inicializarMap();
        if(!estadosPosiblesMap.containsKey("AF")){
            estadosPosiblesMap.put("AF", new EstadoCliente(ATENCION_FINALIZADA));
        }
        return estadosPosiblesMap.get("AF");
    }

    public String getEstado() {
        return estado;
    }
}
