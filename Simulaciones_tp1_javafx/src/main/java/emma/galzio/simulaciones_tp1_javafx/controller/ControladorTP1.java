package emma.galzio.simulaciones_tp1_javafx.controller;

import emma.galzio.simulaciones_tp1_javafx.controller.chiCuadrado.IPruebaChiCuadrado;
import emma.galzio.simulaciones_tp1_javafx.controller.generadorRandom.IGeneradorRandom;
import emma.galzio.simulaciones_tp1_javafx.modelo.ParametrosGenerador;
import emma.galzio.simulaciones_tp1_javafx.modelo.Pseudoaleatorio;
import emma.galzio.simulaciones_tp1_javafx.modelo.ResultadoBondadAjuste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ControladorTP1 {

    @Autowired
    private Map<String, IGeneradorRandom> generadoresRandomMap;
    @Autowired
    private Map<String, IPruebaChiCuadrado> calculadoresChiCuadrado;

    public Pseudoaleatorio[] generarPseudoAleatoreos(ParametrosGenerador parametros, String metodo){
        return generadoresRandomMap.get(metodo).generar(parametros);
    }

    public Pseudoaleatorio generarSiguientePseudo(ParametrosGenerador parametros, Pseudoaleatorio pseudoaleatorio, String metodo){
        return generadoresRandomMap.get(metodo).siguientePseudoAleatoreo(pseudoaleatorio,parametros);
    }
   //Podria generar un método para que vaya actualizando una distribución de frecuencia a medida que
   //se van generando los randoms individualmente

    public ResultadoBondadAjuste generarPruebaChiCuadrado(ParametrosGenerador parametros, String metodoPseudo,
                                                          int cantIntervalos, String distComparacion){

        if(parametros != null && parametros.getN() < 30) throw new IllegalArgumentException("Es recomendable N>=30");
        if(parametros == null) throw new IllegalArgumentException("Es necesario brindar los parametros del generador");
        Pseudoaleatorio [] pseudoaleatorios = generadoresRandomMap.get(metodoPseudo).generar(parametros);
        return calculadoresChiCuadrado.get(distComparacion).generarPruebaChiCuadrado(pseudoaleatorios,cantIntervalos,parametros.getPresicion());

    }

}
