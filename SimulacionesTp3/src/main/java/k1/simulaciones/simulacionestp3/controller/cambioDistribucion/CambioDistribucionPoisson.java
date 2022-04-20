package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.generadorRandom.IGeneradorRandom;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.Intervalo;
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

    @Override
    public Intervalo[] generarDistFrecuenciaInicial(Pseudoaleatorio[] randoms,
                                                    ParametrosCambioDistribucion parametrosCambioDistribucion){
//No debemos dejar que para poisson incluyan una cant de intervalos
        //Poisson es discreta, la distribucion de frecuencias se hace sobre todos los valores.
        float[]minYMax = buscarMinYMax(randoms);
        Intervalo[] distFrecInicial = inicializarDistFrecuencia(minYMax,
                                                            parametrosCambioDistribucion.getPresicion());
        int multiplicador = (int)Math.pow(10, parametrosCambioDistribucion.getPresicion());

        for(int i = 0; i < distFrecInicial.length; i++){

            distFrecInicial[i]
                    .setProbEsp(calcularProbEsperada(parametrosCambioDistribucion,distFrecInicial[i]));
            float frecEsp = distFrecInicial[i].getProbEsp()*randoms.length;
            int frecEspAux = (int)(frecEsp*multiplicador);
            distFrecInicial[i].setFrecEsp((float)frecEspAux/multiplicador);
        }
        llenarDistribucionFrecuencia(randoms,distFrecInicial, parametrosCambioDistribucion.getPresicion());
        return distFrecInicial;
    }
    private void llenarDistribucionFrecuencia(Pseudoaleatorio[] randoms, Intervalo[] distFrecuencia,int presicion){

        for(Pseudoaleatorio random : randoms){
            //Se puede implementar buqueda binaria
            for(int i = 0; i< distFrecuencia.length; i++){
                if((int)random.getRandom()== (int)distFrecuencia[i].getMarcaClase()){
                    distFrecuencia[i].incrementarFrecObservada();
                }
            }
        }
        int multiplicador = (int)Math.pow(10,presicion);
        for(Intervalo intervalo: distFrecuencia){
            float probObs = ((float)intervalo.getFrecObs()/ randoms.length);
            probObs = ((int)(probObs*multiplicador))/(float)multiplicador;
            intervalo.setProbObs(probObs);
        }

    }
    private int busquedaBinaria(Intervalo[] distFrec, int random){
        int izq = 0, der = distFrec.length-1;
        int piv = (der-izq)/2;
        return -1;

    }
    private Intervalo[] inicializarDistFrecuencia(float[]minYMax, int presicion ){

        int k = (int)(minYMax[1]-minYMax[0]);

        Intervalo[] distFrecuencia = new Intervalo[k];
        int min = (int)minYMax[0];
        for(int i = 0; i < k; i++){
            Intervalo intervalo = new Intervalo();
            intervalo.setLimInf(min+i+1);
            intervalo.setLimSup(min+i+1);
            intervalo.setMarcaClase(min+i+1);
            distFrecuencia[i]=intervalo;
        }

        /*
        int multiplicador = (int)Math.pow(10,presicion);
        float amplitudIntervalo = (minYMax[1]-minYMax[0])/kAlt;
        int limInfAux = (int)(minYMax[0]*multiplicador);
        float limInf = (float)limInfAux/multiplicador;
        float limSup;
        Intervalo [] distFrecuencia = new Intervalo[kAlt];
        for(int i = 0; i < distFrecuencia.length; i++){
            Intervalo intervalo = new Intervalo();
            intervalo.setLimInf(limInf);
            limSup = limInf + amplitudIntervalo;
            limSup = ((int)(limSup*multiplicador)/(float)multiplicador);
            intervalo.setLimSup(limSup);
            float marcaClase = ((int)(((limSup+limInf)/2)*multiplicador))/(float)multiplicador;
            intervalo.setMarcaClase(marcaClase);
            distFrecuencia[i] = intervalo;

            limInf = limSup;
            limInf = ((int)(limInf*multiplicador)/(float)multiplicador);
        }

         */
        return distFrecuencia;
    }

    private float[] buscarMinYMax(Pseudoaleatorio[] randoms){
        float max = 0;
        float min = randoms[0].getRandom();

        for(int i = 0; i< randoms.length; i++){
            max = Math.max(randoms[i].getRandom(), max);
            min = Math.min(randoms[i].getRandom(),min);
        }
        return new float[]{min, max};
    }

    private float calcularProbEsperada(ParametrosCambioDistribucion parametrosCambioDistribucion, Intervalo intervalo) {

        float probEsp = (float)Math.pow(parametrosCambioDistribucion.getLambda(),intervalo.getMarcaClase());
        probEsp*=(float)Math.exp(-parametrosCambioDistribucion.getLambda());
        long factorialX=1;
        int x = intervalo.getFrecObs();
        while(x > 0){
            factorialX*=x;
            x--;
        }
        probEsp = probEsp/factorialX;
        int multiplicador = (int)Math.pow(10, parametrosCambioDistribucion.getPresicion());
        int probAux = (int)(probEsp*multiplicador);
        return ((float)probAux/multiplicador);
    }

}
