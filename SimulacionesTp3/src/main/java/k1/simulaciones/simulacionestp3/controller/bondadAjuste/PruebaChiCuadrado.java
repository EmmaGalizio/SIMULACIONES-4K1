package k1.simulaciones.simulacionestp3.controller.bondadAjuste;

import k1.simulaciones.simulacionestp3.modelo.Intervalo;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjuste;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PruebaChiCuadrado implements IPruebaChiCuadrado{
    @Override
    public ResultadoBondadAjuste generarPruebaChiCuadrado(Intervalo[] distFrecuencia, ParametrosCambioDistribucion parametros) {

        List<Intervalo> intervalosChiCuadrado = calcularIntervalosChiCuadrado(distFrecuencia);
        ResultadoBondadAjuste resultadoBondadAjuste = new ResultadoBondadAjuste();
        resultadoBondadAjuste.setDistFrecuencia(distFrecuencia);

        float estChiCuadradoCalc = generarEstadisticoChiCuadrado(intervalosChiCuadrado,
                                                                                parametros.getPresicion());
        float estTabulado = obtenerValorChiCuadrado(intervalosChiCuadrado.size());
        resultadoBondadAjuste.setDistribucionChiCuadrado(intervalosChiCuadrado);
        resultadoBondadAjuste.setEstadisticoObsChiCuadrado(estChiCuadradoCalc);
        resultadoBondadAjuste.setEstatisticoEspChiCuadrado(estTabulado);

        return resultadoBondadAjuste;
    }

    private float generarEstadisticoChiCuadrado(List<Intervalo> distFrecuencia, int presicion){
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
