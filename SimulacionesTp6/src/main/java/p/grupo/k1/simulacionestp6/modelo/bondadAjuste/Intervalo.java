package p.grupo.k1.simulacionestp6.modelo.bondadAjuste;

import lombok.Data;

@Data
public class Intervalo {

    private float limInf;
    private float marcaClase;
    private float limSup;
    private int frecObs;
    private float probObs;
    private float frecEsp;
    private float probEsp;
    private float estadistico;
    private float estadisticoAcumulado;

    public void incrementarFrecObservada(){
        if(frecObs < 0) frecObs=0;
        frecObs++;
    }

}
