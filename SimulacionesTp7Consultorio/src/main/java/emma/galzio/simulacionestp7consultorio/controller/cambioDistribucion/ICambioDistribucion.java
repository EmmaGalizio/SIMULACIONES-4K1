package emma.galzio.simulacionestp7consultorio.controller.cambioDistribucion;


import emma.galzio.simulacionestp7consultorio.modelo.ParametrosCambioDistribucion;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosGenerador;
import emma.galzio.simulacionestp7consultorio.modelo.Pseudoaleatorio;
import emma.galzio.simulacionestp7consultorio.modelo.VaribaleAleatoria;
import emma.galzio.simulacionestp7consultorio.modelo.bondadAjuste.Intervalo;

public interface ICambioDistribucion {

    Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador);

    Intervalo[] generarDistFrecuenciaInicial(Pseudoaleatorio[] pseudoaleatorios, ParametrosCambioDistribucion parametrosCambioDistribucion);

    VaribaleAleatoria siguienteRandom(ParametrosCambioDistribucion parametrosCambioDistribucion, ParametrosGenerador parametrosGenerador,
                                      Pseudoaleatorio randomCUBase);

    default double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

}
