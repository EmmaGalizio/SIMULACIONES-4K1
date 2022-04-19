
package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.generadorRandom.IGeneradorRandom;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;


/*
@Component(ConstantesCambioDistribucion.NORMAL_CONVOLUSION)
public class CambioDistribucionNormalConvolucion implements ICambioDistribucion {
    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;

    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador){

        int n = parametros.getN();
        int nUniforme = n*12;
        ParametrosGenerador parametrosGenerador1 = Arrays.stream(parametrosGenerador).iterator().next();
        IGeneradorRandom generadorRandom1 = generadoresRandom.get(parametrosGenerador.getMetodoGeneradorRandom());

        parametrosGenerador1.setN(nUniforme);
        Pseudoaleatorio vectorRandomUniforme[] = generadorRandom1.generar(parametrosGenerador1);

        //CORREGIR

        float media = ( n / 2 );
        float varianza = ( n / 12 );
        double desviacionEstandar = Math.sqrt(varianza);
        float sumatoria = 0;
        for(int i = 0; i < n ; i++){

            for(int j= i; j < i+12 ; j++){
                sumatoria += vectorRandomUniforme[j].getRandom();
            }
        }

        double randomNormalDistribuido = (((sumatoria - 6)* desviacionEstandar) + media);




    }
}
*/