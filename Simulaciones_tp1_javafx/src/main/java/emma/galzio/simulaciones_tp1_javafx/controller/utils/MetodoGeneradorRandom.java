package emma.galzio.simulaciones_tp1_javafx.controller.utils;

import lombok.Getter;

@Getter
public class MetodoGeneradorRandom {

    private String metodo;
    private String id;

    private MetodoGeneradorRandom(String metodo, String id){
        this.metodo = metodo;
        this.id = id;
    }

    public static MetodoGeneradorRandom getInstanceLineal(){
        return new MetodoGeneradorRandom("Cong. Lineal", ConstantesGenerador.LINEAL);
    }
    public static MetodoGeneradorRandom getInstanceMultiplicativo(){
        return new MetodoGeneradorRandom("Cong. Multiplicativo", ConstantesGenerador.MULTIPLICATIVO);
    }
    public static MetodoGeneradorRandom getInstanceLenguaje(){
        return new MetodoGeneradorRandom("Lenguaje", ConstantesGenerador.LENGUAJE);
    }
    @Override
    public String toString(){
        return metodo;
    }

}
