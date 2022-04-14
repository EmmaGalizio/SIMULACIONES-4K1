package emma.galzio.simulaciones_tp1_javafx.controller.chiCuadrado;

import emma.galzio.simulaciones_tp1_javafx.modelo.Pseudoaleatorio;
import emma.galzio.simulaciones_tp1_javafx.modelo.ResultadoBondadAjuste;

public interface IPruebaChiCuadrado {

    ResultadoBondadAjuste generarPruebaChiCuadrado(Pseudoaleatorio [] pseudoaleatorios, int k, int presicion);

}
