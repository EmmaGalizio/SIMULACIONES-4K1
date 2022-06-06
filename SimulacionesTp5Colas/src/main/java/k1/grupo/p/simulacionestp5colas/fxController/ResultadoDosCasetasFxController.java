package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoDosCasetas;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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

    }
}
