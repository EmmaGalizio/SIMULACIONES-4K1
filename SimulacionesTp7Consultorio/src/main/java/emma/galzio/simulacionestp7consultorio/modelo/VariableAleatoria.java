package emma.galzio.simulacionestp7consultorio.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Representa un random generado individualmente con algún método de cambio de distribución
 * randomGenerado: Representa el random generado en la distribución deseada
 * siguienteRandomBase: Representa el random uniforme 0-1 que se utilizará como base
 * para la obtención del siguiente random en la distribución deseada
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariableAleatoria {

    private float randomGenerado;
    private Pseudoaleatorio siguienteRandomBase;


}
