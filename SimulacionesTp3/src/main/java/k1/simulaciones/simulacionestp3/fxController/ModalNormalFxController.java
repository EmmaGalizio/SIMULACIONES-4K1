package k1.simulaciones.simulacionestp3.fxController;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import k1.simulaciones.simulacionestp3.modelo.ParametrosCambioDistribucion;
import k1.simulaciones.simulacionestp3.modelo.ParametrosGenerador;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Component
@Lazy
public class ModalNormalFxController implements ITp3FxController{

    @FXML
    private TextField tf_x0_1;

    @FXML
    private TextField tf_desv_est;

    @FXML
    private TextField tf_intervalos;

    @FXML
    private TextField tf_x0_2;

    @FXML
    private TextField tf_k_1;

    @FXML
    private TextField tf_k_2;

    @FXML
    private Button btn_aceptar;

    @FXML
    private TextField tf_g_1;

    @FXML
    private TextField tf_g_2;

    @FXML
    private TextField tf_c_1;

    @FXML
    private TextField tf_c_2;

    @FXML
    private TextField tf_N;

    @FXML
    private TextField tf_media;

    @FXML
    private TextField tf_a_1;

    @FXML
    private TextField tf_a_2;

    @FXML
    private TextField tx_m_1;

    @FXML
    private TextField tx_m_2;

    private MainFxController mainFxController;
    private Stage selfStage;

    @FXML
    void aceptarParametros(ActionEvent event) {
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(Integer.parseInt(tf_x0_1.getText().trim()));
        parametrosGenerador.setG(Integer.parseInt(tf_g_1.getText().trim()));
        parametrosGenerador.setK(Integer.parseInt(tf_k_1.getText().trim()));
        parametrosGenerador.setC(Integer.parseInt(tf_c_1.getText().trim()));

        ParametrosGenerador parametrosGenerador2 = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(Integer.parseInt(tf_x0_2.getText().trim()));
        parametrosGenerador.setG(Integer.parseInt(tf_g_2.getText().trim()));
        parametrosGenerador.setK(Integer.parseInt(tf_k_2.getText().trim()));
        parametrosGenerador.setC(Integer.parseInt(tf_c_2.getText().trim()));

        ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
        parametrosCambioDistribucion.setMedia(Float.parseFloat(tf_media.getText().trim()));
        parametrosCambioDistribucion.setDesvEst(Float.parseFloat(tf_desv_est.getText().trim()));
        parametrosCambioDistribucion.setN(Integer.parseInt(tf_N.getText().trim()));
        parametrosCambioDistribucion.setKInicial(Integer.parseInt(tf_intervalos.getText().trim()));
        parametrosGenerador.setPresicion(4);
        parametrosGenerador2.setPresicion(4);
        parametrosCambioDistribucion.setPresicion(4);
        parametrosCambioDistribucion.setDEmpiricos(2);

        mainFxController.generarPruebasBondadAjuste(parametrosCambioDistribucion,parametrosGenerador,parametrosGenerador2);
        selfStage.close();

    }
    @Override
    public void setSelfStage(Stage stage) {
        selfStage = stage;

    }

    @Override
    public void setMainFxController(MainFxController mainFxController) {
        this.mainFxController = mainFxController;
    }
}

