package p.grupo.k1.simulacionestp6.controller.cambioDistribucion;



import p.grupo.k1.simulacionestp6.modelo.ParametrosCambioDistribucion;
import p.grupo.k1.simulacionestp6.modelo.ParametrosGenerador;
import p.grupo.k1.simulacionestp6.modelo.Pseudoaleatorio;
import p.grupo.k1.simulacionestp6.modelo.VaribaleAleatoria;
import p.grupo.k1.simulacionestp6.modelo.bondadAjuste.Intervalo;

public interface ICambioDistribucion {

    Pseudoaleatorio[] cambiarDistribucion(ParametrosCambioDistribucion parametros, ParametrosGenerador... parametrosGenerador);

    Intervalo[] generarDistFrecuenciaInicial(Pseudoaleatorio[] pseudoaleatorios, ParametrosCambioDistribucion parametrosCambioDistribucion);

    VaribaleAleatoria siguienteRandom(ParametrosCambioDistribucion parametrosCambioDistribucion, ParametrosGenerador parametrosGenerador,
                                      Pseudoaleatorio randomCUBase);

}
