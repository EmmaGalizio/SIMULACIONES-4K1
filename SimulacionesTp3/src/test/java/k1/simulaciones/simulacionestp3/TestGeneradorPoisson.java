package k1.simulaciones.simulacionestp3;

import k1.simulaciones.simulacionestp3.controller.cambioDistribucion.ICambioDistribucion;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesGenerador;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class TestGeneradorPoisson {

    @Autowired
    private Map<String, ICambioDistribucion> cambioDistribucionMap;

    @Test
    public void testRandomsPoisson(){

        ICambioDistribucion cambioDistribucion = cambioDistribucionMap.get(ConstantesCambioDistribucion.POISSON);
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setC(7);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(5);
        parametrosGenerador.setX0(5);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(7f);
        parametrosCambioDistribucion.setPresicion(4);
        parametrosCambioDistribucion.setN(100);

        Pseudoaleatorio[] pseudosPoisson = cambioDistribucion.cambiarDistribucion(parametrosCambioDistribucion, parametrosGenerador);
        System.out.println("Posson generados:");
        for(Pseudoaleatorio pseudoaleatorio : pseudosPoisson){
            System.out.println(pseudoaleatorio);
        }

    }
}
