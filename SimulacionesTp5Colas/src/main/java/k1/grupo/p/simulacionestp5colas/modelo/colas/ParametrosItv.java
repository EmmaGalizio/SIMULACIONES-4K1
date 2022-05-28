package k1.grupo.p.simulacionestp5colas.modelo.colas;

import lombok.Data;

@Data
public class ParametrosItv {

    private float maxMinutosSimular;
    private float lambdaExpServicioNave;
    private float lambdaExpServCaseta;
    private float lambdaExpServicioOficina;
    private float lambdaExpLlegadasClientes;
    private int cantEmpCaseta;
    private int cantEmpNave;
    private int cantEmpOficina;
    private int cantFilasMostrar;
    private int mostrarFilaDesde;

    public void validar(){

        StringBuilder stringBuilder = new StringBuilder();
        if(cantEmpCaseta <1 || cantEmpNave <1 || cantEmpOficina < 1)
            stringBuilder.append("Debe indicar al menos 1 empleado en cada puesto\n");
        if(maxMinutosSimular < 1) stringBuilder.append("Se debe simular al menos 1 minuto\n");
        if(lambdaExpLlegadasClientes < 0) stringBuilder.append("En promedio debe llegar más de 1 cliente por unidad de tiempo\n");
        if(lambdaExpServCaseta < 0) stringBuilder.append("En promedio debe atender a más de 1 cliente por unidad de tiempo en la caseta\n");
        if(lambdaExpServicioOficina < 0) stringBuilder.append("En promedio debe atender a más de 1 cliente por unidad de tiempo en la oficina\n");
        if(lambdaExpServicioNave < 0) stringBuilder.append("En promedio debe atender a más de 1 cliente por unidad de tiempo en la nave\n");
        if(stringBuilder.length() > 0)
            throw new IllegalArgumentException(stringBuilder.toString());

    }

}
