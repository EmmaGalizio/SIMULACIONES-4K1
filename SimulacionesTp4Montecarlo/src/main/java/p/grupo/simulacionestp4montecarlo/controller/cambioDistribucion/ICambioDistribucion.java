package p.grupo.simulacionestp4montecarlo.controller.cambioDistribucion;


import p.grupo.simulacionestp4montecarlo.modelo.VaribaleAleatoria;
import p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste.Intervalo;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosCambioDistribucion;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosGenerador;
import p.grupo.simulacionestp4montecarlo.modelo.Pseudoaleatorio;

public interface ICambioDistribucion {

    Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador);

    Intervalo[] generarDistFrecuenciaInicial(Pseudoaleatorio[] pseudoaleatorios, ParametrosCambioDistribucion parametrosCambioDistribucion);

    VaribaleAleatoria siguienteRandom(ParametrosCambioDistribucion parametrosCambioDistribucion, ParametrosGenerador parametrosGenerador,
                                      Pseudoaleatorio randomCUBase);

}
