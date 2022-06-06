package k1.grupo.p.simulacionestp5colas.dto;

import k1.grupo.p.simulacionestp5colas.modelo.colas.eventos.*;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;

@Data
public class VectorEstadoDtoActual {

    private String nombreEvento;
    private float reloj;
    private EventoLlegadaCliente eventoLlegadaCliente;
    private EventoFinAtencionCaseta finAtencionCaseta;
    private EventoFinInspeccion finInspeccion1;
    private EventoFinInspeccion finInspeccion2;
    private EventoFinAtencion finAtencionOficina1;
    private EventoFinAtencion finAtencionOficina2;
    private EventoFinSimulacion finSimulacion;
    private Servidor empleadoCaseta;
    private Servidor inspector1;
    private Servidor inspector2;
    private Servidor oficinista1;
    private Servidor oficinista2;
    private int colaCaseta;
    private int colaNave;
    private int colaOficina;
    private int contadorVehiculos; //Implementado
    private int contadorClientesNoAtendidos;
    private int contadorVehiculosAtencionFinalizada; //Implementado
    private float acumuladorTiempoEsperaColaCaseta; //Implementado
    private float acumuladorTiempoEsperaCaseta; //Implementado
    private float acumuladorTiempoAtencionCaseta;
    private int contadorClientesAtendidosCaseta;
    private float acumuladorTiempoEsperaColaNave; //Implementado
    private float acumuladorTiempoEsperaNave; //Implementado
    private float acumuladorTiempoAtencionNave;
    private int contadorClientesAtendidosNave;
    private float acumuladorTiempoEsperaColaOficina; //Implementado
    private float acumuladorTiempoEsperaOficina; //Implementado
    private float acumuladorTiempoAtencionOficina;
    private float acumuladorTiempoAtencion; //Implementado, se usa para calcular el promedio

    private float acumuladorTotalEsperaCola; //Implementado
    private float acumuladorTiempoLibreEmpleadosCaseta; //IMPLEMENTADO
    private float acumuladorTiempoLibreEmpleadosNave; //Implementado
    private float acumuladorTiempoLibreEmpleadosOficina;
    private int acumuladorLongitudColaNave; //Para calcular la
    //longitud promedio de la cola de la nave, se calcula dividiendolo
    //por el contador de atendidos en caseta


}
