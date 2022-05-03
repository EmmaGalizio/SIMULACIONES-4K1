package p.grupo.simulacionestp4montecarlo.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosGenerador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class GeneradorLinealModalFxController implements Initializable,ITp4FxController {

    @Autowired
    private MainFxController mainFxController;
    @FXML
    private TextField tf_c_descargas;
    @FXML
    private TextField tf_k_descargas;
    @FXML
    private Button btn_aceptarParametros;
    @FXML
    private TextField tf_g_arribos;
    @FXML
    private TextField tf_g_descargas;
    @FXML
    private TextField tf_c_costoDesc;
    @FXML
    private TextField tf_x0_descargas;
    @FXML
    private TextField tf_g_costoDesc;
    @FXML
    private TextField tf_x0_arribos;
    @FXML
    private TextField tf_c_arribos;
    @FXML
    private TextField tf_k_arribos;
    @FXML
    private TextField tf_k_costoDesc;
    @FXML
    private TextField tf_x0_costoDesc;
    private Stage selfStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        setTextFieldFormatter(integerFilter, tf_k_arribos, tf_g_arribos, tf_x0_arribos, tf_k_descargas);
        setTextFieldFormatter(integerFilter, tf_g_descargas, tf_x0_descargas, tf_x0_costoDesc, tf_x0_costoDesc);
        tf_x0_costoDesc.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_c_arribos.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_c_descargas.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_c_costoDesc.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
    }
    private void setTextFieldFormatter(UnaryOperator<TextFormatter.Change> integerFilter, TextField tf_k_arribos, TextField tf_g_arribos, TextField tf_x0_arribos, TextField tf_k_descargas) {
        tf_k_arribos.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_g_arribos.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_x0_arribos.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_k_descargas.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
    }

    @FXML
    void aceptarParametros(ActionEvent event) {

        try{
            mainFxController.setParametrosGeneradorIngresos(parseParametrosGeneradorIngresos());
            mainFxController.setParametrosGeneradorDescargas(parseParametrosGeneradorDescargas());
            mainFxController.setParametrosGeneradorCostoDescarga(parseParametrosGeneradorCostoDescarga());
            selfStage.close();

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos Incompletos");
            alert.setContentText("Todos los campos deben estar completos con un número entero");
            alert.showAndWait();
        }

    }

    private ParametrosGenerador parseParametrosGeneradorIngresos() {
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(Integer.parseInt(tf_x0_arribos.getText().trim()));
        parametrosGenerador.setG(Integer.parseInt(tf_g_arribos.getText().trim()));
        parametrosGenerador.setK(Integer.parseInt(tf_k_arribos.getText().trim()));
        parametrosGenerador.setC(Integer.parseInt(tf_c_arribos.getText().trim()));
        return parametrosGenerador;
    }
    private ParametrosGenerador parseParametrosGeneradorDescargas() {
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(Integer.parseInt(tf_x0_descargas.getText().trim()));
        parametrosGenerador.setG(Integer.parseInt(tf_g_descargas.getText().trim()));
        parametrosGenerador.setK(Integer.parseInt(tf_k_descargas.getText().trim()));
        parametrosGenerador.setC(Integer.parseInt(tf_c_descargas.getText().trim()));
        return parametrosGenerador;
    }
    private ParametrosGenerador parseParametrosGeneradorCostoDescarga() {
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setX0(Integer.parseInt(tf_x0_costoDesc.getText().trim()));
        parametrosGenerador.setG(Integer.parseInt(tf_g_costoDesc.getText().trim()));
        parametrosGenerador.setK(Integer.parseInt(tf_k_costoDesc.getText().trim()));
        parametrosGenerador.setC(Integer.parseInt(tf_c_costoDesc.getText().trim()));
        return parametrosGenerador;
    }

    @Override
    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
    }
}
