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
        vectorEstadoActual.setNombreEvento(this.nombreEvento);
        vectorEstadoActual.incrementarContadorAtendidosCaseta();
        //Cliente es un atributo de la superclase evento
        Servidor empleadoCasetaAnterior = cliente.getServidorActual();
        //El objeto cliente que est?? en este objeto Evento no es el mismo que el que est?? en la lista de clientes del vector
        //Porque al clonarse el vector tambi??n se clonan los clientes, porque cambia el estado de un vector a otro

        Cliente clienteActual = vectorEstadoActual.buscarClientePorId(cliente.getNumeroCliente());
        clienteActual.setHoraLlegadaNave(vectorEstadoActual.getReloj());
        Servidor empleadoCaseta = vectorEstadoActual.buscarEmpCasetaPorId(empleadoCasetaAnterior.getId());
        //Verificar hay alg??n circuito de la nave libre o si tiene que esperar en la cola
        Servidor empleadoNaveLibre = this.buscarCircuitoNaveLibre(vectorEstadoActual);
        if(empleadoNaveLibre == null){
            //Est??n todos los circuitos de la nave ocupados
            vectorEstadoActual.acumularLongXTiempoColaNave();
            vectorEstadoActual.agregarClienteColaNave(clienteActual);
            clienteActual.setEstado(EstadoCliente.getInstanceEsperandoNave());
            vectorEstadoActual.setMomentoUltimaModColaNave(this.momentoEvento);

        }else{
            //Hay al menos un circuito de la nave libre
            clienteActual.setHoraInicioAtencionNave(vectorEstadoActual.getReloj());
            //Siendo atendido por la nave
            clienteActual.setEstado(EstadoCliente.getInstanceAtencionNave());
            clienteActual.setServidorActual(empleadoNaveLibre);
            empleadoNaveLibre.setEstado(EstadoServidor.getInstanceServidorOcupado());
            //Creo que no se usar??a en ningun lado la referencia al cliente que est?? almacenada en el servidor
            //Pero con tanto quilombo de objetos y referencias no viene mal
            empleadoNaveLibre.setClienteActual(clienteActual);
            //Como inmediatamente el cliente empez?? a ser atendido en la nave, se crea el evento de fin de atenci??n.
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioNave());
            parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
            VaribaleAleatoria tiempoAtencionNave = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);

            EventoFinInspeccion eventoFinInspeccion = new EventoFinInspeccion();
            //Me parece que se tendr??a que ir poniendo el n??mero nom??s, aunque no va a variar porque una vez que se le pasa la referencia no se toca m??s
            eventoFinInspeccion.setRandomFinInspeccion(randomCUBase);
            eventoFinInspeccion.setTiempoFinInspeccion(tiempoAtencionNave.getRandomGenerado());
            eventoFinInspeccion.setMomentoEvento(vectorEstadoActual.getReloj()+eventoFinInspeccion.getTiempoFinInspeccion());
            eventoFinInspeccion.setCliente(clienteActual);
            randomCUBase = tiempoAtencionNave.getSiguienteRandomBase();
            //Se cre?? el evento de fin de inspecci??n, ahora se agrega al heap
            heapEventos.add(eventoFinInspeccion);
            //Es necesario actualizar el evento de fin de inspecci??n en el vector de estado para poder mostrarlo
            //Los arrays con los eventos del vector de estado se clonan cada vez que se clona el vector
            //De esta forma puede variar lo que se muestra en las filas
            vectorEstadoActual.actualizarEventoFinAtencionNave(eventoFinInspeccion, empleadoNaveLibre);
            //Si hay alg??n empleado de nave libre, quiere decir que, o es el primer cliente que sale de la caseta, o
            //ya hubo un evento de fin de atencion de nave y no hab??a m??s clientes en la cola de la nave, por lo que
            //el momentoLiberacion del empleado de la nave est?? seteado (si ya ocurri?? un evento de fin de atencion en nave y la cola estaba vac??a),
            //o es igual a 0 si es el primer cliente que sale de la caseta, lo que est?? bien porque quiere decir que el empleado estuvo libre
            //to.do el tiempo. Me parece que este es el ??nico lado donde se actualiza el tiempo libre de los empleados de la nave
            //Porque al salir de la caseta pasa directo a la nave y dejar??a de estar libre. Si ocurre un evento de fin de
            //inspecci??n, e inmediatamente el empleado comienza a atender a otro en ning??n momento pasa a estar libre.
            //En cambio, si ocurre el evento de fin de inspecci??n pero no hay empleados en la cola de la nave, entonces el empleado
            //de la nave pasa a estar libre y el ??nico momento en que va a empezar a atender a un cliente es en este evento,
            //en el momento en que salga de la caseta.
            vectorEstadoActual.acumularTiempoLibreEmpleadosNave(empleadoNaveLibre);
        }
        //Actualizar acumuladores
        vectorEstadoActual.acumularTiempoTotalCaseta(clienteActual);
        vectorEstadoActual.acumularTiempoAtencionCaseta(clienteActual);
        vectorEstadoActual.acumularLongitudColaNave();
        //Verificar si hay alguien esperando para ser atendido en caseta, de ser as?? generar el evento y actualizar estado del servidor
        //de la caseta.
        Cliente clienteEsperaColaCaseta = vectorEstadoActual.getSiguienteClienteColaCaseta();
        if(clienteEsperaColaCaseta == null){
            //La cola est?? vacia
            empleadoCaseta.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadoCaseta.setClienteActual(null);
            //Si la cola est?? vac??a entonces el empleado de la caseta pasar?? a estar libre, y seguir?? as?? hasta que llegue otro
            //cliente al sistema.
            empleadoCaseta.setMomentoLiberacion(vectorEstadoActual.getReloj());
            vectorEstadoActual.actualizarEventoFinAtencionCaseta(null, empleadoCaseta);
            vectorEstadoActual.getFinAtencionCaseta()[empleadoCaseta.getId()-1]=null;
            vectorEstadoActual.setMomentoUltimaLiberacionCaseta(this.momentoEvento);
        }else {
            //La cola no est?? vac??a, se recuper?? la referencia que ten??a la cola al cliente, pero no es el mismo cliente
            //que est?? en la lista de clientes del vector (porque est??n clonados) por lo que es encesario recuperarlo del vector
            clienteEsperaColaCaseta = vectorEstadoActual.buscarClientePorId(clienteEsperaColaCaseta.getNumeroCliente());
            //ahora ya est?? actualizada la referencia al cliente que estaba esperando en la caseta y pasa a ser atendido
            clienteEsperaColaCaseta.setEstado(EstadoCliente.getInstanceAtencionCaseta());
            clienteEsperaColaCaseta.setHoraInicioAtencionCaseta(vectorEstadoActual.getReloj());
            clienteEsperaColaCaseta.setServidorActual(empleadoCaseta);
            empleadoCaseta.setEstado(EstadoServidor.getInstanceServidorOcupado());
            empleadoCaseta.setClienteActual(clienteEsperaColaCaseta);
            //Es necesario crear el evento de fin de atenci??n de este cliente.
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServCaseta());
            parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
            VaribaleAleatoria tiempoAtencionCaseta = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            EventoFinAtencionCaseta siguienteFinAtencionCaseta = new EventoFinAtencionCaseta();
            siguienteFinAtencionCaseta.setRandomTiempoAtencion(randomCUBase);
            siguienteFinAtencionCaseta.setTiempoAtencion(tiempoAtencionCaseta.getRandomGenerado());
            siguienteFinAtencionCaseta.setMomentoEvento(vectorEstadoActual.getReloj()+ siguienteFinAtencionCaseta.getTiempoAtencion());
            siguienteFinAtencionCaseta.setCliente(clienteEsperaColaCaseta);
            heapEventos.add(siguienteFinAtencionCaseta);
            //Se cre?? el evento de fin de atenci??n de caseta y se agreg?? al heap
            //Es necesario actualizar los acumuladores de tiempo de espera en cola de caseta, porque un cliente salio
            //de la cola de la caseta
            vectorEstadoActual.actualizarEventoFinAtencionCaseta(siguienteFinAtencionCaseta, empleadoCaseta);
            //Se acumula el tiempo de espera en la cola de la caseta del cliente que pas?? a ser atendido.
            vectorEstadoActual.acumularTiempoColaCaseta(clienteEsperaColaCaseta);
            vectorEstadoActual.acumularTiempoEsperaCola(clienteEsperaColaCaseta.getHoraLlegadaCaseta());
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
