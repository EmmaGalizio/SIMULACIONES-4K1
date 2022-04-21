package k1.simulaciones.simulacionestp3.controller.bondadAjuste;


import k1.simulaciones.simulacionestp3.modelo.IntervaloKS;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjuste;
import k1.simulaciones.simulacionestp3.modelo.ResultadoBondadAjusteKS;

import java.util.ArrayList;
import java.util.List;

public interface IPruebaKS {

    ResultadoBondadAjuste generarPruebaKS(ResultadoBondadAjuste resultado, ParametrosCambioDistribucion parametros);



}
