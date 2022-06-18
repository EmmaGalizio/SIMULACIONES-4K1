package p.grupo.k1.simulacionestp6.modelo.rungeKutta;

import lombok.Data;

/***
 * Xm: Valor de la variable independiente en el momento actual.
 * Xmp1: Valor de la variable independiente en el proximo momento.
 * Ym: Valor de la variable dependiente en el momento actual.
 * Ym+1: Valor de la variable dependiente en el proximo momento
 */
@Data
public class ResultadoRungeKutta {

    private float xm;
    private float ym;
    private float xmp1;
    private float k1;
    private float k2;
    private float k3;
    private float k4;
    private float ymp1;


}
