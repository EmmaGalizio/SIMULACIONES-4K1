package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import org.springframework.stereotype.Component;

@Component(ConstantesCambioDistribucion.EXP)
public class CambioDistribucionExponencial implements ICambioDistribucion{

    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador) {

        return new Pseudoaleatorio[0];
    }
}
