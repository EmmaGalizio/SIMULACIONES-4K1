package k1.simulaciones.simulacionestp3.controller.bondadAjuste;


import k1.simulaciones.simulacionestp3.modelo.IntervaloKS;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjuste;

import java.util.ArrayList;
import java.util.List;

public interface IPruebaKS {

    ResultadoBondadAjuste generarPruebaKS(List<IntervaloKS> distFrecuencia, ParametrosCambioDistribucion parametros);

    default List<IntervaloKS> calcularIntervalosKS(IntervaloKS[] intervalos){

        float espAcumI = 0, acumJ = 0;
        int li = 0, obsAcumI=0;
        List<IntervaloKS> intervalosKS = new ArrayList<>();

        for(int i = 0; i < intervalos.length; i++){
            espAcumI += intervalos[i].getFrecEsp();
            obsAcumI += intervalos[i].getFrecObs();
            IntervaloKS intervalo = new IntervaloKS();
            intervalo.setLimInf(intervalos[li].getLimInf());
            intervalo.setLimSup(intervalos[i].getLimSup());
            intervalo.setFrecEsp(espAcumI);
            intervalo.setFrecObs(obsAcumI);
            intervalosKS.add(intervalo);
            espAcumI = 0;
            obsAcumI = 0;
            acumJ = 0;
            li = i+1;




        }
        return intervalosKS;
    }

}
