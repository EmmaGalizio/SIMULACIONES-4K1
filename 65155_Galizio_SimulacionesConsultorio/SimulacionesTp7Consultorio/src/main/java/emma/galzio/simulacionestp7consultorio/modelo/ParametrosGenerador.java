package emma.galzio.simulacionestp7consultorio.modelo;

import lombok.Data;

@Data
public class ParametrosGenerador {

    private int n;
    private int x0;
    private int g;
    private int k;
    private int c;
    private int precision;
    private String metodoGeneradorRandom;

    public ParametrosGenerador() {
    }

    public ParametrosGenerador(int n, int precision) {
        this.n = n;
    }

    public ParametrosGenerador(int n, int x0, int g, int k, int c,int precision) {
        this.n = n;
        this.x0 = x0;
        this.g = g;
        this.k = k;
        this.c = c;
    }

    public ParametrosGenerador(int n, int x0, int g, int k,int precision) {
        this.n = n;
        this.x0 = x0;
        this.g = g;
        this.k = k;
    }

}
