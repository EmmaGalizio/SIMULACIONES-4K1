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

    private double xm;
    private double ym;
    private double xmp1;
    private double k1;
    private double k2;
    private double k3;
    private double k4;
    private double ymp1;


}
