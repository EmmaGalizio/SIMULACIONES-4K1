package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.VaribaleAleatoria;
import k1.grupo.p.simulacionestp5colas.modelo.colas.Cliente;
import k1.grupo.p.simulacionestp5colas.modelo.colas.EstadoCliente;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EstadoServidor;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;
import lombok.Data;


@Data
public class EventoFinAtencionCaseta extends Evento{

    private Pseudoaleatorio randomTiempoAtencion;
    private float tiempoAtencion;

    public EventoFinAtencionCaseta() {
        this.setNombreEvento("Fin At. Caseta");
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   Pseudoaleatorio randomCUBase,
                                   IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                   ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV vectorEstadoActual = (VectorEstadoITV) estadoAnterior.clone();
        vectorEstadoActual.setReloj(this.getMomentoEvento());
        //Cliente es un atributo de la superclase evento
        Servidor empleadoCaseta = cliente.getServidorActual();
        //El objeto cliente que está en este objeto Evento no es el mismo que el que está en la lista de clientes del vector
        //Porque al clonarse el vector también se clonan los clientes, porque cambia el estado de un vector a otro
        //En cambio los servidores son siempre los mismos así que no importa de donde lo saco.
        //Pero al cliente es necesario buscarlo desde el vector que se está procesando actualmente
        Cliente clienteActual = vectorEstadoActual.buscarClientePorId(cliente.getNumeroCliente());
        clienteActual.setHoraLlegadaNave(vectorEstadoActual.getReloj());
        //Verificar hay algún circuito de la nave libre o si tiene que esperar en la cola
        Servidor empleadoNaveLibre = this.buscarCircuitoNaveLibre(vectorEstadoActual);
        if(empleadoNaveLibre == null){
            //Están todos los circuitos de la nave ocupados
            vectorEstadoActual.agregarClienteColaNave(clienteActual);
            clienteActual.setEstado(EstadoCliente.getInstanceEsperandoNave());
        }else{
            //Hay al menos un circuito de la nave libre
            clienteActual.setHoraInicioAtencionNave(vectorEstadoActual.getReloj());
            //Siendo atendido por la nave
            clienteActual.setEstado(EstadoCliente.getInstanceAtencionNave());
            clienteActual.setServidorActual(empleadoNaveLibre);
            empleadoNaveLibre.setEstado(EstadoServidor.getInstanceServidorOcupado());
            //Creo que no se usaría en ningun lado la referencia al cliente que está almacenada en el servidor
            //Pero con tanto quilombo de objetos y referencias no viene mal
            empleadoNaveLibre.setClienteActual(clienteActual);
            //Como inmediatamente el cliente empezó a ser atendido en la nave, se crea el evento de fin de atención.
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioNave());
            VaribaleAleatoria tiempoAtencionNave = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);

