package emma.galzio.simulacionestp7consultorio.fxController;


import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesGenerador;
import emma.galzio.simulacionestp7consultorio.controller.utils.MetodoGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosGenerador;
import emma.galzio.simulacionestp7consultorio.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class GeneradorLinealFxController implements Initializable {

    @Autowired
    private MainFxController mainFxController;
    @Autowired
    private StageManager stageManager;

    @FXML
    private TextField tf_c_LlegadaConTurno;

    @FXML
    private TextField tf_c_AtSecretaria;

    @FXML
    private TextField tf_c_LlegadaSinTurno;

    @FXML
    private TextField tf_g_LlegadaSinTurno;

    @FXML
    private TextField tf_k_LlegadaConTurno;

    @FXML
    private Button btn_aceptarParametros;

    @FXML
    private TextField tf_x0_LlegadaConTurno;

    @FXML
    private TextField tf_k_LlegadaSinTurno;

    @FXML
    private TextField tf_k_AtTecnico;

    @FXML
    private TextField tf_x0_AtTecnico;

    @FXML
    private TextField tf_k_AtSecretaria;

    @FXML
    private TextField tf_g_LlegadaConTurno;

    @FXML
    private TextField tf_x0_AtSecretaria;

    @FXML
    private Button btn_cancelar;

    @FXML
    private TextField tf_g_AtTecnico;

    @FXML
    private TextField tf_c_AtTecnico;

    @FXML
    private TextField tf_g_AtSecretaria;

    @FXML
    private TextField tf_x0_LlegadaSinTurno;

    @Value("${sim.tp7.scene.Main}")
    private Resource mainSceneResource;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setIntegerTextFieldsFilter();

    }

    @FXML
    @SneakyThrows
    public void aceptarParametros(ActionEvent event) {

        ParametrosGenerador parametrosLlegadasTurno = new ParametrosGenerador();
        ParametrosGenerador parametrosLlegadasEstudio = new ParametrosGenerador();
        ParametrosGenerador parametrosSecretaria = new ParametrosGenerador();
        ParametrosGenerador parametrosTecnico = new ParametrosGenerador();
        parametrosTecnico.setPrecision(4);
        parametrosLlegadasEstudio.setPrecision(4);
        parametrosSecretaria.setPrecision(4);
        parametrosLlegadasTurno.setPrecision(4);
        parametrosSecretaria.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        parametrosTecnico.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        parametrosLlegadasEstudio.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);
        parametrosLlegadasTurno.setMetodoGeneradorRandom(ConstantesGenerador.LINEAL);

        try {
            //Seteo parametros secretaria
            parametrosSecretaria.setX0(Integer.parseInt(tf_x0_AtSecretaria.getText().trim()));
            parametrosSecretaria.setK(Integer.parseInt(tf_k_AtSecretaria.getText().trim()));
            parametrosSecretaria.setG(Integer.parseInt(tf_g_AtSecretaria.getText().trim()));
            parametrosSecretaria.setC(Integer.parseInt(tf_c_AtSecretaria.getText().trim()));
            //Parametros técnico
            parametrosTecnico.setX0(Integer.parseInt(tf_x0_AtTecnico.getText().trim()));
            parametrosTecnico.setK(Integer.parseInt(tf_k_AtTecnico.getText().trim()));
            parametrosTecnico.setG(Integer.parseInt(tf_g_AtTecnico.getText().trim()));
            parametrosTecnico.setC(Integer.parseInt(tf_c_AtTecnico.getText().trim()));
            //Parametros con turno
            parametrosLlegadasEstudio.setX0(Integer.parseInt(tf_x0_LlegadaConTurno.getText().trim()));
            parametrosLlegadasEstudio.setK(Integer.parseInt(tf_k_LlegadaConTurno.getText().trim()));
            parametrosLlegadasEstudio.setG(Integer.parseInt(tf_g_LlegadaConTurno.getText().trim()));
            parametrosLlegadasEstudio.setC(Integer.parseInt(tf_c_LlegadaConTurno.getText().trim()));
            //Parametros sin turno
            parametrosLlegadasTurno.setX0(Integer.parseInt(tf_x0_LlegadaSinTurno.getText().trim()));
            parametrosLlegadasTurno.setK(Integer.parseInt(tf_k_LlegadaSinTurno.getText().trim()));
            parametrosLlegadasTurno.setG(Integer.parseInt(tf_g_LlegadaSinTurno.getText().trim()));
            parametrosLlegadasTurno.setC(Integer.parseInt(tf_c_LlegadaSinTurno.getText().trim()));

            mainFxController.setParametrosSecretaria(parametrosSecretaria);
            mainFxController.setParametrosTecnico(parametrosTecnico);
            mainFxController.setParametrosLlegadasConTurno(parametrosLlegadasEstudio);
            mainFxController.setParametrosLlegadasSinTurno(parametrosLlegadasTurno);

            stageManager.loadStageParentScene(mainSceneResource.getURL());


        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Todos los campos son obligatorios y deben ser números enteros mayores a 0");
            alert.showAndWait();
        }
    }


    @FXML
    @SneakyThrows
    public void regresarMain(ActionEvent event) {
        mainFxController.seleccionarGeneradorLenguaje();
        stageManager.loadStageParentScene(mainSceneResource.getURL());
    }

    private UnaryOperator<TextFormatter.Change> getIntegerFilter(){
        return change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
    }
    private void setIntegerTextFieldsFilter(){
        UnaryOperator<TextFormatter.Change> integerFilter = this.getIntegerFilter();
        tf_x0_AtSecretaria.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_g_AtSecretaria.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_k_AtSecretaria.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_c_AtSecretaria.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_x0_AtTecnico.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_g_AtTecnico.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_k_AtTecnico.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_c_AtTecnico.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_x0_LlegadaConTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_g_LlegadaConTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_k_LlegadaConTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_c_LlegadaConTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_x0_LlegadaSinTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_g_LlegadaSinTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_k_LlegadaSinTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_c_LlegadaSinTurno.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
    }
}
