package p.grupo.k1.simulacionestp6.modelo.rungeKutta.eventos;

import lombok.Data;
import p.grupo.k1.simulacionestp6.controller.cambioDistribucion.ICambioDistribucion;
import p.grupo.k1.simulacionestp6.controller.generadorRandom.IGeneradorRandom;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.colas.ParametrosItv;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.Evento;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.EventoFinInspeccion;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.EmpleadoNave;
import p.grupo.k1.simulacionestp6.modelo.colas.servidor.EstadoServidor;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoRungeKutta;

import java.util.List;

@Data
public class EventoLlegadaAtaque extends Evento {

    public static final String ATAQUE_SERVIDOR = "Servidor Nave 1";
    public static final String ATAQUE_LLEGADAS = "Llegada Clientes";
    public static final float PROB_ACUM_LLEGADAS = 0.7f;
    public static final float A_LLEGADAS = 85.3f;

    private String tipoAtaque;
    private float randomTipoAtaque;
    private float tiempoHastaLlegada;
    private float randomMomentoAtaque;

    public EventoLlegadaAtaque() {
        this.nombreEvento = "Llegada Ataque";
    }

    public EventoLlegadaAtaque(float momentoAtaque){
        this.momentoEvento = momentoAtaque;
        this.nombreEvento = "Llegada Ataque";
    }

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                          ParametrosGenerador parametrosGenerador,
                                          Pseudoaleatorio randomCUBase, IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                          ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        VectorEstadoITV estadoActual = (VectorEstadoITV) estadoAnterior.clone();
        estadoActual.setReloj(this.momentoEvento);
        estadoActual.setNombreEvento(this.nombreEvento);
        EventoLlegadaAtaque llegadaActual = estadoActual.getLlegadaAtaque();
        //estadoActual.setLlegadaAtaque(null);
        randomCUBase = generadorRandom.siguientePseudoAleatoreo(randomCUBase, parametrosGenerador);
        llegadaActual.setRandomTipoAtaque(randomCUBase.getRandom());
        calcularTipoAtaque(llegadaActual.getRandomTipoAtaque(),llegadaActual);

        EventoFinBloqueoLlegada eventoFinBloqueoLlegada = null;
        EventoFinBloqueoNaveUno eventoFinBloqueoNaveUno = null;
        List<ResultadoRungeKutta> ecDifFinBloqueoLlegada = null;
        List<ResultadoRungeKutta> ecDifFinBloqueoNave = null;

