package p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import p.grupo.simulacionestp4montecarlo.modelo.Pseudoaleatorio;
import p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste.Intervalo;

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