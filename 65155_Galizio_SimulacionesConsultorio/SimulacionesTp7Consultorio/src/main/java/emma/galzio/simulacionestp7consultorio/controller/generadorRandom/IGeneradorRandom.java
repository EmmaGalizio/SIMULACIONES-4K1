package emma.galzio.simulacionestp7consultorio.controller.generadorRandom;


import emma.galzio.simulacionestp7consultorio.modelo.ParametrosGenerador;
import emma.galzio.simulacionestp7consultorio.modelo.Pseudoaleatorio;

public interface IGeneradorRandom {

    Pseudoaleatorio[] generar(ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(ParametrosGenerador parametrosGenerador);

    default double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }


}
