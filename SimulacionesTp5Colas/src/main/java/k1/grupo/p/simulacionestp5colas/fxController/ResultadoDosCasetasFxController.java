package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoActual;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoDosCasetas;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class ResultadoDosCasetasFxController implements IResultadoSImulacion {
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
    private TableView<VectorEstadoDtoDosCasetas> tv_SimItv;

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
        List<VectorEstadoDtoDosCasetas> resultadoActual = this.mapVectorEstado(resultadoSimulacion);
    }
    private List<VectorEstadoDtoDosCasetas> mapVectorEstado(List<VectorEstadoITV> resultado) {
        List<VectorEstadoDtoDosCasetas> resultadoActual = new ArrayList<>();
        for (VectorEstadoITV vector : resultado) {
            VectorEstadoDtoDosCasetas vectorActual = new VectorEstadoDtoDosCasetas();
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
            vectorActual.setFinInspeccion1(vector.getFinInspeccion()[0]);
            vectorActual.setFinInspeccion2(vector.getFinInspeccion()[1]);

            vectorActual.setFinAtencionOficina1(vector.getFinAtencionOficina()[0]);
            vectorActual.setFinAtencionOficina2(vector.getFinAtencionOficina()[1]);

            vectorActual.setFinAtencionCaseta1(vector.getFinAtencionCaseta()[0]); //revisar
            vectorActual.setFinAtencionCaseta2(vector.getFinAtencionCaseta()[1]);

            vectorActual.setEventoLlegadaCliente(vector.getProximaLlegadaCliente()); //revisar
            vectorActual.setFinSimulacion(vector.getFinSimulacion()); //revisar

            //set empleados

            vectorActual.setEmpleadoCaseta1(vector.getEmpleadosCaseta().get(0));
            vectorActual.setEmpleadoCaseta2(vector.getEmpleadosCaseta().get(1));

            vectorActual.setInspector1(vector.getEmpleadosNave().get(0));
            vectorActual.setInspector2(vector.getEmpleadosNave().get(1));

            vectorActual.setOficinista1(vector.getEmpleadosOficina().get(0));
            vectorActual.setOficinista2(vector.getEmpleadosOficina().get(1));

            resultadoActual.add(vectorActual);

        }
        return resultadoActual;
    }
}
