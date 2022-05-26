package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;

public class EventoFinAtencion extends Evento{

    private float tiempoAtencion;


    //Este evento representa la salida del cliente de la oficina
    //Cuando se saca un evento del heap en el controlador se debe controlar
    //si corresponde con este tipo de evento, de ser así se debe activar una bandera
    //que indique que al final del procesamiento del siguiente evento se debe realizar el
    //mantenimiento de la lista de clientes.

    @Override
    VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                   ParametrosGenerador parametrosGenerador,
                                   ParametrosCambioDistribucion parametrosCambioDistribucion,
                                   Pseudoaleatorio randomCUBase,
                                   IGeneradorRandom generadorRandom,
                                   ICambioDistribucion generadorVariableAleatoria) {
        return null;
    }

    public float getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(float tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }
}
