package k1.simulaciones.simulacionestp3.modelo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResultadoBondadAjuste {

    private final Intervalo[] distFrecuencia;
    private final Pseudoaleatorio[] valoresGenerados;
    private final float estadisticoObs;
    private final float estatisticoEsp;

    public int getN(){
        return valoresGenerados.length;
    }


}
