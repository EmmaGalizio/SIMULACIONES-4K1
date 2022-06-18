package p.grupo.k1.simulacionestp6.modelo.rungeKutta;

import lombok.Data;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;

import java.util.List;

@Data
public class ResultadoSimulacion {

    private List<VectorEstadoITV> simulacionItv;
    private List<ResultadoRungeKutta> ecDiferencialLlegadaAtaque;
    private List<ResultadoRungeKutta> ecDiferencialFinBloqueoLlegadas;
    private List<ResultadoRungeKutta> ecDiferencialFinBloqueoNaveUno;

    private float tiempoMedioOficina;
    private float tiempoMedioAtencionOficina;
    private float tiempoMedioPermanenciaSistema;
    private float porcTiempoLibreCaseta;
    private float porcTiempoLibreNave;
    private float porcTiempoLibreOficina;
    private float tiempoMedioColaCaseta;
    private float tiempoMedioColaNave;
    private float porcAtencionFinalizada;
    private float porcNoAtendidos;
    private float longitudMediaColaNave;


    public void calcularEstadisticas(VectorEstadoITV vectorEstadoITV){

        tiempoMedioOficina = vectorEstadoITV.getAcumuladorTiempoEsperaOficina() / vectorEstadoITV.getContadorVehiculosAtencionFinalizada();
        //Se debería hacer con los atendidos en la oficina, pero como al salir de la oficina
        //se termina la tención, entonces es lo mismo poner los clientes con at. finalizada

        tiempoMedioAtencionOficina = vectorEstadoITV.getAcumuladorTiempoAtencionOficina()/vectorEstadoITV
                                                                                .getContadorVehiculosAtencionFinalizada();

        tiempoMedioPermanenciaSistema = vectorEstadoITV.getAcumuladorTiempoAtencion()/vectorEstadoITV
                                                                                .getContadorVehiculosAtencionFinalizada();

        porcTiempoLibreCaseta = vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosCaseta()*100/ vectorEstadoITV.getReloj();
        porcTiempoLibreNave = vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosNave()*100/ vectorEstadoITV.getReloj();
        porcTiempoLibreOficina = vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosOficina()*100/ vectorEstadoITV.getReloj();

        tiempoMedioColaCaseta = vectorEstadoITV.getAcumuladorTiempoEsperaColaCaseta()/vectorEstadoITV
                                                                                .getContadorClientesAtendidosCaseta();
        tiempoMedioColaNave = vectorEstadoITV.getAcumuladorTiempoEsperaColaNave()/vectorEstadoITV
                                                                                    .getContadorClientesAtendidosNave();

        int cantTotalLlegadas = vectorEstadoITV.getContadorVehiculos()+ vectorEstadoITV.getContadorClientesNoAtendidos();
        //Se calcula sobre el total de llegadas, teniendo en cuenta atendidos y no atendidos
        porcAtencionFinalizada = ((float)vectorEstadoITV.getContadorVehiculosAtencionFinalizada()*100) /cantTotalLlegadas;

        porcNoAtendidos = ((float)vectorEstadoITV.getContadorClientesNoAtendidos()*100)/cantTotalLlegadas;

        longitudMediaColaNave = ((float)vectorEstadoITV.getAcumuladorLongitudColaNave())/vectorEstadoITV
                                                                                        .getContadorClientesAtendidosNave();

    }


}
