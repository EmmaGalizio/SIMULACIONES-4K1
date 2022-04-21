package k1.simulaciones.simulacionestp3.controller.bondadAjuste;


import k1.simulaciones.simulacionestp3.modelo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PruebaKS implements IPruebaKS {
    @Override
    public ResultadoBondadAjuste generarPruebaKS(ResultadoBondadAjuste resultado,  ParametrosCambioDistribucion parametros) {
        List<IntervaloKS> intervaloKS = new ArrayList<>();
        float max = calcularIntervalosKS(resultado.getDistFrecuencia(), intervaloKS);
        resultado.setEstadisticoObsKS(max);

        float estKS = obtenerValorKS(intervaloKS.size());
        resultado.setDistribucionKS(intervaloKS);

        resultado.setEstatisticoEspKS(estKS);

        return resultado;
    }



    private float  calcularIntervalosKS(Intervalo[] intervalos,  List<IntervaloKS> intervalosKS ){

        float probEspAcumI = 0;
        int li = 0;
        float probObsAcumI=0;
        float absDif;

        float max = 0;
        for(int i = 0; i < intervalos.length; i++){
            probEspAcumI += intervalos[i].getProbEsp();
            probObsAcumI += intervalos[i].getProbObs();
            absDif  = Math.abs((float)(probObsAcumI-probEspAcumI));
            max = (absDif>max)? absDif : max;
            IntervaloKS intervalo = new IntervaloKS();
            intervalo.setLimInf(intervalos[i].getLimInf());
            intervalo.setLimSup(intervalos[i].getLimSup());
            intervalo.setMarcaClase(intervalos[i].getMarcaClase());
            intervalo.setProbObs(intervalos[i].getProbObs());
            intervalo.setProbEsp(intervalos[i].getProbEsp());
            intervalo.setProbEspAC(probEspAcumI);
            intervalo.setProbObsAC(probObsAcumI);
            intervalo.setFrecEsp(intervalos[i].getFrecEsp());
            intervalo.setFrecObs(intervalos[i].getFrecObs());
            intervalo.setDifAbs(absDif);
            intervalosKS.add(intervalo);

            probEspAcumI = 0;
            probObsAcumI = 0;
            //li = i+1;
        }
        return max;
    }
    private float obtenerValorKS(int intervalos){
        float[] KS = new float[36];
        KS[0] = 0.975f;
        KS[1] = 0.842f;
        KS[2] = 0.708f;
        KS[3] = 0.624f;
        KS[4] = 0.563f;
        KS[5] = 0.519f;
        KS[6] = 0.483f;
        KS[7] = 0.454f;
        KS[8] =0.43f;
        KS[9] = 0.409f;
        KS[10] = 0.391f;
        KS[11] = 0.375f;
        KS[12] = 0.361f;
        KS[13] = 0.349f;
        KS[14] = 0.338f;
        KS[15] = 0.327f;
        KS[16] = 0.318f;
        KS[17] = 0.309f;
        KS[18] = 0.301f;
        KS[19] = 0.294f;
        KS[20] = 0.287f;
        KS[21] = 0.281f;
        KS[22] = 0.275f;
        KS[23] = 0.269f;
        KS[24] = 0.264f;
        KS[25] = 0.259f;
        KS[26] = 0.254f;
        KS[27] = 0.249f;
        KS[28] = 0.246f;
        KS[29] = 0.242f;
        KS[30] = 0.238f;
        KS[31] = 0.234f;
        KS[32] = 0.231f;
        KS[33] = 0.227f;
        KS[34] = 0.224f;
        KS[35] = 1.36f/(float)Math.sqrt(intervalos);
        //Devuelve la posicion intervalos-2 porque el indice del array empieza en 0 y,
        //al ser uniforme la distribución no hay valores empíricos, o sea m=0;
        if(intervalos <= 36)return KS[intervalos - 1];
        return KS[35];
    }

}