            EventoFinInspeccion eventoFinInspeccion = new EventoFinInspeccion();
            //Me parece que se tendría que ir poniendo el número nomás, aunque no va a variar porque una vez que se le pasa la referencia no se toca más
            eventoFinInspeccion.setRandomFinInspeccion(randomCUBase);
            eventoFinInspeccion.setTiempoFinInspeccion(tiempoAtencionNave.getRandomGenerado());
            eventoFinInspeccion.setMomentoEvento(vectorEstadoActual.getReloj()+eventoFinInspeccion.getTiempoFinInspeccion());
            eventoFinInspeccion.setCliente(clienteActual);
            randomCUBase = tiempoAtencionNave.getSiguienteRandomBase();
            //Se creó el evento de fin de inspección, ahora se agrega al heap
            heapEventos.add(eventoFinInspeccion);
            //Es necesario actualizar el evento de fin de inspección en el vector de estado para poder mostrarlo
            //Los arrays con los eventos del vector de estado se clonan cada vez que se clona el vector
            //De esta forma puede variar lo que se muestra en las filas
            vectorEstadoActual.actualizarEventoFinAtencionNave(eventoFinInspeccion, empleadoNaveLibre);
            //Si hay algún empleado de nave libre, quiere decir que, o es el primer cliente que sale de la caseta, o
            //ya hubo un evento de fin de atencion de nave y no había más clientes en la cola de la nave, por lo que
            //el momentoLiberacion del empleado de la nave está seteado (si ya ocurrió un evento de fin de atencion en nave y la cola estaba vacía),
            //o es igual a 0 si es el primer cliente que sale de la caseta, lo que está bien porque quiere decir que el empleado estuvo libre
            //to.do el tiempo. Me parece que este es el único lado donde se actualiza el tiempo libre de los empleados de la nave
            //Porque al salir de la caseta pasa directo a la nave y dejaría de estar libre. Si ocurre un evento de fin de
            //inspección, e inmediatamente el empleado comienza a atender a otro en ningún momento pasa a estar libre.
            //En cambio, si ocurre el evento de fin de inspección pero no hay empleados en la cola de la nave, entonces el empleado
            //de la nave pasa a estar libre y el único momento en que va a empezar a atender a un cliente es en este evento,
            //en el momento en que salga de la caseta.
            vectorEstadoActual.acumularTiempoLibreEmpleadosNave(empleadoNaveLibre);
        }
        //Actualizar acumuladores
        vectorEstadoActual.acumularTiempoTotalCaseta(clienteActual);
        //Verificar si hay alguien esperando para ser atendido en caseta, de ser así generar el evento y actualizar estado del servidor
        //de la caseta.
        Cliente clienteEsperaColaCaseta = vectorEstadoActual.getSiguienteClienteColaCaseta();
        if(clienteEsperaColaCaseta == null){
            //La cola está vacia
            empleadoCaseta.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadoCaseta.setClienteActual(null);
            //Si la cola está vacía entonces el empleado de la caseta pasará a estar libre, y seguirá así hasta que llegue otro
            //cliente al sistema.
            empleadoCaseta.setMomentoLiberacion(vectorEstadoActual.getReloj());
        }else {
            //La cola no está vacía, se recuperó la referencia que tenía la cola al cliente, pero no es el mismo cliente
            //que está en la lista de clientes del vector (porque están clonados) por lo que es encesario recuperarlo del vector
            clienteEsperaColaCaseta = vectorEstadoActual.buscarClientePorId(clienteEsperaColaCaseta.getNumeroCliente());
            //ahora ya está actualizada la referencia al cliente que estaba esperando en la caseta y pasa a ser atendido
            clienteEsperaColaCaseta.setEstado(EstadoCliente.getInstanceAtencionCaseta());
            clienteEsperaColaCaseta.setHoraInicioAtencionCaseta(vectorEstadoActual.getReloj());
            clienteEsperaColaCaseta.setServidorActual(empleadoCaseta);
            empleadoCaseta.setEstado(EstadoServidor.getInstanceServidorOcupado());
            empleadoCaseta.setClienteActual(clienteEsperaColaCaseta);
            //Es necesario crear el evento de fin de atención de este cliente.
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServCaseta());
            VaribaleAleatoria tiempoAtencionCaseta = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            EventoFinAtencionCaseta siguienteFinAtencionCaseta = new EventoFinAtencionCaseta();
            siguienteFinAtencionCaseta.setRandomTiempoAtencion(randomCUBase);
            siguienteFinAtencionCaseta.setTiempoAtencion(tiempoAtencionCaseta.getRandomGenerado());
            siguienteFinAtencionCaseta.setMomentoEvento(vectorEstadoActual.getReloj()+ siguienteFinAtencionCaseta.getTiempoAtencion());
            heapEventos.add(siguienteFinAtencionCaseta);
            //Se creó el evento de fin de atención de caseta y se agregó al heap
            //Es necesario actualizar los acumuladores de tiempo de espera en cola de caseta, porque un cliente salio
            //de la cola de la caseta
            vectorEstadoActual.actualizarEventoFinAtencionCaseta(siguienteFinAtencionCaseta, empleadoCaseta);
            //Se acumula el tiempo de espera en la cola de la caseta del cliente que pasó a ser atendido.
            vectorEstadoActual.acumularTiempoColaCaseta(clienteEsperaColaCaseta);
            randomCUBase = tiempoAtencionCaseta.getSiguienteRandomBase();
            //En este caso, el empleado de la caseta nunca deja de estar libre por lo que no se actualiza el acumulador de tiempo
            //libre de la caseta
        }
        vectorEstadoActual.setSiguientePseudoCU(randomCUBase);

        return vectorEstadoActual;
    }

    private Servidor buscarCircuitoNaveLibre(VectorEstadoITV vectorEstadoITV){
        for(Servidor servidor: vectorEstadoITV.getEmpleadosNave()){
            if(servidor.estaLibre()) return servidor;
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinAtencionCaseta evento = new EventoFinAtencionCaseta();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomTiempoAtencion((Pseudoaleatorio) randomTiempoAtencion.clone());
        evento.setTiempoAtencion(tiempoAtencion);
        return evento;
    }
}
