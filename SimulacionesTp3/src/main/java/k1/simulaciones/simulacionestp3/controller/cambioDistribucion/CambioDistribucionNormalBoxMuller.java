package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.generadorRandom.IGeneradorRandom;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.Intervalo;
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

    @Override
    public Intervalo[] generarDistFrecuenciaInicial(Pseudoaleatorio[] randoms,
                                                    ParametrosCambioDistribucion parametrosCambioDistribucion){

        float[]minYMax = buscarMinYMax(randoms);
        Intervalo[] distFrecInicial = inicializarDistFrecuencia(minYMax,
                parametrosCambioDistribucion.getKInicial(),parametrosCambioDistribucion.getPresicion());
        int multiplicador = (int)Math.pow(10, parametrosCambioDistribucion.getPresicion());
        float limInf, limSup;
        for(int i = 0; i < distFrecInicial.length; i++){
            limInf = distFrecInicial[i].getLimInf();
            limSup = distFrecInicial[i].getLimSup();
            distFrecInicial[i]
                    .setProbEsp(calcularProbEsperada(parametrosCambioDistribucion,limInf,limSup));
            float frecEsp = distFrecInicial[i].getProbEsp()*randoms.length;
            int frecEspAux = (int)(frecEsp*multiplicador);
            distFrecInicial[i].setFrecEsp((float)frecEspAux/multiplicador);
        }
        llenarDistribucionFrecuencia(randoms,distFrecInicial, parametrosCambioDistribucion.getPresicion());
        return distFrecInicial;
    }
    private void llenarDistribucionFrecuencia(Pseudoaleatorio[] randoms, Intervalo[] distFrecuencia,int presicion){

        for(Pseudoaleatorio random : randoms){

            for(int i = 0 ; i < distFrecuencia.length;i++){
                if((random.getRandom() >= distFrecuencia[i].getLimInf()
                        && random.getRandom() < distFrecuencia[i].getLimSup())||
                        ((i == (distFrecuencia.length-1))&&
                                random.getRandom()==distFrecuencia[i].getLimSup())){
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
    private Intervalo[] inicializarDistFrecuencia(float[]minYMax, int k, int presicion ){

        int multiplicador = (int)Math.pow(10,presicion);
        float amplitudIntervalo = (minYMax[1]-minYMax[0])/k;
        int limInfAux = (int)(minYMax[0]*multiplicador);
        float limInf = (float)limInfAux/multiplicador;
        float limSup;
        Intervalo [] distFrecuencia = new Intervalo[k];
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

    private float calcularProbEsperada(ParametrosCambioDistribucion parametrosCambioDistribucion, float limInf, float limSup) {

        float constante = 1/(parametrosCambioDistribucion.getDesvEst()*(float)Math.sqrt(2*Math.PI));
        float marcaClase = (limInf+limSup)/2;
        float amplitud = limSup-limInf;
        float probabilidad = constante* (float)Math.exp((-0.5)*
                Math.pow(((marcaClase - parametrosCambioDistribucion.getMedia())/ parametrosCambioDistribucion.getDesvEst()),2));
        int multiplicador = (int)Math.pow(10,parametrosCambioDistribucion.getPresicion());
        probabilidad*=amplitud;
        int probAux = (int)(probabilidad*multiplicador);
        return ((float)probAux/multiplicador);

    }
}
