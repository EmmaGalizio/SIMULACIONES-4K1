package p.grupo.k1.simulacionestp6;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.Evento;
import p.grupo.k1.simulacionestp6.modelo.colas.eventos.EventoLlegadaCliente;
import p.grupo.k1.simulacionestp6.modelo.estructurasDatos.TSBHeap;

@SpringBootTest
public class TestHeapEventos {

    @Test
    public void testModEventosHeap(){

        TSBHeap<Evento> heapEventos = new TSBHeap<>();

        Evento evento1 = new EventoLlegadaCliente();
        evento1.setMomentoEvento(1);

        Evento evento2 = new EventoLlegadaCliente();
        evento2.setMomentoEvento(2);

        Evento evento3 = new EventoLlegadaCliente();
        evento3.setMomentoEvento(3);

        Evento evento4 = new EventoLlegadaCliente();
        evento4.setMomentoEvento(4);

        heapEventos.add(evento1);
        heapEventos.add(evento2);
        heapEventos.add(evento3);
        heapEventos.add(evento4);

        evento2.setMomentoEvento(7);

        Evento siguienteEvento = heapEventos.remove();
        heapEventos.add(siguienteEvento);

        System.out.println("Eventos:");
        while(!heapEventos.isEmpty()){
            System.out.println(heapEventos.remove().getMomentoEvento());
        }



    }

}
