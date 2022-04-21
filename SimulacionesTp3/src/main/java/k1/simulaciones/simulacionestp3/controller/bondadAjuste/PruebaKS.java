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

        float espAcumI = 0;
        int li = 0, obsAcumI=0;
        float absDif = 0;

        float max = 0;
        for(int i = 0; i < intervalos.length; i++){
            espAcumI += intervalos[i].getProbEsp();
            obsAcumI += intervalos[i].getProbEsp();
            absDif  = Math.abs(obsAcumI-espAcumI);
            max = (absDif>max)? absDif : max;
            IntervaloKS intervalo = new IntervaloKS();
            intervalo.setLimInf(intervalos[li].getLimInf());
            intervalo.setLimSup(intervalos[i].getLimSup());
            intervalo.setProbEsp(espAcumI);
            intervalo.setProbObs(obsAcumI);
            intervalosKS.add(intervalo);
            espAcumI = 0;
            obsAcumI = 0;

            li = i+1;





        }
        return max;
    }
    private float obtenerValorKS(int intervalos){
        float[] KS = new float[20];
        KS[0] = 0.97f;
        KS[1] = 0.84f;
        KS[2] = 0.70f;
        KS[3] = 0.62f;
        KS[4] = 0.56f;
        KS[5] = 0.51f;
        KS[6] = 0.48f;
        KS[7] = 0.45f;
        KS[8] =0.43f;
        KS[9] = 0.40f;
        KS[10] = 0.39f;
        KS[11] = 0.37f;
        KS[12] = 0.36f;
        KS[13] = 0.34f;
        KS[14] = 0.33f;
        KS[15] = 0.32f;
        KS[16] = 0.31f;
        KS[17] = 0.30f;
        KS[18] = 0.29f;
        KS[19] = 0.28f;
        //Devuelve la posicion intervalos-2 porque el indice del array empieza en 0 y,
        //al ser uniforme la distribución no hay valores empíricos, o sea m=0;
        return KS[intervalos - 1];
    }

}
