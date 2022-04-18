package k1.simulaciones.simulacionestp3.controller.generadorRandom;


import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import k1.simulaciones.simulacionestp3.modelo.Pseudoaleatorio;

public interface IGeneradorRandom {

    Pseudoaleatorio[] generar(ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros);

}
