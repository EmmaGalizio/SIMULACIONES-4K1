package p.grupo.k1.simulacionestp6.modelo.rungeKutta.eventos;

import lombok.Data;
import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.modelo.ParametrosCambioDistribucion;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.VaribaleAleatoria;
import p.grupo.k1.simulacionestp6.modelo.colas.Cliente;
import p.grupo.k1.simulacionestp6.modelo.colas.EstadoCliente;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.Evento;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.EventoFinInspeccion;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.EmpleadoNave;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.EstadoServidor;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoRungeKutta;

import java.util.LinkedList;
import java.util.List;

@Data
public class EventoFinBloqueoNaveUno extends Evento {
    

    private float duracionBloqueo;
    private float t0;

    public EventoFinBloqueoNaveUno(){
        this.nombreEvento = "Bloqueo Inspector 1";
    }
    
    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior, ParametrosGenerador parametrosGenerador,
                                          Pseudoaleatorio randomCUBase, IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv, ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV estadoActual = (VectorEstadoITV) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.setNombreEvento(this.nombreEvento);
        estadoActual.setFinBloqueoNaveUno(null);

        EmpleadoNave empleadoAtacado = estadoActual.getEmpleadosNave().get(0);
        Evento eventoFinInspeccion1 = estadoActual.getFinInspeccion()[0];

        if(eventoFinInspeccion1 != null){
            empleadoAtacado.setEstado(EstadoServidor.getInstanceServidorOcupado());
        } else{

            if(estadoActual.getCantClientesColaNave() == 0){
                empleadoAtacado.setEstado(EstadoServidor.getInstanceServidorLibre());
                //Los dos momentos de liberacion no se deberían utilizar más
                //Se debe hacer un método en VectorEstadoItv que acumule los tiempos libres en cada
                //Evento, revisando si en el estado anterior alguno de los empleados de cada tipo estaba libre
                empleadoAtacado.setMomentoLiberacion(this.momentoEvento);
                //estadoActual.setMomentoUltimaLiberacionNave(this.momentoEvento);
                empleadoAtacado.setClienteActual(null);
            }else{
                Cliente cliente = estadoActual.getsiguienteClienteColaNave();
                cliente = estadoActual.buscarClientePorId(cliente.getNumeroCliente());
                cliente.setEstado(EstadoCliente.getInstanceAtencionNave());
                cliente.setHoraInicioAtencionNave(this.momentoEvento);
                cliente.setServidorActual(empleadoAtacado);

                empleadoAtacado.setEstado(EstadoServidor.getInstanceServidorOcupado());
                empleadoAtacado.setClienteActual(cliente);

                ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
                parametrosCambioDistribucion.setLambda(parametrosItv.getLambdaExpServicioNave());
                parametrosCambioDistribucion.setPresicion(parametrosGenerador.getPresicion());
                VaribaleAleatoria tiempoFinInspeccion = generadorVariableAleatoria.siguienteRandom(parametrosCambioDistribucion,parametrosGenerador,randomCUBase);
                EventoFinInspeccion eventoFinInspeccion = new EventoFinInspeccion();
                eventoFinInspeccion.setRandomFinInspeccion(randomCUBase);
                eventoFinInspeccion.setTiempoFinInspeccion(tiempoFinInspeccion.getRandomGenerado());
                eventoFinInspeccion.setMomentoEvento(this.momentoEvento+eventoFinInspeccion.getTiempoFinInspeccion());
                eventoFinInspeccion.setCliente(cliente);
                heapEventos.add(eventoFinInspeccion);

                estadoActual.acumularTiempoColaNave(cliente);
                estadoActual.acumularTiempoEsperaCola(cliente.getHoraLlegadaNave());
                randomCUBase = tiempoFinInspeccion.getSiguienteRandomBase();

            }
        }
        //Es necesario calcular cuando será el próximo ataque
        EventoLlegadaAtaque siguienteAtaque = new EventoLlegadaAtaque();
        List<ResultadoRungeKutta> ecDifSiguienteAtaque = new LinkedList<>();
        randomCUBase = siguienteAtaque.calcularTiempoLlegadaSiguienteAtaque(randomCUBase,generadorRandom,
                parametrosGenerador, ecDifSiguienteAtaque);
        ResultadoRungeKutta resultadoRungeKutta = ecDifSiguienteAtaque
                .get(ecDifSiguienteAtaque.size()-1);

