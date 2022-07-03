package emma.galzio.simulacionestp7consultorio.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
public class ResultadoSimulacionFxController  implements Initializable {

    @Autowired
    private MainFxController mainFxController;
    @FXML
    private TextField tf_promedioPermanencia;

    @FXML
    private TableView<?> tv_resultadoSim;

    @FXML
    private TextField tf_porcOcupSecretaria;

    @FXML
    private TextField tf_TiempoColaTecnico;

    @FXML
    private Button btn_atras;

    @FXML
    private TextField tf_tiempoMedTecnico;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void regresarMain(ActionEvent event) {

    }

}
