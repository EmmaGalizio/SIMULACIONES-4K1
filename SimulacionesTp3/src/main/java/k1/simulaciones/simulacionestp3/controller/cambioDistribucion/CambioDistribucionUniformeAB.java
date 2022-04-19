package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.generadorRandom.IGeneradorRandom;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component(ConstantesCambioDistribucion.UNIFORME)
public class CambioDistribucionUniformeAB implements ICambioDistribucion{

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;
    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros,
                                                 ParametrosGenerador... parametrosGeneradorList) {
        if(parametros.getUnifA() <= 0 || parametros.getUnifB() <= 0)
            throw new IllegalArgumentException("Debe indiar los limites inferior y superior");
        ParametrosGenerador parametrosGenerador = Arrays.stream(parametrosGeneradorList).iterator().next();
        IGeneradorRandom generadorRandom = generadoresRandom.get(parametrosGenerador.getMetodoGeneradorRandom());
        parametrosGenerador.setN(1);
        Pseudoaleatorio randomUnif = generadorRandom.generar(parametrosGenerador)[0];
        Pseudoaleatorio[] pseudosRandomAB = new Pseudoaleatorio[parametros.getN()];
        int multiplicador = (int)Math.pow(10, parametros.getPresicion());
        int aux;
        for(int i = 0; i < parametros.getN(); i++){
            float random = parametros.getUnifA()
                    + (randomUnif.getRandom()*(parametros.getUnifB()-parametros.getUnifA()));
            aux = (int)(random*multiplicador);
            random = (float)aux/multiplicador;
            pseudosRandomAB[i] = new Pseudoaleatorio(i+1, random);
            randomUnif = generadorRandom.siguientePseudoAleatoreo(randomUnif,parametrosGenerador);
        }

        return pseudosRandomAB;
    }
}
