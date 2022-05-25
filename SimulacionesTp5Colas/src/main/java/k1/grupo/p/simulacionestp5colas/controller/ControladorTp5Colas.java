package k1.grupo.p.simulacionestp5colas.controller;

import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;

import java.util.List;

public class ControladorTp5Colas {

    public List<VectorEstadoITV> generarSimulacion(){

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

        //Al crear el vector de estado 0 se deben instanciar los objetos servidores, y esos
        //mismos objetos se van pasando de vector en vector (se copian las referencias) y se
        // actualizan sus atributos y estado.
        //La lista de clientes debe ser clonada, no se puede solo copiar la referencia
        //Porque puede ser necesario mostrar un cliente en el vector i-1, pero ser eliminado en el
        //vector i.


        return null;
    }

}
