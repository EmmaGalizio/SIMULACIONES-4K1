package emma.galzio.simulacionestp7consultorio.modelo;

import lombok.Data;

@Data
public class ParametrosConsultorio {

    private float lambdaLlegadaTurno;
    private float lambdaLlegadaEstudio;
    private float unifASecretaria;
    private float unifBSecretaria;
    private float unifATecnico;
    private float unifBTecnico;
    private int turnosDisponiblesDiario;
    private ParametrosGenerador parametrosLlegadaTurno;
    private ParametrosGenerador parametrosLlegadaEstudio;
    private ParametrosGenerador parametrosSecretaria;
    private ParametrosGenerador parametrosTecnico;
    private Pseudoaleatorio randomBaseCULlegadaTurno;
    private Pseudoaleatorio randomBaseCULlegadaEstudio;
    private Pseudoaleatorio randomBaseCUSecretaria;
    private Pseudoaleatorio randomBaseCUTecnico;
    private int diasASimular;
    private int cantFilasMostrar;
    private int primeraFila;

    public boolean validarParametrosGeneradores(){
        return parametrosLlegadaEstudio != null && parametrosLlegadaTurno != null &&
                parametrosSecretaria != null && parametrosTecnico != null;
    }

}
