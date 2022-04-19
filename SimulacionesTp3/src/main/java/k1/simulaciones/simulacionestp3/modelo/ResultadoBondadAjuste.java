package k1.simulaciones.simulacionestp3.modelo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ResultadoBondadAjuste {

    private final Intervalo[] distFrecuencia;
    private final List<Intervalo> distribucionChiCuadrado;
    private final Pseudoaleatorio[] valoresGenerados;
    private final float estadisticoObsChiCuadrado;
    private final float estatisticoEspChiCuadrado;

    public int getN(){
        return valoresGenerados.length;
    }


}
