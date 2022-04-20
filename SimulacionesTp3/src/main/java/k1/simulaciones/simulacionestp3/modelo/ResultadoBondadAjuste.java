package k1.simulaciones.simulacionestp3.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoBondadAjuste {

    private Intervalo[] distFrecuencia;
    private List<Intervalo> distribucionChiCuadrado;
    private Pseudoaleatorio[] valoresGenerados;
    private float estadisticoObsChiCuadrado;
    private float estatisticoEspChiCuadrado;

    public int getN(){
        return valoresGenerados.length;
    }


}
