package k1.simulaciones.simulacionestp3.controller.cambioDistribucion;

import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component(ConstantesCambioDistribucion.POISSON)
public class CambioDistribucionPoisson implements ICambioDistribucion{
    @Override
    public Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros,
                                                 ParametrosGenerador... parametrosGenerador) {

        if(parametros.getLambda() <= 0){
            parametros.setLambda(parametros.getMedia());
        }


        return new Pseudoaleatorio[0];
    }
}
