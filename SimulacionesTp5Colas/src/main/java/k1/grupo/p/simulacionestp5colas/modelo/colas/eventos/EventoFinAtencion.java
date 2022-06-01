package k1.grupo.p.simulacionestp5colas.modelo.colas.eventos;

import k1.grupo.p.simulacionestp5colas.controller.cambioDistribucion.ICambioDistribucion;
import k1.grupo.p.simulacionestp5colas.controller.generadorRandom.IGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosCambioDistribucion;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.Pseudoaleatorio;
import k1.grupo.p.simulacionestp5colas.modelo.colas.Cliente;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.modelo.estructurasDatos.TSBHeap;
import lombok.Data;

@Data
public class EventoFinAtencion extends Evento{

    //No utiliza un random porque creo
    private Pseudoaleatorio randomAtencionOficina;
    private float tiempoAtencionOficina;
    private Cliente clienteAtencionFinalizada;

    public EventoFinAtencion() {
        this.setNombreEvento("Fin At. Oficina");
    }

    //Este evento representa la salida del cliente de la oficina
    //Cuando se saca un evento del heap en el controlador se debe controlar
    //si corresponde con este tipo de evento, de ser así se debe activar una bandera
    //que indique que al final del procesamiento del siguiente evento se debe realizar el
    //mantenimiento de la lista de clientes.

    @Override
    public VectorEstadoITV procesarEvento(VectorEstadoITV estadoAnterior,
                                          ParametrosGenerador parametrosGenerador,
                                          Pseudoaleatorio randomCUBase,
                                          IGeneradorRandom generadorRandom,
                                          ParametrosItv parametrosItv,
                                          ICambioDistribucion generadorVariableAleatoria,
                                          TSBHeap<Evento> heapEventos) {

        //Cuando se procese este evento es necesario setear el campo
        //clienteAtencionFinalizada para que en la próxima iteración del controlador
        //se pueda eliminar de la lista de clientes al cliente que salió del predio
        //Total ya están registrados todos los datos necesarios en los acumuladores y contadores
        //Además, es necesario sacarlos porque si hecemos una simulación en la que llegan 10000 clientes
        //entonces la lista quedaría con 10000 elementos distintos.
        return null;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        EventoFinAtencion evento = new EventoFinAtencion();
        evento.setMomentoEvento(super.getMomentoEvento());
        evento.setRandomAtencionOficina((Pseudoaleatorio) randomAtencionOficina.clone());
        evento.setTiempoAtencionOficina(tiempoAtencionOficina);
        return evento;
    }

}
