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
    private EventoLlegadaCliente ProximaLlegadaCliente;
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
    //SE PODRÍAN AGREGAR AUCUMULADORES PARA EL TIEMPO LIBRE DE CADA TIPO DE SERVIDOR, COMO PARA AGREGAR MÁS MÉTRICAS
    //PERO EN ESTE MOMENTO NO ME ACUERDO NI ME SALEN PENSAR CÓMO CALCULAR ESE TIEMPO LIBRE
    //QUE CAMBIARÍA CUANDO EL SERVIDOR PASA DE ESTAR LIBRE A OCUPADO. CAPAZ QUE DENTRO DEL SERVIDOR SE DEBERÍA GUARDAR
    //EL ATRIBUTO DE CUANDO QUEDÓ LIBRE, Y SE ACTUALIZARÍA ESE ATRIBUTO EN EL RESPECTIVO EVENTO DE FIN DE ATENCION DE CADA SERVIDOR
    private float acumuladorTiempoLibreEmpleadosCaseta; //IMPLEMENTADO
    private float acumuladorTiempoLibreEmpleadosNave; //Implementado
    private float acumuladorTiempoLibreEmpleadosOficina; //Implementado
    private List<Cliente> clientes;

    //Este atributo es solo para el actualizar el pseudo en el controlador
    //está acá solo para facilitar el diseño del programa, no está bien que esté acá.
    private Pseudoaleatorio siguientePseudoCU;


    @Override
    @SneakyThrows
    public Object clone() {
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
    public int getCantClientesColaCaseta(){
        return colaCaseta.size();
    }
    public int getCantClientesColaNave(){
        return colaNave.size();
    }
    public int getCantClientesColaOficina(){
        return colaOficina.size();
    }

    public void incremetarLlegadaVehiculos(){
        contadorVehiculos++;
    }

    public void agregarCliente(Cliente cliente) {
        if(clientes == null) clientes = new LinkedList<>();
        clientes.add(cliente);
    }

    public void agregarClienteColaCaseta(Cliente cliente) {
        if(colaCaseta == null) colaCaseta = new ArrayDeque<>();
        colaCaseta.add(cliente);
    }

    public void actualizarEventoFinAtencionCaseta(EventoFinAtencionCaseta evento, Servidor empleadoCaseta){
        finAtencionCaseta[empleadoCaseta.getId()-1] = evento;
    }

    public Cliente buscarClientePorId(int numeroCliente) {

        for(Cliente cliente: clientes){
            if(cliente.tieneId(numeroCliente)){
                return cliente;
            }
        }
        return null;
    }

    public void agregarClienteColaNave(Cliente clienteActual) {
        if(colaNave == null) colaNave = new ArrayDeque<>();
        colaNave.add(clienteActual);
    }

    public void acumularTiempoTotalCaseta(Cliente clienteActual) {
        acumuladorTiempoEsperaCaseta += (clienteActual.getHoraLlegadaNave()-clienteActual.getHoraLlegadaCaseta());
    }
    public void acumularTiempoColaCaseta(Cliente clienenteActual){
        acumuladorTiempoEsperaColaCaseta += (clienenteActual.getHoraInicioAtencionCaseta() - clienenteActual.getHoraLlegadaCaseta());
    }

    public Cliente getSiguienteClienteColaCaseta() {
        if(colaCaseta != null){
            return colaCaseta.poll();
        }
        return null;
    }

    public void actualizarEventoFinAtencionNave(EventoFinInspeccion eventoFinInspeccion, Servidor empleadoNaveLibre) {
        finInspeccion[empleadoNaveLibre.getId()-1] = eventoFinInspeccion;
    }

    public void acumularTiempoLibreEmpleadosCaseta(Servidor empleadoCaseta){
        acumuladorTiempoLibreEmpleadosCaseta+= (this.reloj - empleadoCaseta.getMomentoLiberacion());
    }
    public void acumularTiempoLibreEmpleadosNave(Servidor empleadoNave){
        acumuladorTiempoLibreEmpleadosNave+= (this.reloj - empleadoNave.getMomentoLiberacion());
    }
    public void acumularTiempoLibreEmpleadosOficina(Servidor empleadoOficina){
        acumuladorTiempoLibreEmpleadosOficina+= (this.reloj - empleadoOficina.getMomentoLiberacion());
    }

    public void agregarClienteColaOficina(Cliente clienteActual) {
        if(colaOficina == null) colaOficina = new ArrayDeque<>();
        colaOficina.add(clienteActual);
    }

    public void actualizarEventoFinAtencion(EventoFinAtencion eventoFinAtencion, Servidor empleadoOficinaLibre) {
        finAtencionOficina[empleadoOficinaLibre.getId()-1] = eventoFinAtencion;
    }

    public void acumularTiempoTotalNave(Cliente clienteActual) {
        acumuladorTiempoEsperaNave += (this.reloj-clienteActual.getHoraLlegadaNave());
    }

    public Cliente getsiguienteClienteColaNave() {
        if(colaNave != null){
            return colaNave.poll();
        }
        return null;
    }

    public void acumularTiempoColaNave(Cliente siguienteClienteInspeccion) {
        acumuladorTiempoEsperaColaNave+= this.reloj - siguienteClienteInspeccion.getHoraLlegadaNave();
    }
    public void acumularTiempoEsperaCola(float momentoIngresoCola){
        acumuladorTotalEsperaCola+=this.reloj - momentoIngresoCola;
    }

    public Cliente getSiguienteClienteColaOficina() {
        if(colaOficina != null) return colaOficina.poll();
        return null;
    }

    public void acumularTiempoColaOficina(Cliente clienteActual) {
        acumuladorTiempoEsperaColaOficina += (this.reloj-clienteActual.getHoraLlegadaOficina());
    }

    public void acumularTiempoEsperaOficina(Cliente clienteActual) {
        acumuladorTiempoEsperaOficina+= this.reloj - clienteActual.getHoraLlegadaOficina();
    }

    public void incremetarVehiculosAtencionFinalizada() {
        this.contadorVehiculosAtencionFinalizada++;
    }

    public void acumularTiempoEnSistema(Cliente clienteActual) {
        this.acumuladorTiempoAtencion+= this.reloj - clienteActual.getHoraLlegadaCaseta();
    }
}
