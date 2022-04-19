package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.generadorRandom.IGeneradorRandom;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component(ConstantesCambioDistribucion.POISSON)
@RequiredArgsConstructor
public class CambioDistribucionPoisson implements ICambioDistribucion{

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandom;

    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros,
                                                 ParametrosGenerador... parametrosGeneradorList) {

        if(parametros.getLambda() <= 0){
            parametros.setLambda(parametros.getMedia());
        }
        ParametrosGenerador parametrosGenerador = Arrays.stream(parametrosGeneradorList).iterator().next();
        IGeneradorRandom generadorRandom = generadoresRandom.get(parametrosGenerador.getMetodoGeneradorRandom());
        parametrosGenerador.setN(1);
        Pseudoaleatorio randomUnif = generadorRandom.generar(parametrosGenerador)[0];
        Pseudoaleatorio[] randomsPosson = new Pseudoaleatorio[parametros.getN()];
        float a = (float)Math.exp(-parametros.getLambda());
        for(int i = 0; i < parametros.getN(); i++){
            Pseudoaleatorio randomPoisson = generarRandomPoisson(a, randomUnif,
                    generadorRandom,parametrosGenerador);
            randomPoisson.setI(i+1);
            randomsPosson[i] = randomPoisson;
            randomUnif = generadorRandom.siguientePseudoAleatoreo(randomUnif,parametrosGenerador);
        }
        return randomsPosson;
    }

    private Pseudoaleatorio generarRandomPoisson(float a, Pseudoaleatorio randomUnif,
                                                 IGeneradorRandom generador, ParametrosGenerador parametrosGenerador){

        float p = 1;
        int x=-1;
        do{
            p *= randomUnif.getRandom();
            x++;
            randomUnif = generador.siguientePseudoAleatoreo(randomUnif,parametrosGenerador);
        }while(p >= a);
        return new Pseudoaleatorio(0,x);

    }

}
