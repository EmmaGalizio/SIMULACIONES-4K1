package p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import p.grupo.simulacionestp4montecarlo.modelo.Pseudoaleatorio;
import p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste.Intervalo;
import p.grupo.simulacionestp4montecarlo.modelo.bondadAjuste.IntervaloKS;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoBondadAjuste {

    private  Intervalo[] distFrecuencia;
    private  List<Intervalo> distribucionChiCuadrado;
    private  List<IntervaloKS> distribucionKS;
    private  Pseudoaleatorio[] valoresGenerados;
    private  float estadisticoObsChiCuadrado;
    private  float estatisticoEspChiCuadrado;
    private  float estadisticoObsKS;
    private  float estatisticoEspKS;

    public int getN(){
        return valoresGenerados.length;
    }


}
