package p.grupo.simulacionestp4montecarlo.modelo.montecarlo;

import lombok.Data;

@Data
public class VectorEstadoMontecarloPuerto {

    private long dia;
    private float randomIngresos;
    private int cantIngresos;
    private int cantActualMuelle;
    private float randomDescargas;
    private int cantDescargas;
    private float costoDescarga;
    private int cantDescargasPostergadas;
    private float costoDemora;
    private float costoTotalDia;
    private float costoAcumulado;
    private float costoDemoraAcumulado;
    private long diasDemoraAcumulada;
    private int barcosPostergadosAcum;

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