        //En el momento en que se calcula el fin del evento se setea el momentoEvento
        //Del evento de fin de ataque correspondiente
        if(llegadaActual.getRandomTipoAtaque() < PROB_ACUM_LLEGADAS){
            eventoFinBloqueoLlegada = new EventoFinBloqueoLlegada();
            eventoFinBloqueoLlegada.setT0(this.momentoEvento);
            ecDifFinBloqueoLlegada = eventoFinBloqueoLlegada.calcularFinEvento(parametrosGenerador.getPresicion());
            double finAtaque = ecDifFinBloqueoLlegada.get(ecDifFinBloqueoLlegada.size()-1).getXm();
            finAtaque *= 5;
            finAtaque = truncar(finAtaque, parametrosGenerador.getPresicion());
            eventoFinBloqueoLlegada.setMomentoEvento((float)(this.momentoEvento + finAtaque));

        }else{
            eventoFinBloqueoNaveUno = new EventoFinBloqueoNaveUno();
            eventoFinBloqueoNaveUno.setT0(this.momentoEvento);
            ecDifFinBloqueoNave = eventoFinBloqueoNaveUno.calcularFinEvento(parametrosGenerador.getPresicion());
            double finAtaque = ecDifFinBloqueoNave.get(ecDifFinBloqueoNave.size()-1).getXm();
            finAtaque *= 2;
            finAtaque = truncar(finAtaque, parametrosGenerador.getPresicion());
            eventoFinBloqueoNaveUno.setMomentoEvento((float)(this.momentoEvento + finAtaque));
            this.interrumpirServidor(estadoActual, eventoFinBloqueoNaveUno, heapEventos);
        }
        estadoActual.setFinBloqueoLlegada(eventoFinBloqueoLlegada);
        estadoActual.setFinBloqueoNaveUno(eventoFinBloqueoNaveUno);
        estadoActual.setEcDiferencialBloqueoLlegadas(ecDifFinBloqueoLlegada);
        estadoActual.setEcDiferencialBloqueoNave(ecDifFinBloqueoNave);
        estadoActual.setSiguientePseudoCU(randomCUBase);
        estadoActual.acumularTiempoLibreServidores(estadoAnterior);
        heapEventos.add((eventoFinBloqueoLlegada != null) ? eventoFinBloqueoLlegada: eventoFinBloqueoNaveUno);
        return estadoActual;
    }

    private void interrumpirServidor(VectorEstadoITV estadoActual, EventoFinBloqueoNaveUno eventoFinBloqueoNaveUno,
                                     TSBHeap<Evento> heapEventos) {

        EmpleadoNave empleadoNave = estadoActual.getEmpleadosNave().get(0);
        empleadoNave.setEstado(EstadoServidor.getInstanceServidorBloqueado());
        //Al interrumpirse el servidor, si esta atendiendo a algun cliente entonces se deberá actualizar el
        //Evento de fin de atención teniendo en cuenta la duracion del ataque, para eso se debe actualizar
        //El evento de fin de atención correspondiente al Empleado de nave 1. Pero a causa de esto es
        //Necesario reorganizar el heap de eventos, porque si justo el proximo evento que se quiere
        //sacar del heap es el fin de atención va a quedar con los tiempos mal seteados.
        //Por eso es necesario eliminar el elemento de la raiz del heap e insertarlo de nuevo
        //Para que se re ordene el primer elemento.
        EventoFinInspeccion finInspeccionNave1 = estadoActual.getFinInspeccion()[0];

        if(finInspeccionNave1 != null){
            Evento finInspeccionCopiaHeap = heapEventos.getElementCopy(finInspeccionNave1);
            float nuevoFinInspeccion = finInspeccionNave1.getMomentoEvento();
            nuevoFinInspeccion += eventoFinBloqueoNaveUno.getDuracionBloqueo();
            finInspeccionNave1.setMomentoEvento(nuevoFinInspeccion);
            finInspeccionCopiaHeap.setMomentoEvento(nuevoFinInspeccion);
        }
        //Al hacer esto, en caso de que finInspeccionNave1 esté en la raiz del heap se actualiza
        //Y se vuelve a reorganizar
        //No deberia hacer falta, se agrega un nuevo elemento al final del metodo procesrEvento
        //así que ahí se reordenaría el heap
        Evento siguienteEvento = heapEventos.remove();
        heapEventos.add(siguienteEvento);
    }


    public void calcularTipoAtaque(float randomTipoAtaque, EventoLlegadaAtaque llegadaActual) {

        String tipo = (randomTipoAtaque < PROB_ACUM_LLEGADAS) ?
                ATAQUE_LLEGADAS : ATAQUE_SERVIDOR;
        llegadaActual.setTipoAtaque(tipo);
    }

    @Override
    public Object clone(){
        EventoLlegadaAtaque nuevoEvento = new EventoLlegadaAtaque();
        nuevoEvento.setNombreEvento(this.getNombreEvento());
        nuevoEvento.setMomentoEvento(this.getMomentoEvento());
        nuevoEvento.setTiempoHastaLlegada(this.getTiempoHastaLlegada());
        nuevoEvento.setRandomMomentoAtaque(this.getRandomMomentoAtaque());
        nuevoEvento.setRandomTipoAtaque(this.getRandomTipoAtaque());
        nuevoEvento.setTipoAtaque(this.getTipoAtaque());
        return nuevoEvento;
    }

    public Pseudoaleatorio calcularTiempoLlegadaSiguienteAtaque(Pseudoaleatorio randomBaseCU,
                                                                IGeneradorRandom generadorRandom,
                                                                ParametrosGenerador parametrosGenerador,
                                                                List<ResultadoRungeKutta> tablaRungeKutta){
        double A = EventoLlegadaAtaque.A_LLEGADAS; // es una constante, hay que ver cuando ocurre
        A = truncar(A, parametrosGenerador.getPresicion());
        //la llegada del vehículo 80 en el TP5
        Pseudoaleatorio siguienteRandom = generadorRandom
                .siguientePseudoAleatoreo(randomBaseCU,parametrosGenerador);
        float B = siguienteRandom.getRandom();
        this.setRandomMomentoAtaque(B);
        double puntoCorte = 2*A;
        puntoCorte = truncar(puntoCorte, parametrosGenerador.getPresicion());
        double h = 0.0100;
        double t = 0;
        double k1,k2,k3,k4;

        ResultadoRungeKutta resultadoRungeKutta;

        do{
            resultadoRungeKutta = new ResultadoRungeKutta();
            resultadoRungeKutta.setXm(t);
            resultadoRungeKutta.setYm(A);
            k1 = B*A;
            k1 = truncar(k1, parametrosGenerador.getPresicion());
            resultadoRungeKutta.setK1(k1);
            k2 = B*(A+ (0.5f*h*k1));
            k2 = truncar(k2, parametrosGenerador.getPresicion());
            resultadoRungeKutta.setK2(k2);
            k3 = B*(A+(0.5f*h*k2));
            k3 = truncar(k3, parametrosGenerador.getPresicion());
            resultadoRungeKutta.setK3(k3);
            k4 = B*(A+(h*k3));
            k4 = truncar(k4,parametrosGenerador.getPresicion());
            resultadoRungeKutta.setK4(k4);
            A = A + ((h/6)*(k1+(2*k2)+(2*k3)+k4));
            A = truncar(A, parametrosGenerador.getPresicion());
            resultadoRungeKutta.setYmp1(A);
            t+=h;
            t = truncar(t, 2);
            resultadoRungeKutta.setXmp1(t);
            tablaRungeKutta.add(resultadoRungeKutta);
        }while(resultadoRungeKutta.getYm() < puntoCorte);

        double tiempoHastaAtaque = tablaRungeKutta.get(tablaRungeKutta.size()-1).getXm();
        tiempoHastaAtaque*= 9;
        tiempoHastaAtaque = truncar(tiempoHastaAtaque, parametrosGenerador.getPresicion());
        this.tiempoHastaLlegada = (float) tiempoHastaAtaque;
        return siguienteRandom;
    }

    public double truncar(double f, float presicion){
        double multiplicador = Math.pow(10, presicion);
        int aux = (int)(f * multiplicador);
        //return (double)aux / multiplicador;
        return Math.round(f*multiplicador)/multiplicador;
    }

    public float getRandomMomentoAtaque() {
        return randomMomentoAtaque;
    }

    public void setRandomMomentoAtaque(float randomMomentoAtaque) {
        this.randomMomentoAtaque = randomMomentoAtaque;
    }
}
