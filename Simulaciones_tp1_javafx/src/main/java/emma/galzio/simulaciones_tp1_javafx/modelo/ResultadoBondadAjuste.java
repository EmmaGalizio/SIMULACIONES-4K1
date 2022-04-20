package emma.galzio.simulaciones_tp1_javafx.modelo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoBondadAjuste {

    private  Intervalo[] distFrecuencia;
    private  Pseudoaleatorio[] valoresGenerados;
    private  float estadisticoObs;
    private  float estatisticoEsp;

    public int getN(){
        return valoresGenerados.length;
    }


}
