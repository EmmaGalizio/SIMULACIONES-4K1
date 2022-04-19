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

@Component(ConstantesCambioDistribucion.EXP_NEG)
public class CambioDistribucionExponencialNeg implements ICambioDistribucion{

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;

    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador) {

        //
        if(parametros.getPresicion() <= 0 || parametros.getPresicion() > 9) parametros.setPresicion(4);

        ParametrosGenerador parametrosGenerador1 = Arrays.stream(parametrosGenerador).iterator().next();
        IGeneradorRandom generadorRandom = generadoresRandom.get(parametrosGenerador1.getMetodoGeneradorRandom());

        //defino vector de randoms exponenciales negativos
        int n = parametros.getN();
        Pseudoaleatorio pseudoaleatoriosExpNeg[] = new Pseudoaleatorio[n];

        //defino vector de randoms uninformes y genero randoms uninformes
        parametrosGenerador1.setN(n);
        Pseudoaleatorio randomsUnif[] = generadorRandom.generar(parametrosGenerador1);
        Pseudoaleatorio randomUnif;

        // genero randoms exponenciales negativos
        float pseudoAleatorioExpNeg;
        int multiplicador = (int)Math.pow(10, parametros.getPresicion());
        int aux;
        for(int i = 0; i < n; i++){
            randomUnif = randomsUnif[i];
            pseudoAleatorioExpNeg = (float) ((-1/parametros.getLambda())*Math.log(1-randomUnif.getRandom()));
            aux = (int) (pseudoAleatorioExpNeg*multiplicador);
            pseudoAleatorioExpNeg = (float)aux/multiplicador;
            pseudoaleatoriosExpNeg[i] = new Pseudoaleatorio(i,pseudoAleatorioExpNeg);
        }

        return pseudoaleatoriosExpNeg;
    }
}
