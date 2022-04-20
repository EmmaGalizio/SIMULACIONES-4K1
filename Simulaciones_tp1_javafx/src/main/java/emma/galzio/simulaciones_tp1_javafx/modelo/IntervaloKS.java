package emma.galzio.simulaciones_tp1_javafx.modelo;

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
    private float probEspAC;
    private float probObsAC;
    private float estadistico;
    private float estadisticoAcumulado;

    public void incrementarFrecObservada(){
        if(frecObs < 0) frecObs=0;
        frecObs++;
    }

}
