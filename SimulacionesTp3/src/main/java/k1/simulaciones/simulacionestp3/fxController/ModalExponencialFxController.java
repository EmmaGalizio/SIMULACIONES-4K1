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
public class ModalExponencialFxController implements ITp3FxController{

    @FXML
    private TextField tf_c;

    @FXML
    private TextField tf_a;

    @FXML
    private TextField tx_m;

    @FXML
    private TextField tf_x0;

    @FXML
    private TextField tf_N;

    @FXML
    private TextField tf_k;

    @FXML
    private TextField tf_intervalos;

    @FXML
    private TextField tf_lambda;

    @FXML
    private TextField tf_g;

    @FXML
    private Button btn_aceptar;
    private MainFxController mainFxController;
    private Stage selfStage;

    @FXML
    void aceptarParametros(ActionEvent event) {


            ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
            parametrosGenerador.setPresicion(4);
            parametrosGenerador.setX0(Integer.parseInt(tf_x0.getText().trim()));
            parametrosGenerador.setG(Integer.parseInt(tf_g.getText().trim()));
            parametrosGenerador.setK(Integer.parseInt(tf_k.getText().trim()));
            parametrosGenerador.setC(Integer.parseInt(tf_c.getText().trim()));
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setLambda(Float.parseFloat(tf_lambda.getText().trim()));
            parametrosCambioDistribucion.setN(Integer.parseInt(tf_N.getText().trim()));
            parametrosCambioDistribucion.setKInicial(Integer.parseInt(tf_intervalos.getText().trim()));
            parametrosGenerador.setPresicion(4);
            parametrosCambioDistribucion.setPresicion(4);
            parametrosCambioDistribucion.setDEmpiricos(1);

            mainFxController.generarPruebasBondadAjuste(parametrosCambioDistribucion,parametrosGenerador);
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
