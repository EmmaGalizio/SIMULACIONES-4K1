package emma.galzio.simulaciones_tp1_javafx.controller.generadorRandom;

import emma.galzio.simulaciones_tp1_javafx.controller.utils.ConstantesGenerador;
import emma.galzio.simulaciones_tp1_javafx.modelo.Pseudoaleatorio;
import emma.galzio.simulaciones_tp1_javafx.modelo.ParametrosGenerador;

public interface IGeneradorRandom {

    Pseudoaleatorio[] generar(ParametrosGenerador parametros);
    Pseudoaleatorio siguientePseudoAleatoreo(Pseudoaleatorio pseudoaleatorio, ParametrosGenerador parametros);

}
