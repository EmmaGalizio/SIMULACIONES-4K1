package emma.galzio.simulaciones_tp1_javafx.controller.utils;

import lombok.Getter;

@Getter
public class DistribucionEsperadaChiCuadrado {

    private String distribucion;
    private String id;

    private DistribucionEsperadaChiCuadrado(String distribucion, String id) {
        this.distribucion = distribucion;
        this.id = id;
    }

    public static DistribucionEsperadaChiCuadrado getInstanceUniforme(){
        return new DistribucionEsperadaChiCuadrado("Dist. Uniforme",
                ConstantesDistribucionChiCuadrado.UNIFORME);
    }

    public String toString(){
        return distribucion;
    }
}
