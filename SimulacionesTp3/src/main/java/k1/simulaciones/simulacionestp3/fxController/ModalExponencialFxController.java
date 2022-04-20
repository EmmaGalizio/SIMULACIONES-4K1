package k1.simulaciones.simulacionestp3.fxController;

import javafx.event.ActionEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Component
@Lazy
public class ModalExponencialFxController {

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

    @FXML
    void aceptarParametros(ActionEvent event) {

    }

}
