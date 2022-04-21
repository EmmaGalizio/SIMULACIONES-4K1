package k1.simulaciones.simulacionestp3.modelo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoBondadAjusteKS {

    private  Intervalo[] distFrecuencia;
    private  List<Intervalo> distribucionKS;
    private  Pseudoaleatorio[] valoresGenerados;
    private  float estadisticoObsKS;
    private  float estatisticoEspKS;

    public int getN(){
        return valoresGenerados.length;
    }


}