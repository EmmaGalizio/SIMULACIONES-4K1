package p.grupo.simulacionestp4montecarlo.controller.generadorRandom;


import p.grupo.simulacionestp4montecarlo.modelo.ParametrosGenerador;
import p.grupo.simulacionestp4montecarlo.modelo.Pseudoaleatorio;

public interface IGeneradorRandom {

    Pseudoaleatorio[] generar(ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(ParametrosGenerador parametrosGenerador);


}
