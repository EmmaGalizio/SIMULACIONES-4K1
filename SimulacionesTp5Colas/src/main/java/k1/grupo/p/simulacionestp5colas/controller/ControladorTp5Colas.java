package k1.grupo.p.simulacionestp5colas.controller;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.CambioDistribucionExponencialNeg;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.controller.utils.ConstantesGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.VaribaleAleatoria;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.colas.eventos.*;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EmpleadoCaseta;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.EstadoServidor;
import k1.grupo.p.simulacionestp5colas.modelo.colas.servidor.Servidor;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ControladorTp5Colas {

    @Autowired
    Map<String, IGeneradorRandom> mapGeneradorRandom;
    @Autowired
    CambioDistribucionExponencialNeg generadorExponencialNeg;

    public List<VectorEstadoITV> generarSimulacion(ParametrosItv parametrosItv,
                                                   ParametrosGenerador parametrosGenerador){

        if(parametrosItv == null) throw new IllegalArgumentException("Debe indicar los parametros de la simulacion");
        parametrosItv.validar();
        if(parametrosGenerador == null){
            parametrosGenerador = new ParametrosGenerador();
            parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        }
        IGeneradorRandom generadorRandom = mapGeneradorRandom.get(parametrosGenerador.getMetodoGeneradorRandom());
        parametrosGenerador.setN(1);
        Pseudoaleatorio randomCUBase = generadorRandom.generar(parametrosGenerador)[0];

        EventoLlegadaCliente eventoInicial = new EventoLlegadaCliente();
        randomCUBase = this.generarEventoInicial(generadorRandom,parametrosGenerador,parametrosItv
                                                    ,eventoInicial,randomCUBase);
        EventoFinSimulacion eventoFinal = this.generarEventoFinal(parametrosItv);
        VectorEstadoITV vectorEstadoAnterior = this.inicializarVectorEstado(eventoInicial,parametrosItv);
        vectorEstadoAnterior.setFinSimulacion(eventoFinal);

        //Crea un heap ascendente (menor en la raiz)
        TSBHeap<Evento> eventosHeap = new TSBHeap<>();
        eventosHeap.add(eventoInicial);
        eventosHeap.add(eventoFinal);

        Evento eventoActual = eventosHeap.remove();
        VectorEstadoITV vectorEstadoActual;
        int cantEventos = 1;
        int ultimaFila = parametrosItv.getMostrarFilaDesde() + parametrosItv.getCantFilasMostrar();
        EventoFinAtencion finAtencionCliente = null;
        List<VectorEstadoITV> simulacionItv = new LinkedList<>();
        while(true){
            //La clase VectorEstadoITV implementa el método clone()
            //Al momento de procesar el evento en vez de instanciar un Vector vacío
            //se llama al método clone del vector anterior que ya tiene todos los datos
            //correspondientes a los contadores, acumuladores, clientes, estados de servidores
            //y valores de los siguientes eventos a ejecutarse, entonces solo
            //es necesario que cada evento modifique los valores correspondientes a dicho evento.
            //Por cada tipo de evento, el vector de estado tiene un array de ese tipo
            //de evento con la misma cantidad de elementos que la cantidad de servidores.
            //Por ejemplo, si la nave tiene 2 empleados, entonces el arreglo que corresponde a
            //los eventos de fin de inspeccón tendrá dos elementos.
            //Además, cada servidor tiene un identificador, por lo que si se quiere cambiar
            //el fin de inspección del inspector 1, se deberá cambiar el elemento 0 del array
            //de eventos de fin de inspección (i-1)
            vectorEstadoActual = eventoActual.procesarEvento(vectorEstadoAnterior,parametrosGenerador
                    ,randomCUBase,generadorRandom,generadorExponencialNeg, eventosHeap);


            if(finAtencionCliente != null){
                vectorEstadoActual.eliminarClienteAtendido(finAtencionCliente.getClienteAtencionFinalizada());
                finAtencionCliente = null;
            }
            //Por cada evento que se procese se deberá actualizar el siguientePseudoCU
            //del vector de estado, para poder utilizarlo para generar el próximo evento.
            randomCUBase = vectorEstadoActual.getSiguientePseudoCU();
            if(cantEventos >= parametrosItv.getMostrarFilaDesde() && cantEventos < ultimaFila){
                simulacionItv.add(vectorEstadoActual);
            }
            vectorEstadoAnterior = vectorEstadoActual;
            if(eventoActual instanceof EventoFinSimulacion) break;
            if(eventoActual instanceof  EventoFinAtencion)
                finAtencionCliente = (EventoFinAtencion) eventoActual;
            eventoActual = eventosHeap.remove();
        }
        //En un primer momento se obtienen las instancias de los generadores randoms y de las variables
        //aleatorias
        //Despues se crea un evento inicial del tipo EventoLlegadaCliente a mano,
        //Y a partir de ese evento se crea el Vector de estado 0, se incluye ese evento
        //en el Heap de eventos y se pasa al bucle.
        //Dentro del bucle se saca ese primer evento del Heap y se procesa con
        //el método procesarEvento del correspondiente evento.
        //Dentro de ese método es cuando se crea el vector de estado i copiando los
        //datos que se deben mantener, se crea el evento correspondiente (por ejemplo, si es un
        //evento de llegada se debe crear el próximo evento de llegada, o si es un evento de fin de atención
        //de caseta se deben actualizar los acumuladores correspondientes, verificar la cola correspondiente
        //y crear el proximo evento de fin de atención de caseta que corresponda.
        //También calculo que en ese mismo evento habría que verificar si el servidor de la nave está ocupado
        //y crear el evento de atención de la nace correspondiente para el cliente que dejó la caseta, o, si está,
        //ocupado agregar ese cliente a la cola de la nave.
        //Y así con todos los eventos.
        //El controlador lo único que debería hacer es ir recorriendo el bucle y sacando eventos del heap.


        //La lista de clientes debe ser clonada, no se puede solo copiar la referencia
        //Porque puede ser necesario mostrar un cliente en el vector i-1, pero ser eliminado en el
        //vector i.


        return simulacionItv;
    }



    private Pseudoaleatorio generarEventoInicial(IGeneradorRandom generadorRandom,
                                                      ParametrosGenerador parametrosGenerador,
                                                      ParametrosItv parametrosItv,
                                                      EventoLlegadaCliente evento,
                                                    Pseudoaleatorio randomCUBase){
        evento.setRandomProxLlegada(randomCUBase);
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpLlegadasClientes());
        VaribaleAleatoria varibaleAleatoria = generadorExponencialNeg.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);

        evento.setTiempoHastaProxLlegada(varibaleAleatoria.getRandomGenerado());
        evento.setMomentoEvento(varibaleAleatoria.getRandomGenerado());

        return varibaleAleatoria.getSiguienteRandomBase();
    }

    private VectorEstadoITV inicializarVectorEstado(EventoLlegadaCliente eventoInicial,
                                                    ParametrosItv parametrosItv) {

        VectorEstadoITV vectorEstadoITV = new VectorEstadoITV();
        vectorEstadoITV.setReloj(0); //no debería hacer falta;
        vectorEstadoITV.setColaOficina(new ArrayDeque<>());
        vectorEstadoITV.setColaNave(new ArrayDeque<>());
        vectorEstadoITV.setColaCaseta(new ArrayDeque<>());
        vectorEstadoITV.setLlegadaCliente(eventoInicial);
        vectorEstadoITV.setNombreEvento(eventoInicial.getNombreEvento());
        vectorEstadoITV.setEmpleadosNave(this.generarEmpleadosNave(parametrosItv));
        vectorEstadoITV.setEmpleadosCaseta(this.generarEmpleadosCaseta(parametrosItv));
        vectorEstadoITV.setEmpleadosOficina(this.generarEmpleadosOficina(parametrosItv));
        vectorEstadoITV.setClientes(new LinkedList<>());
        this.inicializarVectoresDeEventos(vectorEstadoITV,parametrosItv);


        return vectorEstadoITV;
    }

    private void inicializarVectoresDeEventos(VectorEstadoITV vectorEstadoITV,
                                              ParametrosItv parametrosItv){
        vectorEstadoITV.setFinAtencionCaseta(new EventoFinAtencionCaseta[parametrosItv.getCantEmpCaseta()]);
        vectorEstadoITV.setFinAtencionOficina(new EventoFinAtencion[parametrosItv.getCantEmpOficina()]);
        vectorEstadoITV.setFinInspeccion(new EventoFinInspeccion[parametrosItv.getCantEmpNave()]);

    }

    private List<Servidor> generarEmpleadosCaseta(ParametrosItv parametrosItv){
        List<Servidor> empleadosCaseta = new ArrayList<>(parametrosItv.getCantEmpCaseta());
        for(int i = 1; i <= parametrosItv.getCantEmpCaseta(); i++){
            Servidor servidor = new EmpleadoCaseta();
            servidor.setNombre("Caseta-"+i);
            servidor.setId(i);
            servidor.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadosCaseta.add(servidor);
        }
        return empleadosCaseta;
    }
    private List<Servidor> generarEmpleadosNave(ParametrosItv parametrosItv){
        List<Servidor> empleadosNave = new ArrayList<>(parametrosItv.getCantEmpNave());
        for(int i = 1; i <= parametrosItv.getCantEmpCaseta(); i++){
            Servidor servidor = new EmpleadoCaseta();
            servidor.setNombre("Inspector-"+i);
            servidor.setId(i);
            servidor.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadosNave.add(servidor);
        }
        return empleadosNave;
    }
    private List<Servidor> generarEmpleadosOficina(ParametrosItv parametrosItv){
        List<Servidor> empleadosOficina = new ArrayList<>(parametrosItv.getCantEmpOficina());
        for(int i = 1; i <= parametrosItv.getCantEmpCaseta(); i++){
            Servidor servidor = new EmpleadoCaseta();
            servidor.setNombre("Oficina-"+i);
            servidor.setId(i);
            servidor.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadosOficina.add(servidor);
        }
        return empleadosOficina;
    }

    private EventoFinSimulacion generarEventoFinal(ParametrosItv parametrosItv){
        EventoFinSimulacion finSimulacion= new EventoFinSimulacion();
        finSimulacion.setMomentoEvento(parametrosItv.getMaxMinutosSimular());
        return finSimulacion;
    }


}
