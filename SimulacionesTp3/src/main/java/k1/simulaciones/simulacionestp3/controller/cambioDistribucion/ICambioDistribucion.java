package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;

public interface ICambioDistribucion {

    Pseudoaleatorio[] cambiarDistribucion(Pseudoaleatorio[] pseudosUniforme, ParametrosCambioDistribucion parametros);

}
