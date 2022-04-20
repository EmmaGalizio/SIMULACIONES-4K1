package k1.simulaciones.simulacionestp3.modelo;

import lombok.Data;

@Data
public class ParametrosCambioDistribucion {

    private int n;
    private float unifA, unifB;
    private float media;
    private float desvEst;
    private float lambda;
    private int presicion;
    private int kInicial;

}
