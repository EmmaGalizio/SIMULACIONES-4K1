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
public class EventoFinInspeccion extends Evento{

    private Pseudoaleatorio randomFinInspeccion;
    private float tiempoFinInspeccion;

    public EventoFinInspeccion() {
        this.setNombreEvento("Fin Inspección");
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   Pseudoaleatorio randomCUBase, IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                   ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV vectorEstadoActual = (VectorEstadoITV) estadoAnterior.clone();
        vectorEstadoActual.setReloj(this.momentoEvento);
        vectorEstadoActual.setNombreEvento(this.nombreEvento);
        vectorEstadoActual.incrementarContadorAtendidosNave();
        Servidor inspectorAnterior = cliente.getServidorActual();

        Cliente clienteActual = vectorEstadoActual.buscarClientePorId(cliente.getNumeroCliente());
        clienteActual.setHoraLlegadaOficina(this.momentoEvento);
        Servidor inspector = vectorEstadoActual.buscarEmpNavePorId(inspectorAnterior.getId());

        //Una vez que el cliente sale de la inspección debe pasar por la oficina a retirar la documentación,
        //Para eso, debe fijarse si algún empleado de la oficina está libre, si lo está, entonces directamente pasa a ser atendido
        //sino, pasa a esperar en la cola de la oficina.
        Servidor empleadoOficinaLibre = this.buscarEmpleadoOficinaLibre(vectorEstadoActual);

        if(empleadoOficinaLibre == null){
            //No hay ningún empleado de la oficina libre
            vectorEstadoActual.agregarClienteColaOficina(clienteActual);
            clienteActual.setEstado(EstadoCliente.getInstanceEsperandoOficina());

        }else{
            //Hay al menos un oficinista libre otro cambio hecho
            clienteActual.setHoraInicioAtencionOficina(this.momentoEvento);
            clienteActual.setEstado(EstadoCliente.getInstanceAtencionOficina());
            clienteActual.setServidorActual(empleadoOficinaLibre);
            empleadoOficinaLibre.setEstado(EstadoServidor.getInstanceServidorOcupado());
            empleadoOficinaLibre.setClienteActual(clienteActual);
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioOficina());
            parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
            VaribaleAleatoria tiempoAtencionOficina = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            EventoFinAtencion eventoFinAtencion = new EventoFinAtencion();
            eventoFinAtencion.setRandomAtencionOficina(randomCUBase);
            eventoFinAtencion.setTiempoAtencionOficina(tiempoAtencionOficina.getRandomGenerado());
            eventoFinAtencion.setMomentoEvento(this.momentoEvento+eventoFinAtencion.getTiempoAtencionOficina());
            eventoFinAtencion.setCliente(clienteActual);
            randomCUBase = tiempoAtencionOficina.getSiguienteRandomBase();
            heapEventos.add(eventoFinAtencion);
            vectorEstadoActual.actualizarEventoFinAtencion(eventoFinAtencion, empleadoOficinaLibre);
            vectorEstadoActual.acumularTiempoLibreEmpleadosOficina(empleadoOficinaLibre);
            //Hasta acá el cliente sale de la inspección y pasa directo para la oficina a que lo atiendanm se crea el
            //evento de fin de atención y se actualiza el heap de eventos y el evento correspondiente al empleado
            //que empezó a atender dentro del vector de estado
        }

        vectorEstadoActual.acumularTiempoTotalNave(clienteActual);
        vectorEstadoActual.acumularTiempoAtencionNave(clienteActual);

        vectorEstadoActual.acumularLongXTiempoColaNave();
        Cliente siguienteClienteInspeccion = vectorEstadoActual.getsiguienteClienteColaNave();
        vectorEstadoActual.setMomentoUltimaModColaNave(this.momentoEvento);
        if(siguienteClienteInspeccion == null){
            inspector.setEstado(EstadoServidor.getInstanceServidorLibre());
            inspector.setMomentoLiberacion(this.momentoEvento);
            inspector.setClienteActual(null);
            vectorEstadoActual.actualizarEventoFinAtencionNave(null,inspector);
            vectorEstadoActual.getFinInspeccion()[inspector.getId()-1]=null;
        } else{
            //Hay clientes esperando para ser atendidos por el inspector
            siguienteClienteInspeccion = vectorEstadoActual.buscarClientePorId(siguienteClienteInspeccion.getNumeroCliente());
            siguienteClienteInspeccion.setEstado(EstadoCliente.getInstanceAtencionNave());
            siguienteClienteInspeccion.setHoraInicioAtencionNave(this.momentoEvento);
            siguienteClienteInspeccion.setServidorActual(inspector);

            inspector.setClienteActual(siguienteClienteInspeccion);
            inspector.setEstado(EstadoServidor.getInstanceServidorOcupado());

            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioNave());
            parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
            VaribaleAleatoria tiempoFinInspeccion = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
            EventoFinInspeccion eventoFinInspeccion = new EventoFinInspeccion();
            eventoFinInspeccion.setRandomFinInspeccion(randomCUBase);
            eventoFinInspeccion.setTiempoFinInspeccion(tiempoFinInspeccion.getRandomGenerado());
            eventoFinInspeccion.setMomentoEvento(this.momentoEvento+eventoFinInspeccion.getTiempoFinInspeccion());
            eventoFinInspeccion.setCliente(siguienteClienteInspeccion);
            heapEventos.add(eventoFinInspeccion);

            vectorEstadoActual.actualizarEventoFinAtencionNave(eventoFinInspeccion,inspector);
            vectorEstadoActual.acumularTiempoColaNave(siguienteClienteInspeccion);
            vectorEstadoActual.acumularTiempoEsperaCola(siguienteClienteInspeccion.getHoraLlegadaNave());
            randomCUBase = tiempoFinInspeccion.getSiguienteRandomBase();

        }

        vectorEstadoActual.setSiguientePseudoCU(randomCUBase);

        return vectorEstadoActual;
    }

    private Servidor buscarEmpleadoOficinaLibre(VectorEstadoITV vectorEstadoActual) {
        for(Servidor empleadoOficina: vectorEstadoActual.getEmpleadosOficina()){
            if(empleadoOficina.estaLibre()) return empleadoOficina;
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinInspeccion evento = new EventoFinInspeccion();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomFinInspeccion((Pseudoaleatorio) randomFinInspeccion.clone());
        evento.setTiempoFinInspeccion(tiempoFinInspeccion);
        return evento;
    }
}
