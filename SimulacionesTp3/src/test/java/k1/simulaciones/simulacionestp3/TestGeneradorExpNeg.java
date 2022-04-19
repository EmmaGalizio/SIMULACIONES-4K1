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
public class TestGeneradorExpNeg {

    @Autowired
    Map<String, ICambioDistribucion> cambioDistribucionMap;

    @Test
    public void testRandomsExpNeg(){

        ICambioDistribucion cambioDistribucion = cambioDistribucionMap.get(ConstantesCambioDistribucion.EXP_NEG);
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setC(7);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(5);
        parametrosGenerador.setX0(5);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setLambda(0.4f);
        parametrosCambioDistribucion.setPresicion(4);
        parametrosCambioDistribucion.setN(100);

        Pseudoaleatorio[] pseudosExpNeg = cambioDistribucion.cambiarDistribucion(parametrosCambioDistribucion, parametrosGenerador);
        System.out.println("Exponencial negativos generados:");
        for(Pseudoaleatorio pseudoaleatorio : pseudosExpNeg){
            System.out.println(pseudoaleatorio);
        }

    }
}
