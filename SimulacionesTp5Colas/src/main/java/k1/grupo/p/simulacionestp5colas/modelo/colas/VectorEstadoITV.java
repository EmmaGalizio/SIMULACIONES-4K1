package k1.grupo.p.simulacionestp5colas.modelo.colas;

import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.eventos.*;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.*;

@Data
public class VectorEstadoITV {

    private String nombreEvento;
    private float reloj;
    private EventoLlegadaCliente llegadaCliente;
    private EventoFinAtencionCaseta[] finAtencionCaseta;
    private EventoFinInspeccion[] finInspeccion;
    private EventoFinAtencion[] finAtencionOficina;
    private EventoFinSimulacion finSimulacion;
    //Los servidores pueden ser singleton, pero no sería necesario
    private List<Servidor> empleadosCaseta;
    private List<Servidor> empleadosNave;
    private List<Servidor> empleadosOficina;
    //Para el tableview agregar un "getter" para el size de la cola.
    //por ejemplo getSizeColaCaseta, y en el table view uso como propiedad sizeColaCaseta.
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

    //Este atributo es solo para el actualizar el pseudo en el controlador
    //está acá solo para facilitar el diseño del programa, no está bien que esté acá.
    private Pseudoaleatorio siguientePseudoCU;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        VectorEstadoITV nuevoVector = new VectorEstadoITV();
        //Seteo de valores primitivos
        nuevoVector.setReloj(reloj);
        nuevoVector.setContadorVehiculos(contadorVehiculos);
        nuevoVector.setContadorVehiculosAtencionFinalizada(contadorVehiculosAtencionFinalizada);
        nuevoVector.setAcumuladorTiempoEsperaColaCaseta(acumuladorTiempoEsperaColaCaseta);
        nuevoVector.setAcumuladorTiempoEsperaCaseta(acumuladorTiempoEsperaCaseta);
        nuevoVector.setAcumuladorTiempoEsperaColaNave(acumuladorTiempoEsperaColaNave);
        nuevoVector.setAcumuladorTiempoEsperaNave(acumuladorTiempoEsperaNave);
        nuevoVector.setAcumuladorTiempoEsperaColaOficina(acumuladorTiempoEsperaColaOficina);
        nuevoVector.setAcumuladorTiempoEsperaOficina(acumuladorTiempoEsperaOficina);
        nuevoVector.setAcumuladorTiempoAtencion(acumuladorTiempoAtencion);
        nuevoVector.setAcumuladorTotalEsperaCola(acumuladorTotalEsperaCola);
        //No es necesario clonarlo ni nada de eso porque un string es inmutable
        //Así que si lo modifico directamente se crea un nuevo objeto y el anterior
        //no se modifica
        //igual no debería hacer falta setear el nombre pero ya que se está clonando se copia to-do el objeto
        nuevoVector.setNombreEvento(nombreEvento);
        //Los servidores son siempre los mismos así que se puede copiar la referencia
        nuevoVector.setEmpleadosCaseta(empleadosCaseta);
        nuevoVector.setEmpleadosNave(empleadosNave);
        nuevoVector.setEmpleadosOficina(empleadosOficina);
        //Las colas varían de evento en evento así que es necesario clonarlas
        nuevoVector.setColaCaseta(new ArrayDeque<>(colaCaseta));
        nuevoVector.setColaNave(new ArrayDeque<>(colaNave));
        nuevoVector.setColaOficina(new ArrayDeque<>(colaOficina));
        //La lista de clientes varía en los eventos de llegada y fin de atención, se tiene que clonar
        //nuevoVector.setClientes(new LinkedList<>(clientes));
        this.cloarClientes(nuevoVector);
        this.clonarEventos(nuevoVector);
        nuevoVector.setFinSimulacion(finSimulacion);
        return nuevoVector;
    }

    private void cloarClientes(VectorEstadoITV nuevoVector){
        List<Cliente> nuevosClientes = new LinkedList<>();
        clientes.forEach((cliente)-> nuevosClientes.add((Cliente) cliente.clone()));
        nuevoVector.setClientes(nuevosClientes);
    }

    @SneakyThrows
    private void clonarEventos(VectorEstadoITV nuevoVector) {
        EventoFinAtencionCaseta[] finAtencionCaseta =
                new EventoFinAtencionCaseta[this.finAtencionCaseta.length];
        EventoFinInspeccion[] finInspeccion = new EventoFinInspeccion[this.finInspeccion.length];
        EventoFinAtencion[] finAtencionOficina = new EventoFinAtencion[this.finAtencionOficina.length];

        for(int i = 0; i < finAtencionOficina.length; i++){
            finAtencionOficina[i] = (EventoFinAtencion) this.finAtencionOficina[i].clone();
        }
        for(int i = 0; i < finInspeccion.length; i++){
            finInspeccion[i] = (EventoFinInspeccion) this.finInspeccion[i].clone();
        }
        for(int i = 0; i < finAtencionCaseta.length; i++){
            finAtencionCaseta[i] = (EventoFinAtencionCaseta) this.finAtencionCaseta[i].clone();
        }
        nuevoVector.setFinInspeccion(finInspeccion);
        nuevoVector.setFinAtencionOficina(finAtencionOficina);
        nuevoVector.setFinAtencionCaseta(finAtencionCaseta);
    }

    public void eliminarClienteAtendido(Cliente clienteAtencionFinalizada) {

        Iterator<Cliente> clienteIterator = clientes.iterator();
        while(clienteIterator.hasNext()){
            Cliente cliente = clienteIterator.next();
            if(cliente.equals(clienteAtencionFinalizada)){
                clienteIterator.remove();
                break;
            }
        }

    }
}
