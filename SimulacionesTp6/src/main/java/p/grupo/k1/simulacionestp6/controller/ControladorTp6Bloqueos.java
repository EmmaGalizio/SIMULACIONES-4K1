package p.grupo.k1.simulacionestp6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.CambioDistribucionExponencialNeg;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.controller.utils.ConstantesGenerador;
import p.grupo.k1.simulacionestp6.modelo.ParametrosCambioDistribucion;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.VaribaleAleatoria;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.*;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.*;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoRungeKutta;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoSimulacion;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.eventos.EventoLlegadaAtaque;

import java.util.*;

@Component
public class ControladorTp6Bloqueos {

    @Autowired
    Map<String, IGeneradorRandom> mapGeneradorRandom;
    @Autowired
    CambioDistribucionExponencialNeg generadorExponencialNeg;

    public ResultadoSimulacion generarSimulacion(ParametrosItv parametrosItv,
                                                   ParametrosGenerador parametrosGenerador){

        if(parametrosItv == null) throw new IllegalArgumentException("Debe indicar los parametros de la simulacion");
        parametrosItv.validar();
        if(parametrosGenerador == null){
            parametrosGenerador = new ParametrosGenerador();
            parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        }
        IGeneradorRandom generadorRandom = mapGeneradorRandom.get(parametrosGenerador.getMetodoGeneradorRandom());
        parametrosGenerador.setN(1);
        parametrosGenerador.setPresicion(4);
        Pseudoaleatorio randomCUBase = generadorRandom.siguientePseudoAleatoreo(parametrosGenerador);

        EventoLlegadaCliente eventoInicial = new EventoLlegadaCliente();
        randomCUBase = this.generarEventoInicial(generadorRandom,parametrosGenerador,parametrosItv
                                                    ,eventoInicial,randomCUBase);
        EventoFinSimulacion eventoFinal = this.generarEventoFinal(parametrosItv);
        VectorEstadoITV vectorEstadoAnterior = this.inicializarVectorEstado(eventoInicial,parametrosItv);
        vectorEstadoAnterior.setFinSimulacion(eventoFinal);

        ResultadoSimulacion resultadoSimulacion = new ResultadoSimulacion();

        List<ResultadoRungeKutta> rungeKuttaAtaqueInicial = new LinkedList<>();

        EventoLlegadaAtaque primerLlegadaAtaque = new EventoLlegadaAtaque();
        randomCUBase = primerLlegadaAtaque.calcularTiempoLlegadaSiguienteAtaque(randomCUBase, generadorRandom,
                parametrosGenerador, rungeKuttaAtaqueInicial);
        //primerLlegadaAtaque.setRandomMomentoAtaque(randomCUBase.getRandom());
        resultadoSimulacion.setEcDiferencialLlegadaAtaque(rungeKuttaAtaqueInicial);
        float momentoAtaqueInicial = primerLlegadaAtaque.getTiempoHastaLlegada();
        //randomCUBase = generadorRandom.siguientePseudoAleatoreo(randomCUBase, parametrosGenerador);
        primerLlegadaAtaque.setMomentoEvento(momentoAtaqueInicial);

        //primerLlegadaAtaque.setTiempoHastaLlegada((float)momentoAtaqueInicial);

        vectorEstadoAnterior.setLlegadaAtaque(primerLlegadaAtaque);

        //Crea un heap ascendente (menor en la raiz)
        TSBHeap<Evento> eventosHeap = new TSBHeap<>();
        eventosHeap.add(eventoInicial);
        eventosHeap.add(eventoFinal);

        eventosHeap.add((EventoLlegadaAtaque)primerLlegadaAtaque.clone());

        Evento eventoActual = eventosHeap.remove();
        VectorEstadoITV vectorEstadoActual;
        int cantEventos = 1;
        int ultimaFila = parametrosItv.getMostrarFilaDesde() + parametrosItv.getCantFilasMostrar();
        EventoFinAtencion finAtencionCliente = null;
        List<VectorEstadoITV> simulacionItv = new LinkedList<>();
        simulacionItv.add(vectorEstadoAnterior);
        boolean ecBloqueoLlegCargada = false, ecBloqueoNaveCargada = false;

        while(true){
            vectorEstadoActual = eventoActual.procesarEvento(vectorEstadoAnterior,parametrosGenerador
                    ,randomCUBase,generadorRandom,parametrosItv,generadorExponencialNeg, eventosHeap);

            if(!ecBloqueoLlegCargada && vectorEstadoActual.getEcDiferencialBloqueoLlegadas() != null){
                resultadoSimulacion.setEcDiferencialFinBloqueoLlegadas(vectorEstadoActual.getEcDiferencialBloqueoLlegadas());
                ecBloqueoLlegCargada = true;
            }
            if(!ecBloqueoNaveCargada && vectorEstadoActual.getEcDiferencialBloqueoNave() != null){
                resultadoSimulacion.setEcDiferencialFinBloqueoNaveUno(vectorEstadoActual.getEcDiferencialBloqueoNave());
                ecBloqueoNaveCargada = true;
            }
            //Una vez que las listas de las ecuaciones estén cargadas en el objeto resultado no tiene sentido
            //Mantenerlas en memoria durante toda la simulación ocupando memoria de gusto.
            vectorEstadoActual.setEcDiferencialBloqueoLlegadas(null);
            vectorEstadoActual.setEcDiferencialBloqueoNave(null);

            if(finAtencionCliente != null){
                vectorEstadoActual.eliminarClienteAtendido(finAtencionCliente.getClienteAtencionFinalizada());
                finAtencionCliente = null;
            }
            randomCUBase = vectorEstadoActual.getSiguientePseudoCU();
            if((cantEventos >= parametrosItv.getMostrarFilaDesde() && cantEventos < ultimaFila) || eventoActual instanceof EventoFinSimulacion){
                simulacionItv.add(vectorEstadoActual);
            }
            vectorEstadoAnterior = vectorEstadoActual;
            if(eventoActual instanceof EventoFinSimulacion) break;
            if(eventoActual instanceof EventoFinAtencion) {
                finAtencionCliente = (EventoFinAtencion) eventoActual;
            }
            eventoActual = eventosHeap.remove();
            cantEventos++;
        }
        resultadoSimulacion.setSimulacionItv(simulacionItv);
        resultadoSimulacion.calcularEstadisticas(simulacionItv.get(simulacionItv.size()-1));
        return resultadoSimulacion;
    }

