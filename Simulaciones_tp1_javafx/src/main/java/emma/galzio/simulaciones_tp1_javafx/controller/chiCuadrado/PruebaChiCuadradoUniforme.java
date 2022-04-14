package emma.galzio.simulaciones_tp1_javafx.controller.chiCuadrado;

import emma.galzio.simulaciones_tp1_javafx.controller.utils.ConstantesDistribucionChiCuadrado;
import emma.galzio.simulaciones_tp1_javafx.modelo.Intervalo;
import emma.galzio.simulaciones_tp1_javafx.modelo.Pseudoaleatorio;
import emma.galzio.simulaciones_tp1_javafx.modelo.ResultadoBondadAjuste;
import org.springframework.stereotype.Component;

@Component(ConstantesDistribucionChiCuadrado.UNIFORME)
public class PruebaChiCuadradoUniforme implements IPruebaChiCuadrado{
    @Override
    public ResultadoBondadAjuste generarPruebaChiCuadrado(Pseudoaleatorio[] pseudoaleatorios,
                                                                            int k, int presicion) {
        if(pseudoaleatorios == null) return null;
        if(k <= 0) k = (int)Math.sqrt(pseudoaleatorios.length);
        if(presicion <= 0 || presicion > 9) presicion = 4;

        Intervalo[] distFrecuencia = new Intervalo[k];
        inicializarDistribucionFrecuencia(distFrecuencia, presicion, pseudoaleatorios.length);
        generarDistribucionFrecuencia(distFrecuencia,pseudoaleatorios,presicion);
        float estChiCuadrado = generarEstadisticoChiCuadrado(distFrecuencia,presicion);
        float estTabulado = obtenerValorChiCuadrado(k);
        return new ResultadoBondadAjuste(distFrecuencia,pseudoaleatorios,estChiCuadrado,estTabulado);
    }

    private float generarEstadisticoChiCuadrado(Intervalo[] distFrecuencia, int presicion){
//Probar por que mostraba tantos decimales!!!
        float estAcum = 0.0f;
        int multiplicador = (int)Math.pow(10,presicion);

        for(Intervalo intervalo : distFrecuencia){
            float numerador = ((int)(Math.pow(intervalo.getFrecEsp()- intervalo.getFrecObs(),2)*multiplicador))/(float)multiplicador;
            float est = ((int)(numerador/ intervalo.getFrecEsp()*multiplicador))/(float)multiplicador;
            estAcum+=est;
            estAcum = ((int)(estAcum*multiplicador))/(float)multiplicador;
            intervalo.setEstadistico(est);
            intervalo.setEstadisticoAcumulado(estAcum);
        }
        return estAcum;
    }

    private void inicializarDistribucionFrecuencia(Intervalo[] distFrecuencia, int presicion, int n) {

        int multiplicador = (int)Math.pow(10,presicion);
        float amplitudIntervalo = ((int)(((float)1/distFrecuencia.length)*multiplicador))/(float) multiplicador;
        float limInf = 0.0f;
        for(int i = 0; i < distFrecuencia.length; i++){
            Intervalo intervalo = new Intervalo();
            intervalo.setLimInf(limInf);
            float limSup = limInf + amplitudIntervalo;
            limSup = ((int)(limSup*multiplicador)/(float)multiplicador);
            intervalo.setLimSup(limSup);
            float marcaClase = ((int)(((limSup+limInf)/2)*multiplicador))/(float)multiplicador;
            intervalo.setMarcaClase(marcaClase);
            float frecEsp = ((int)(((float)n/ distFrecuencia.length)*multiplicador))/(float)multiplicador;
            intervalo.setFrecEsp(frecEsp);
            float probEsp = ((int)((frecEsp/n)*multiplicador))/(float)multiplicador;
            intervalo.setProbEsp(probEsp);
            distFrecuencia[i] = intervalo;
            limInf = limSup;
            limInf = ((int)(limInf*multiplicador)/(float)multiplicador);
            //En todos los casos se puede setear la frecuencia esperada y prob esperada.
        }
    }
    private void generarDistribucionFrecuencia(Intervalo[] distFrecuencia, Pseudoaleatorio[] pseudoaleatorios, int presicion) {

        for(Pseudoaleatorio random : pseudoaleatorios){

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
            float probObs = ((float)intervalo.getFrecObs()/ pseudoaleatorios.length);
            probObs = ((int)(probObs*multiplicador))/(float)multiplicador;
            intervalo.setProbObs(probObs);
        }

    }
    private float obtenerValorChiCuadrado(int intervalos){
        float[] chiCuadrado = new float[20];
        chiCuadrado[0] = 3.8f;
        chiCuadrado[1] = 6f;
        chiCuadrado[2] = 7.8f;
        chiCuadrado[3] = 9.5f;
        chiCuadrado[4] = 11.1f;
        chiCuadrado[5] = 12.6f;
        chiCuadrado[6] = 14.1f;
        chiCuadrado[7] = 15.5f;
        chiCuadrado[8] =16.9f;
        chiCuadrado[9] = 18.3f;
        chiCuadrado[10] = 19.7f;
        chiCuadrado[11] = 21f;
        chiCuadrado[12] = 22.4f;
        chiCuadrado[13] = 23.7f;
        chiCuadrado[14] = 25f;
        chiCuadrado[15] = 26.3f;
        chiCuadrado[16] = 27.6f;
        chiCuadrado[17] = 28.9f;
        chiCuadrado[18] = 30.1f;
        chiCuadrado[19] = 31.4f;
        //Devuelve la posicion intervalos-2 porque el indice del array empieza en 0 y,
        //al ser uniforme la distribución no hay valores empíricos, o sea m=0;
        return chiCuadrado[intervalos - 2];
    }

}
