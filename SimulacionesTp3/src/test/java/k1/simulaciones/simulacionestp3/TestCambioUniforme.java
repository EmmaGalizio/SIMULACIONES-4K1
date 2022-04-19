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
public class TestCambioUniforme {

    @Autowired
    private Map<String, ICambioDistribucion> cambioDistribucionMap;

    @Test
    public void testRandomsUniforme(){

        ICambioDistribucion cambioDistribucion = cambioDistribucionMap.get(ConstantesCambioDistribucion.UNIFORME);
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setC(7);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(5);
        parametrosGenerador.setX0(5);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        //parametrosGenerador.setPresicion(5);

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setUnifA(5);
        parametrosCambioDistribucion.setUnifB(10);
        parametrosCambioDistribucion.setPresicion(4);
        parametrosCambioDistribucion.setN(100);

        Pseudoaleatorio[] pseudosPoisson = cambioDistribucion.cambiarDistribucion(parametrosCambioDistribucion, parametrosGenerador);
        System.out.println("Posson generados:");
        for(Pseudoaleatorio pseudoaleatorio : pseudosPoisson){
            System.out.println(pseudoaleatorio);
        }

    }

}
