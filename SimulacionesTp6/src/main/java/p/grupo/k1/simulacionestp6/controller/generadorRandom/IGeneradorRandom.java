package p.grupo.k1.simulacionestp6.controller.generadorRandom;


import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;

public interface IGeneradorRandom {

    Pseudoaleatorio[] generar(ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(ParametrosGenerador parametrosGenerador);


}
