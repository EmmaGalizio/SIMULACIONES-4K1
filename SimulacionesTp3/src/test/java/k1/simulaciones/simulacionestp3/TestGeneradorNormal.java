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

import java.util.Arrays;
import java.util.Map;

@SpringBootTest
public class TestGeneradorNormal {

    @Autowired
    Map<String, ICambioDistribucion> cambioDistribucionMap;

    @Test
    public void testRandomsNormal(){

        //ICambioDistribucion cambioDistribucion = cambioDistribucionMap.get(ConstantesCambioDistribucion.NORMAL_BOXMULLER);
        ICambioDistribucion cambioDistribucion = cambioDistribucionMap.get(ConstantesCambioDistribucion.NORMAL_CONVOLUCION);
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setC(7);
        parametrosGenerador.setG(10);
        parametrosGenerador.setK(5);
        parametrosGenerador.setX0(5);
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setMedia(10);
        parametrosCambioDistribucion.setDesvEst(2);
        parametrosCambioDistribucion.setPresicion(4);
        parametrosCambioDistribucion.setN(100);

        Pseudoaleatorio[] pseudosNormal = cambioDistribucion.cambiarDistribucion(parametrosCambioDistribucion, parametrosGenerador);
        System.out.println("Normales generados:");

        int vectorContador[] = new int[20];

        for(Pseudoaleatorio pseudoaleatorio : pseudosNormal){
            System.out.println(pseudoaleatorio);
            vectorContador[(int) pseudoaleatorio.getRandom()]++;
        }
        Arrays.stream(vectorContador).forEach(System.out::println);


    }

}
