package k1.simulaciones.simulacionestp3.modelo;

import lombok.Data;

@Data
public class IntervaloKS {

    private float limInf;
    private float marcaClase;
    private float limSup;
    private int frecObs;
    private float probObs;
    private float frecEsp;
    private float probEsp;
    private float probObsAC;
    private float probEspAC;

    private float estadistico;
    private float estadisticoAcumulado;

    public void incrementarFrecObservada(){
        if(frecObs < 0) frecObs=0;
        frecObs++;
    }

}