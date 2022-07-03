package emma.galzio.simulacionestp7consultorio.fxController;

import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesGenerador;
import emma.galzio.simulacionestp7consultorio.controller.utils.MetodoGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosGenerador;
import emma.galzio.simulacionestp7consultorio.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class MainFxController implements Initializable {


    @FXML
    private TextField tf_tecnicoB;

    @FXML
    private TextField tf_mediaLlegadasConTurno;

    @FXML
    private TextField tf_cantidadFilas;

    @FXML
    private TextField tf_tecnicoA;

    @FXML
    private TextField tf_primeraFila;

    @FXML
    private TextField tf_mediaLlegadasSinTurno;

    @FXML
    private Button btn_generarSimulacion;

    @FXML
    private TextField tf_cantTurnos;
    @FXML
    private TextField tf_cantDiasSim;

    @FXML
    private ComboBox<MetodoGeneradorRandom> cb_metodoGenerador;

    @FXML
    private TextField tf_secretariaB;

    @FXML
    private TextField tf_secreatariaA;

    @Autowired
    private StageManager stageManager;
    @Value("${sim.tp7.scene.generadorLineal}")
    private Resource sceneGeneradorLineal;
    @Value("${sim.tp7.scene.generadorMultiplicativo}")
    private Resource sceneGeneradorMultiplicativo;
    @Value("${sim.tp7.scene.resultadoSimEstudioMedico}")
    private Resource sceneResultados;

    private Resource sceneGeneradorSeleccionada;

    private ParametrosGenerador parametrosLlegadasConTurno;
    private ParametrosGenerador parametrosLlegadasSinTurno;
    private ParametrosGenerador parametrosSecretaria;
    private ParametrosGenerador parametrosTecnico;
    private MetodoGeneradorRandom metodoGeneradorSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_metodoGenerador.getItems().addAll(MetodoGeneradorRandom.getInstanceLenguaje(),
                MetodoGeneradorRandom.getInstanceLineal(),MetodoGeneradorRandom.getInstanceMultiplicativo());
        cb_metodoGenerador.getSelectionModel().select(0);

        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();
        parametrosLlegadasConTurno = new ParametrosGenerador();
        parametrosLlegadasConTurno.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        parametrosLlegadasConTurno.setPrecision(4);
        parametrosSecretaria = parametrosLlegadasConTurno;
        parametrosTecnico = parametrosLlegadasConTurno;
        parametrosLlegadasSinTurno = parametrosLlegadasConTurno;

        this.setIntegerTextFieldsFilter();
        this.setTextFieldsFloatFilter();


    }

    private void setTextFieldsFloatFilter(){
        UnaryOperator<TextFormatter.Change> floatFilter = this.getFloatFilter();
        tf_mediaLlegadasConTurno.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_mediaLlegadasSinTurno.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_secreatariaA.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_secretariaB.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_tecnicoA.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_tecnicoB.setTextFormatter(new TextFormatter<Float>(floatFilter));
    }


    private UnaryOperator<TextFormatter.Change> getFloatFilter(){
        return t -> {
            if (t.isReplaced())
                if(t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
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
        tf_cantTurnos.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_cantidadFilas.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_primeraFila.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
        tf_cantDiasSim.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null, integerFilter));
    }

    @FXML
    void generarSimulacion(ActionEvent event){

        ParametrosConsultorio parametrosConsultorio = cargarParametros();

    }

    private ParametrosConsultorio cargarParametros() {

        ParametrosConsultorio parametrosConsultorio = new ParametrosConsultorio();
        parametrosConsultorio.setParametrosSecretaria(parametrosSecretaria);
        parametrosConsultorio.setParametrosTecnico(parametrosTecnico);
        parametrosConsultorio.setParametrosLlegadaEstudio(parametrosLlegadasConTurno);
        parametrosConsultorio.setParametrosLlegadaTurno(parametrosLlegadasSinTurno);

        //Falta cargar los campos de texto


        return parametrosConsultorio;
    }

    @SneakyThrows
    public void seleccionMetodoGenerador(ActionEvent event) {
        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();

        switch (metodoGeneradorSeleccionado.getId()){

            case ConstantesGenerador.LENGUAJE:
                //Todos referencian al mismo objeto porque da igual, no se usa
                parametrosSecretaria = new ParametrosGenerador();
                parametrosSecretaria.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
                parametrosSecretaria.setPrecision(4);
                parametrosLlegadasSinTurno = parametrosSecretaria;
                parametrosLlegadasConTurno = parametrosSecretaria;
                parametrosTecnico = parametrosSecretaria;
                sceneGeneradorSeleccionada = null;
                break;
            case ConstantesGenerador.LINEAL:
                sceneGeneradorSeleccionada = sceneGeneradorLineal;
                break;
            case ConstantesGenerador.MULTIPLICATIVO:
                sceneGeneradorSeleccionada = sceneGeneradorMultiplicativo;
                break;
        }
        if(sceneGeneradorSeleccionada != null){
            stageManager.loadStageParentScene(sceneGeneradorSeleccionada.getURL());
        }

    }

    public void seleccionarGeneradorLenguaje(){
        cb_metodoGenerador.getSelectionModel().select(MetodoGeneradorRandom.getInstanceLenguaje());
        parametrosSecretaria = new ParametrosGenerador();
        parametrosSecretaria.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        parametrosSecretaria.setPrecision(4);
        parametrosLlegadasSinTurno = parametrosSecretaria;
        parametrosLlegadasConTurno = parametrosSecretaria;
        parametrosTecnico = parametrosSecretaria;
        sceneGeneradorSeleccionada = null;
    }

    public ParametrosGenerador getParametrosLlegadasConTurno() {
        return parametrosLlegadasConTurno;
    }

    public void setParametrosLlegadasConTurno(ParametrosGenerador parametrosLlegadasConTurno) {
        this.parametrosLlegadasConTurno = parametrosLlegadasConTurno;
    }

    public ParametrosGenerador getParametrosLlegadasSinTurno() {
        return parametrosLlegadasSinTurno;
    }

    public void setParametrosLlegadasSinTurno(ParametrosGenerador parametrosLlegadasSinTurno) {
        this.parametrosLlegadasSinTurno = parametrosLlegadasSinTurno;
    }

    public ParametrosGenerador getParametrosSecretaria() {
        return parametrosSecretaria;
    }

    public void setParametrosSecretaria(ParametrosGenerador parametrosSecretaria) {
        this.parametrosSecretaria = parametrosSecretaria;
    }

    public ParametrosGenerador getParametrosTecnico() {
        return parametrosTecnico;
    }

    public void setParametrosTecnico(ParametrosGenerador parametrosTecnico) {
        this.parametrosTecnico = parametrosTecnico;
    }
}