    private Pseudoaleatorio generarEventoInicial(IGeneradorRandom generadorRandom,
                                                 ParametrosGenerador parametrosGenerador,
                                                 ParametrosItv parametrosItv,
                                                 EventoLlegadaCliente evento,
                                                 Pseudoaleatorio randomCUBase){
        evento.setRandomProxLlegada(randomCUBase);
        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpLlegadasClientes());
        parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
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
        vectorEstadoITV.setProximaLlegadaCliente(eventoInicial);
        vectorEstadoITV.setNombreEvento("Inicializacion");
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
    private List<EmpleadoNave> generarEmpleadosNave(ParametrosItv parametrosItv){
        List<EmpleadoNave> empleadosNave = new ArrayList<>(parametrosItv.getCantEmpNave());
        for(int i = 1; i <= parametrosItv.getCantEmpNave(); i++){
            EmpleadoNave servidor = new EmpleadoNave();
            servidor.setNombre("Inspector-"+i);
            servidor.setId(i);
            servidor.setEstado(EstadoServidor.getInstanceServidorLibre());
            empleadosNave.add(servidor);
        }
        return empleadosNave;
    }
    private List<Servidor> generarEmpleadosOficina(ParametrosItv parametrosItv){
        List<Servidor> empleadosOficina = new ArrayList<>(parametrosItv.getCantEmpOficina());
        for(int i = 1; i <= parametrosItv.getCantEmpOficina(); i++){
            Servidor servidor = new EmpleadoOficina();
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

    private float truncar(float f, float presicion){
        int multiplicador = (int)Math.pow(10, presicion);
        int aux = (int)(f * multiplicador);
        return (float)aux / multiplicador;
    }


}