        float tiempoHastaLlegada = siguienteAtaque.getTiempoHastaLlegada();
        siguienteAtaque.setMomentoEvento((tiempoHastaLlegada + this.momentoEvento));
        //siguienteAtaque.setTiempoHastaLlegada((float)tiempoHastaLlegada);
        siguienteAtaque.setRandomMomentoAtaque(randomCUBase.getRandom());
        heapEventos.add(siguienteAtaque);

        estadoActual.setFinBloqueoLlegada(null);
        estadoActual.setLlegadaAtaque(siguienteAtaque);

        estadoActual.setSiguientePseudoCU(randomCUBase);
        estadoActual.acumularTiempoLibreServidores(estadoAnterior);
        return estadoActual;
    }

    @Override
    public Object clone(){
        EventoFinBloqueoNaveUno nuevoEvento = new EventoFinBloqueoNaveUno();
        nuevoEvento.setNombreEvento(this.getNombreEvento());
        nuevoEvento.setMomentoEvento(this.getMomentoEvento());
        nuevoEvento.setDuracionBloqueo(this.getDuracionBloqueo());
        return nuevoEvento;
    }


    /***
     * Permite que cada vez que se dispare un evento del tipo EventoLlegadaAtaque, se calcule si el ataque
     * será a las llegadas o al servidor, una vez calculado se crea la instancia correspondiente y se utiliza
     * este método para calcular la ecuación diferencial que determina su duración.
     * @param presicion Numero de digitos decimales que se mostraran
     * @return Una lista en donde cada elemento representa una fila de la tabla del método de
     * Runge Kutta de cuarto orden.
     */
    public List<ResultadoRungeKutta> calcularFinEvento(float presicion) {
        double Sm;
        double h = 0.01f;
        double t = 0;
        double k1,k2,k3,k4;
        double Smp1 = this.t0;
        double puntoCorte = this.t0 * 1.35f;

        //DS/dt = (0.2f * S) + 3 - t
        List<ResultadoRungeKutta> ecDiferencial = new LinkedList<>();
        ResultadoRungeKutta resultadoRungeKutta;

        do{
            Sm = Smp1;

            resultadoRungeKutta = new ResultadoRungeKutta();
            resultadoRungeKutta.setXm(t);
            resultadoRungeKutta.setYm(Sm);

            k1 = (0.2f * Sm) + 3 - t;
            k1 = truncar(k1, presicion);
            resultadoRungeKutta.setK1(k1);

            k2 = (0.2f * (Sm+(0.5f*h*k1))) + 3 - (t+(0.5f*h));
            k2 = truncar(k2,presicion);
            k3 = (0.2f * (Sm+(0.5f*h*k2))) + 3 - (t+(0.5f*h));
            k3 = truncar(k3,presicion);

            k4 = (0.2f * (Sm+(h*k3))) + 3 - (t+h);
            k4 = truncar(k4, presicion);
            resultadoRungeKutta.setK2(k2);
            resultadoRungeKutta.setK3(k3);
            resultadoRungeKutta.setK4(k4);
            Smp1 = Sm + (h/6)*(k1+ (2*k2)+(2*k3)+k4);
            Smp1 = truncar(Smp1,presicion);
            resultadoRungeKutta.setYmp1(Smp1);
            t = t+h;
            t = truncar(t, presicion);
            resultadoRungeKutta.setXmp1(t);


            ecDiferencial.add(resultadoRungeKutta);
        }while(Sm < puntoCorte);

        t = ecDiferencial.get(ecDiferencial.size()-1).getXm();

        this.duracionBloqueo = (float)t*2;
        this.duracionBloqueo = (float)truncar(this.duracionBloqueo, presicion);
        this.momentoEvento = (float)(this.t0 + (t*2));
        this.momentoEvento = (float)truncar(this.momentoEvento, presicion);
        return  ecDiferencial;
    }

    private double truncar(double f, float presicion){
        double multiplicador = Math.pow(10, presicion);
        int aux = (int)(f * multiplicador);
        //return (double)aux / multiplicador;
        return Math.round(f*multiplicador)/multiplicador;
    }
}
