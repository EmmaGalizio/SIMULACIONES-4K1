package k1.grupo.p.simulacionestp5colas.modelo.colas;

import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.eventos.*;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EmpleadoCaseta;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EmpleadoNave;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EmpleadoOficina;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class VectorEstadoITV {

    private String nombreEvento;
    private float reloj;
    private EventoLlegadaCliente proximaLlegadaCliente;
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
    private int contadorVehiculos; //Implementado
    private int contadorClientesNoAtendidos;
    private int contadorVehiculosAtencionFinalizada; //Implementado
    private float acumuladorTiempoEsperaColaCaseta; //Implementado
    private float acumuladorTiempoEsperaCaseta; //Implementado
    private float acumuladorTiempoAtencionCaseta;
    private int contadorClientesAtendidosCaseta;
    //acumulador del tiempo que pasa entre que llega a la cola
    //hasta que finaliza la atencion en la caseta;
    private float acumuladorTiempoEsperaColaNave; //Implementado
    private float acumuladorTiempoEsperaNave; //Implementado
    private float acumuladorTiempoAtencionNave;
    private int contadorClientesAtendidosNave;
    private float acumuladorTiempoEsperaColaOficina; //Implementado
    private float acumuladorTiempoEsperaOficina; //Implementado
    private float acumuladorTiempoAtencionOficina;
    private float acumuladorTiempoAtencion; //Implementado, se usa para calcular el promedio
    //De tiempo que pasa un cliente con atención finalizada dentro del ITV. O sea, se usa con el contador
    //de vehículos con atención finalizada
    //Acumulador del tiempo total desde que llega al sistema
    //hasta que sale al finalizar la atención en la oficina
    private float acumuladorTotalEsperaCola; //Implementado
    //SE PODRÍAN AGREGAR AUCUMULADORES PARA EL TIEMPO LIBRE DE CADA TIPO DE SERVIDOR, COMO PARA AGREGAR MÁS MÉTRICAS
    //PERO EN ESTE MOMENTO NO ME ACUERDO NI ME SALEN PENSAR CÓMO CALCULAR ESE TIEMPO LIBRE
    //QUE CAMBIARÍA CUANDO EL SERVIDOR PASA DE ESTAR LIBRE A OCUPADO. CAPAZ QUE DENTRO DEL SERVIDOR SE DEBERÍA GUARDAR
    //EL ATRIBUTO DE CUANDO QUEDÓ LIBRE, Y SE ACTUALIZARÍA ESE ATRIBUTO EN EL RESPECTIVO EVENTO DE FIN DE ATENCION DE CADA SERVIDOR
    //Tal como está implementado se actualiza este contador solo cuando pasa de estar libre a ocupado y en
    //el evento de fin de simulación, no se actualiza linea a línea, no sé si dejarlo así o cambiarlo
    //El resultado final no va a cambiar, además la única forma de cortar una simulación es mediante un evento de fin
    private float acumuladorTiempoLibreEmpleadosCaseta; //IMPLEMENTADO
    private float acumuladorTiempoLibreEmpleadosNave; //Implementado
    private float acumuladorTiempoLibreEmpleadosOficina; //Implementado
    private int acumuladorLongitudColaNave; //Para calcular la
    //longitud promedio de la cola de la nave, se calcula dividiendolo
    //por el contador de atendidos en caseta
    private List<Cliente> clientes;

    //Este atributo es solo para el actualizar el pseudo en el controlador
    //está acá solo para facilitar el diseño del programa, no está bien que esté acá.
    private Pseudoaleatorio siguientePseudoCU;

    private float acumuladorLongColaNaveXTiempoLong;
    private float momentoUltimaModColaNave;


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
        nuevoVector.setAcumuladorTiempoLibreEmpleadosCaseta(acumuladorTiempoLibreEmpleadosCaseta);
        nuevoVector.setAcumuladorTiempoLibreEmpleadosNave(acumuladorTiempoLibreEmpleadosNave);
        nuevoVector.setAcumuladorTiempoLibreEmpleadosOficina(acumuladorTiempoLibreEmpleadosOficina);
        nuevoVector.setContadorClientesAtendidosCaseta(contadorClientesAtendidosCaseta);
        nuevoVector.setContadorClientesAtendidosNave(contadorClientesAtendidosNave);
        nuevoVector.setContadorClientesNoAtendidos(contadorClientesNoAtendidos);
        nuevoVector.setAcumuladorTiempoAtencionOficina(acumuladorTiempoAtencionOficina);
        nuevoVector.setAcumuladorTiempoAtencionCaseta(acumuladorTiempoAtencionCaseta);
        nuevoVector.setAcumuladorTiempoAtencionOficina(acumuladorTiempoAtencionOficina);
        nuevoVector.setAcumuladorLongitudColaNave(acumuladorLongitudColaNave);
        nuevoVector.setAcumuladorLongColaNaveXTiempoLong(acumuladorLongColaNaveXTiempoLong);
        nuevoVector.setMomentoUltimaModColaNave(momentoUltimaModColaNave);
        //No es necesario clonarlo ni nada de eso porque un string es inmutable
        //Así que si lo modifico directamente se crea un nuevo objeto y el anterior
        //no se modifica
        //igual no debería hacer falta setear el nombre pero ya que se está clonando se copia to-do el objeto
        nuevoVector.setNombreEvento(nombreEvento);
        //Los servidores son siempre los mismos así que se puede copiar la referencia
        //nuevoVector.setEmpleadosCaseta(empleadosCaseta);
        //nuevoVector.setEmpleadosNave(empleadosNave);
        //nuevoVector.setEmpleadosOficina(empleadosOficina);
        //Al final los servidores no son los mismos, varía su estado de línea a línea por lo que es necesario
        //Poder cambiarlos sin que se modifique el anterior.
        //Esto hace que sea necesario buscar el cliente de la lista de clientes si es que se obtiene en algún
        //momento desde el servidor, pero creo que siempre se obtiene del evento y el vector.
        this.clonarServidores(nuevoVector);
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
    private void clonarServidores(VectorEstadoITV nuevoVector){

        List<Servidor> nuevosEmpleadosCaseta = empleadosCaseta.stream().map(empleado -> (EmpleadoCaseta)empleado.clone())
                .collect(Collectors.toCollection(LinkedList::new));
        List<Servidor> nuevosEmpleadosNave = empleadosNave.stream().map(empleado -> (EmpleadoNave)empleado.clone())
                .collect(Collectors.toCollection(LinkedList::new));
        List<Servidor> nuevosEmpleadosOfi = empleadosOficina.stream().map(empleado -> (EmpleadoOficina)empleado.clone())
                .collect(Collectors.toCollection(LinkedList::new));
        nuevoVector.setEmpleadosCaseta(nuevosEmpleadosCaseta);
        nuevoVector.setEmpleadosNave(nuevosEmpleadosNave);
        nuevoVector.setEmpleadosOficina(nuevosEmpleadosOfi);
    }

    @SneakyThrows
    private void clonarEventos(VectorEstadoITV nuevoVector) {
        EventoFinAtencionCaseta[] finAtencionCaseta =
                new EventoFinAtencionCaseta[this.finAtencionCaseta.length];
        EventoFinInspeccion[] finInspeccion = new EventoFinInspeccion[this.finInspeccion.length];
        EventoFinAtencion[] finAtencionOficina = new EventoFinAtencion[this.finAtencionOficina.length];

        for(int i = 0; i < finAtencionOficina.length; i++){
            if(this.finAtencionOficina[i] != null) {
                finAtencionOficina[i] = (EventoFinAtencion) this.finAtencionOficina[i].clone();
            }
        }
        for(int i = 0; i < finInspeccion.length; i++){
            if(this.finInspeccion[i] != null) {
                finInspeccion[i] = (EventoFinInspeccion) this.finInspeccion[i].clone();
            }
        }
        for(int i = 0; i < finAtencionCaseta.length; i++){
            if(this.finAtencionCaseta[i] != null) {
                finAtencionCaseta[i] = (EventoFinAtencionCaseta) this.finAtencionCaseta[i].clone();
            }
        }
        nuevoVector.setFinInspeccion(finInspeccion);
        nuevoVector.setFinAtencionOficina(finAtencionOficina);
        nuevoVector.setFinAtencionCaseta(finAtencionCaseta);
        nuevoVector.setProximaLlegadaCliente(this.getProximaLlegadaCliente());
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
    public void incrementarClientesNoAtendidos(){
        contadorClientesNoAtendidos++;
    }

    public Servidor buscarOficinistaPorId(int id) {
        for(Servidor servidor: empleadosOficina){
            if(servidor.getId() == id) return servidor;
        }
        return null;
    }
    public Servidor buscarEmpCasetaPorId(int id){
        for(Servidor servidor: empleadosCaseta){
            if(servidor.getId() == id) return servidor;
        }
        return null;
    }
    public Servidor buscarEmpNavePorId(int id){
        for(Servidor servidor: empleadosNave){
            if(servidor.getId() == id) return servidor;
        }
        return null;
    }
    public void incrementarContadorAtendidosCaseta(){
        contadorClientesAtendidosCaseta++;
    }
    public void incrementarContadorAtendidosNave(){
        contadorClientesAtendidosNave++;
    }

    public void acumularTiempoAtencionCaseta(Cliente clienteActual){
        acumuladorTiempoAtencionCaseta+= (this.reloj - clienteActual.getHoraInicioAtencionCaseta());
    }
    public void acumularTiempoAtencionNave(Cliente clienteActual){
        acumuladorTiempoAtencionNave+= (this.reloj - clienteActual.getHoraInicioAtencionNave());
    }
    public void acumularTiempoAtencionOficina(Cliente clienteActual){
        acumuladorTiempoAtencionOficina+= (this.reloj - clienteActual.getHoraInicioAtencionOficina());
    }

    public void acumularLongitudColaNave(){
        acumuladorLongitudColaNave+= colaNave.size();
    }

    public void acumularLongXTiempoColaNave() {
        acumuladorLongColaNaveXTiempoLong+= colaNave.size()*(this.reloj-this.momentoUltimaModColaNave);
    }
}
