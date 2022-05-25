package k1.grupo.p.simulacionestp5colas.modelo.colas;

import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
public class VectorEstadoITV {

    private String nombreEvento;
    private float reloj;
    //Atributos de eventos
    //Atributos llegada cliente


    //Los servidores pueden ser singleton, pero no sería necesario
    private Servidor empleadoCaseta;
    private Servidor empleadoNave;
    //Falta otro empleado de nave???
    private Servidor empleadoOficina1;
    private Servidor empleadoOficina2;
    private Queue<Cliente> colaCaseta;
    private Queue<Cliente> colaNave;
    private Queue<Cliente> colaOficina;
    //Acumuladores para estadisticas;
    private int contadorVehiculos;
    private int contadorVehiculosAtencionFinalizada;
    private float acumuladorTiempoEsperaColaCaseta;
    private float acumuladorTiempoEsperaCaseta; //acumulador del tiempo que pasa entre que llega a la cola
    //hasta que finaliza la atencion en la caseta;
    private float acumuladorTiempoEsperaColaNave;
    private float acumuladorTiempoEsperaNave;
    private float acumuladorTiempoEsperaColaOficina;
    private float acumuladorTiempoEsperaOficina;
    private float acumuladorTiempoAtencion; //Acumulador del tiempo total desde que llega al sistema
    //hasta que sale al finalizar la atención en la oficina
    private float acumuladorTotalEsperaCola;
    private List<Cliente> clientes;




}
