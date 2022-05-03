package p.grupo.simulacionestp4montecarlo.modelo.montecarlo;

import lombok.Data;

/***
 * Representa una fila de la tabla del método montecarlo
 */
@Data
public class VectorEstadoMontecarloPuerto {
    //Representa el día que se está simulando en esta fila
    private long dia;
    //Representa el random uniforme 0-1 que se genera para obtener la cantidad de ingresos en base a la tabla de probabilidades
    private float randomIngresos;
    //Representa la cantidad de ingresos, en la primera simulación se calcula a partir de randomIngresos
    //En la segunda se calcula directamente con un random discreto con la distribusión de Poisson
    //De todas formas para calcular el random de Poisson se necesitaría un random uniforme 0-1 como base
    private int cantIngresos;
    //Representa la suma entre los barcos que ingresaron el día actual además de los que quedaron postergados
    private int cantActualMuelle;
    //Random uniforme 0-1 para obtener la cantidad de descargas del día a partir de la tabla de probabilidades
    private float randomDescargas;
    //Cantidad de barcos que se descargaron en e día actual
    //Si el random supera la cantidad actual en el muelle entonces cantDescargas=cantActualMuelle
    private int cantDescargas;
    //Costo total de las descargas del día, para cada descarga se genera un random normal(800,120) y se acumulan
    private float costoDescarga;
    //La cantidad de barcos que quedan para ser descargados al día siguiente, es la cantidad actual en muella menos
    //la cantidad de descargas
    private int cantDescargasPostergadas;
    //Representa el costo que implica que los barcos demorados pasen la noche en el puerto, 1500 por barco, 0 -3200 si
    //no hay demora
    private float costoDemora;
    //Sumatoria entre costoDescarga y costoDemora
    private float costoTotalDia;
    //Acumulación de costoTotalDia, sirve para calcular el costo promedio diario
    private double costoAcumulado;
    //Acumulación de costoDemora, sirve para calcular el costo de demora promedio diario
    private double costoDemoraAcumulado;
    //Cantidad de días en que se produjo una demora, se incrementa de a 1, sirve para calcular el porcentaje
    //de días que el muelle estuvo ocupado
    private long diasDemoraAcumulada;
    //Acumulación de cantDescargasPostergadas, sirve para calcular el promedio de barcos demorados por día
    private long barcosPostergadosAcum;

    //Están acá para no agregar lógica innecesaria al controlador, siendo que el vector es el que tiene la información
    //y "sabe" como se calcula. Además se puede reutilizar para ambas simulaciones
    public void calcularCostoTotalDia(){
        costoTotalDia = costoDescarga+costoDemora;
    }
    public void acumularCostoTotalDiario(){
        costoAcumulado+=costoTotalDia;
    }
    public void acumularCostoDemora(){
        costoDemoraAcumulado+=costoDemora;
    }
    public void acumularDiasDemora(){
        if(cantDescargasPostergadas > 0) diasDemoraAcumulada++;
    }
    public void calcularCostoDemora(){
        costoDemora = (getCantDescargasPostergadas() >0)?
                getCantDescargasPostergadas()*1500:
                (-3200);
    }
    public void acumularBarcosPostergados(){
        barcosPostergadosAcum+=cantDescargasPostergadas;
    }

    @Override
    public String toString(){

        return dia+"\t"+randomIngresos+"\t"+cantIngresos+"\t"+randomDescargas+"\t"+
                cantDescargas+"\t"+cantDescargasPostergadas+"\t"+costoDescarga+"\t"+
                costoDemora+"\t"+costoTotalDia+"\t"+costoAcumulado+"\t"+costoDemoraAcumulado+"\t"+
                diasDemoraAcumulada+"\t"+barcosPostergadosAcum;

    }



}
