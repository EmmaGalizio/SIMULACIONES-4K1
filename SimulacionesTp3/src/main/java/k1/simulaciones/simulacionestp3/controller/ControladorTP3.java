package k1.simulaciones.simulacionestp3.controller;

import k1.simulaciones.simulacionestp3.controller.cambioDistribucion.ICambioDistribucion;
import k1.simulaciones.simulacionestp3.controller.utils.DistribucionEsperadaChiCuadrado;
import k1.simulaciones.simulacionestp3.modelo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ControladorTP3 {

    private final Map<String, ICambioDistribucion> metodosCambioDistribucion;

    public ResultadoBondadAjuste generarDistribucionFrecuencia(ParametrosCambioDistribucion parametrosCambioDistribucion,
                                                               DistribucionEsperadaChiCuadrado distEsperada,
                                                               ParametrosGenerador... parametrosGeneradores){

        if(parametrosCambioDistribucion == null|| parametrosGeneradores == null || distEsperada == null)
            throw new IllegalArgumentException("Debe indicar los parámetros para el generador random uniforme y de la distribucion esperada");

        ICambioDistribucion metodoCambioDist = metodosCambioDistribucion.get(distEsperada.getId());
        //Hacer verificaciones en todas las clases de cambio de distribución,
        // principalmente el n del parametrosCambioDistribucion
        Pseudoaleatorio[] randomsGenerados = metodoCambioDist
                .cambiarDistribucion(parametrosCambioDistribucion,parametrosGeneradores);

        Intervalo[] distFrecuenciaInicial = metodoCambioDist
                .generarDistFrecuenciaInicial(randomsGenerados,parametrosCambioDistribucion);
        //Al generar la distribución de frecuencias inicial dentro de la clase que se
        //encarga de cambiar la distribución, entonces la prueba de chi cuadrado ya es la misma para todos
        //Por lo que se puede implementar solo una clase de IPruebaChiCuadrado
        //Hay que ver bien el tema de llenar la distribución inicial en cada cambio de distribución
        //Y calcular bien la probabilidad esperada de cada intervalo dependiendo de la distribucion
        return null;

    }




}
