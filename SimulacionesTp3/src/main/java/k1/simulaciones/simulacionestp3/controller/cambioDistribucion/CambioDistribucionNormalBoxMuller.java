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

@Component(ConstantesCambioDistribucion.NORMAL_BOXMULLER)
public class CambioDistribucionNormalBoxMuller implements ICambioDistribucion{

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;

    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador) {

        if(parametros.getPresicion() <= 0 || parametros.getPresicion() > 9) parametros.setPresicion(4);

        int n = parametros.getN();
        int nUninforme = n/2;

        //primer generador de uninformes
        ParametrosGenerador parametrosGenerador1 = Arrays.stream(parametrosGenerador).iterator().next();
        IGeneradorRandom generadorRandom1 = generadoresRandom.get(parametrosGenerador1.getMetodoGeneradorRandom());
        //defino vector de randoms uninformes y genero randoms uninformes
        parametrosGenerador1.setN(nUninforme);
        Pseudoaleatorio randomsUnif1[] = generadorRandom1.generar(parametrosGenerador1);
        Pseudoaleatorio randomUnif1;

        //primer generador de uninformes
        ParametrosGenerador parametrosGenerador2 = Arrays.stream(parametrosGenerador).iterator().next();
        IGeneradorRandom generadorRandom2 = generadoresRandom.get(parametrosGenerador2.getMetodoGeneradorRandom());
        //defino vector de randoms uninformes y genero randoms uninformes
        parametrosGenerador2.setN(nUninforme);
        Pseudoaleatorio randomsUnif2[] = generadorRandom2.generar(parametrosGenerador2);
        Pseudoaleatorio randomUnif2;

        //defino vector de randoms normales
        Pseudoaleatorio pseudoaleatoriosNormalesBoxMuller[] = new Pseudoaleatorio[n];

        // genero randoms normales Box Muller
        float pseudoAleatorioENormalesBoxMuller;
        int multiplicador = (int)Math.pow(10, parametros.getPresicion());
        int aux;
        boolean band = false;

        for(int i = 0; i < n; i++){
            /*
            randomUnif = randomsUnif[i];
            if(!band){

            }
            randomUnif = randomsUnif[i];
            pseudoAleatorioExpNeg = (float) ((-1/parametros.getLambda())*Math.log(1-randomUnif.getRandom()));
            aux = (int) (pseudoAleatorioExpNeg*multiplicador);
            pseudoAleatorioExpNeg = (float)aux/multiplicador;
            pseudoaleatoriosExpNeg[i] = new Pseudoaleatorio(i,pseudoAleatorioExpNeg);
            */
        }

        return new Pseudoaleatorio[0];

    }
}
