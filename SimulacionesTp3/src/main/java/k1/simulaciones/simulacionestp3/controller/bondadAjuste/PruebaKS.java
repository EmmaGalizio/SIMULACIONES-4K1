import k1.simulaciones.simulacionestp3.controller.bondadAjuste.IPruebaKS;
import k1.simulaciones.simulacionestp3.modelo.IntervaloKS;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjuste;
import org.springframework.stereotype.Component;

import java.util.List;

public class PruebaKS implements IPruebaKS {
    @Override
    public ResultadoBondadAjuste generarPruebaKS(List<IntervaloKS> distFrecuencia, ParametrosCambioDistribucion parametros) {
        return null;
    }
}

