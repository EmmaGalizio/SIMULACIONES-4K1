package k1.grupo.p.simulacionestp5colas;

import k1.grupo.p.simulacionestp5colas.controller.ControladorTp5Colas;
import k1.grupo.p.simulacionestp5colas.controller.utils.ConstantesGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Vector;

@SpringBootTest
class SimulacionesTp5ColasApplicationTests {

    @Autowired
    private ControladorTp5Colas controladorTp5Colas;
    @Test
    void contextLoads() {
    }

    @Test
    public void testSimulacionITV(){

        ParametrosItv parametrosItv = new ParametrosItv();
        parametrosItv.setMaxMinutosSimular(7);
        parametrosItv.setCantEmpCaseta(1);
        parametrosItv.setCantEmpNave(2);
        parametrosItv.setCantEmpOficina(2);
        parametrosItv.setMostrarFilaDesde(0);
        parametrosItv.setCantFilasMostrar(200);
        //Ese 1,0525 sería la media, necesito calcular lambda
        //parametrosItv.setLambdaExpLlegadasClientes(1.0526f);
        parametrosItv.setLambdaExpLlegadasClientes(0.9500285f);
        //Es necesario setear lambda, con la regla de 3 se obtiene la media, lambda es 1/media
        parametrosItv.setLambdaExpServCaseta(1);
        parametrosItv.setLambdaExpServicioOficina(0.5f);
        parametrosItv.setLambdaExpServicioNave(0.75f);

        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);

        List<VectorEstadoITV> simulacion = controladorTp5Colas.generarSimulacion(parametrosItv,parametrosGenerador);

        for(VectorEstadoITV vector: simulacion){
            System.out.println(vector);
        }
    }

}
