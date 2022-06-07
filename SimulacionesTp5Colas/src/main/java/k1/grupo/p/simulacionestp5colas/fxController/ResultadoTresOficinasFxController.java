package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoActual;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoTresOficinas;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class ResultadoTresOficinasFxController implements IResultadoSImulacion{
    @FXML
    private TextField tf_tiempoMedioAtOficina;

    @FXML
    private TextField tf_tiempoMedioColaCaseta;

    @FXML
    private TextField tf_tiempoMedioLibreCaseta;

    @FXML
    private TextField tf_porcentajeAtFinalizada;

    @FXML
    private TextField tf_tiempoMedOficina;

    @FXML
    private TableView<VectorEstadoDtoTresOficinas> tv_SimItv;

    @FXML
    private TextField tf_tiempoMedioPermanencia;

    @FXML
    private TextField tf_tiempoMedioColaNave;

    @FXML
    private TextField tf_longitudMediaColaNave;

    @FXML
    private TextField tf_porcentajeNoAtendidos;

    @FXML
    private TextField tf_tiempoMedioLibreNave;


    @Override
    public void mostrarResultadosSimulacion(List<VectorEstadoITV> resultadoSimulacion) {
        List<VectorEstadoDtoTresOficinas> resultadoActual = this.mapVectorEstado(resultadoSimulacion);

    }

    private List<VectorEstadoDtoTresOficinas> mapVectorEstado(List<VectorEstadoITV> resultado) {
        List<VectorEstadoDtoTresOficinas> resultadoActual = new ArrayList<>();
        for (VectorEstadoITV vector : resultado){
            VectorEstadoDtoTresOficinas vectorActual = new VectorEstadoDtoTresOficinas();
            //
            //revisar set de las colas

            vectorActual.setColaCaseta(vector.getColaCaseta().size()); // revisar
            vectorActual.setColaNave(vector.getColaNave().size()); //revisar
            vectorActual.setColaOficina(vector.getColaOficina().size()); //revisar

            //set primitivos

            vectorActual.setNombreEvento(vector.getNombreEvento());
            vectorActual.setReloj(vector.getReloj());
            vectorActual.setContadorVehiculos(vector.getContadorVehiculos());
            vectorActual.setContadorClientesNoAtendidos(vector.getContadorClientesNoAtendidos());
            vectorActual.setContadorVehiculosAtencionFinalizada(vector.getContadorVehiculosAtencionFinalizada());
            vectorActual.setAcumuladorTiempoEsperaColaCaseta(vector.getAcumuladorTiempoEsperaColaCaseta());
            vectorActual.setAcumuladorTiempoEsperaCaseta(vector.getAcumuladorTiempoEsperaCaseta());
            vectorActual.setAcumuladorTiempoAtencionCaseta(vector.getAcumuladorTiempoEsperaCaseta());
            vectorActual.setContadorClientesAtendidosCaseta(vector.getContadorClientesAtendidosCaseta());
            vectorActual.setAcumuladorTiempoEsperaColaNave(vector.getAcumuladorTiempoEsperaColaNave());
            vectorActual.setAcumuladorTiempoEsperaNave(vector.getAcumuladorTiempoEsperaNave());
            vectorActual.setAcumuladorTiempoAtencionNave(vector.getAcumuladorTiempoAtencionNave());
            vectorActual.setContadorClientesAtendidosNave(vector.getContadorClientesAtendidosNave());
            vectorActual.setAcumuladorTiempoEsperaColaOficina(vector.getAcumuladorTiempoEsperaColaOficina());
            vectorActual.setAcumuladorTiempoEsperaOficina(vector.getAcumuladorTiempoEsperaOficina());
            vectorActual.setAcumuladorTiempoAtencionOficina(vector.getAcumuladorTiempoAtencionOficina());
            vectorActual.setAcumuladorTiempoAtencion(vector.getAcumuladorTiempoAtencion());

            vectorActual.setAcumuladorTotalEsperaCola(vector.getAcumuladorTotalEsperaCola());
            vectorActual.setAcumuladorTiempoLibreEmpleadosCaseta(vector.getAcumuladorTiempoLibreEmpleadosCaseta());
            vectorActual.setAcumuladorTiempoLibreEmpleadosNave(vector.getAcumuladorTiempoLibreEmpleadosNave());
            vectorActual.setAcumuladorTiempoLibreEmpleadosOficina(vector.getAcumuladorTiempoLibreEmpleadosOficina());
            vectorActual.setAcumuladorLongitudColaNave(vector.getAcumuladorLongitudColaNave());

            //set eventos
            vectorActual.setFinAtencionCaseta(vector.getFinAtencionCaseta()[0]);

            vectorActual.setFinInspeccion1(vector.getFinInspeccion()[0]);
            vectorActual.setFinInspeccion2(vector.getFinInspeccion()[1]);

            vectorActual.setFinAtencionOficina1(vector.getFinAtencionOficina()[0]);
            vectorActual.setFinAtencionOficina2(vector.getFinAtencionOficina()[1]);
            vectorActual.setFinAtencionOficina3(vector.getFinAtencionOficina()[2]);



            vectorActual.setEventoLlegadaCliente(vector.getProximaLlegadaCliente()); //revisar
            vectorActual.setFinSimulacion(vector.getFinSimulacion()); //revisar

            //set empleados

            vectorActual.setEmpleadoCaseta(vector.getEmpleadosCaseta().get(0));

            vectorActual.setInspector1(vector.getEmpleadosNave().get(0));
            vectorActual.setInspector2(vector.getEmpleadosNave().get(1));

            vectorActual.setOficinista1(vector.getEmpleadosOficina().get(0));
            vectorActual.setOficinista2(vector.getEmpleadosOficina().get(1));
            vectorActual.setOficinista3(vector.getEmpleadosOficina().get(3));

            resultadoActual.add(vectorActual);

        }return resultadoActual;


    }
}
