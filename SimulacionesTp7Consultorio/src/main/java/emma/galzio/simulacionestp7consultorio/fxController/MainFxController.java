package emma.galzio.simulacionestp7consultorio.fxController;

import emma.galzio.simulacionestp7consultorio.controller.SimulacionesTp7Controller;
import emma.galzio.simulacionestp7consultorio.controller.utils.ConstantesGenerador;
import emma.galzio.simulacionestp7consultorio.controller.utils.MetodoGeneradorRandom;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosConsultorio;
import emma.galzio.simulacionestp7consultorio.modelo.ParametrosGenerador;
import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class MainFxController implements Initializable {


    @FXML
    private TextField tf_tecnicoDesvEst;

    @FXML
    private TextField tf_mediaLlegadasConTurno;

    @FXML
    private TextField tf_cantidadFilas;

    @FXML
    private TextField tf_tecnicoMedia;

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
    private SimulacionesTp7Controller simulacionesTp7Controller;
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

        if(cb_metodoGenerador.getItems().isEmpty()) {
            cb_metodoGenerador.getItems().addAll(MetodoGeneradorRandom.getInstanceLenguaje(),
                    MetodoGeneradorRandom.getInstanceLineal(), MetodoGeneradorRandom.getInstanceMultiplicativo());
            cb_metodoGenerador.getSelectionModel().select(0);
        }

        if(parametrosTecnico == null && parametrosSecretaria == null &&
                parametrosLlegadasConTurno == null && parametrosLlegadasSinTurno == null) {
            metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();
            parametrosLlegadasConTurno = new ParametrosGenerador();
            parametrosLlegadasConTurno.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
            parametrosLlegadasConTurno.setPrecision(4);
            parametrosSecretaria = parametrosLlegadasConTurno;
            parametrosTecnico = parametrosLlegadasConTurno;
            parametrosLlegadasSinTurno = parametrosLlegadasConTurno;
        }
        this.setIntegerTextFieldsFilter();
        this.setTextFieldsFloatFilter();
    }

    public void seleccionarMetodoGenerador(MetodoGeneradorRandom metodoGeneradorRandom){
        MetodoGeneradorRandom metodoEnComboBox = null;
        for(MetodoGeneradorRandom metodo : cb_metodoGenerador.getItems()){
            if(metodo.getMetodo().equals(metodoGeneradorRandom.getMetodo())){
                metodoEnComboBox = metodo;
            }
        }
        if(metodoEnComboBox != null) cb_metodoGenerador.getSelectionModel().select(metodoEnComboBox);
    }

    private void setTextFieldsFloatFilter(){
        UnaryOperator<TextFormatter.Change> floatFilter = this.getFloatFilter();
        tf_mediaLlegadasConTurno.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_mediaLlegadasSinTurno.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_secreatariaA.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_secretariaB.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_tecnicoMedia.setTextFormatter(new TextFormatter<Float>(floatFilter));
        tf_tecnicoDesvEst.setTextFormatter(new TextFormatter<Float>(floatFilter));
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
    @SneakyThrows
    void generarSimulacion(ActionEvent event){

        try {
            ParametrosConsultorio parametrosConsultorio = cargarParametros();
            List<VectorEstadoClinica> resultadoSim = simulacionesTp7Controller.generarSimulacion(parametrosConsultorio);
            ResultadoSimulacionFxController resultadoFxController = stageManager.loadStageParentScene(sceneResultados.getURL());
            resultadoFxController.mostrarResultadosSimulacion(resultadoSim);

        }catch(NumberFormatException nfe){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Label label = new Label("Todos los campos son obligatorios y mayores a 0");
            label.setWrapText(true);
            alert.setTitle("Error");
            //alert.setContentText("Todos los campos son obligatorios mayores a 0");
            alert.getDialogPane().setContent(label);
            alert.showAndWait();
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label(e.getMessage());
            label.setWrapText(true);
            alert.getDialogPane().setContent(label);
            alert.showAndWait();
        }
    }

    private ParametrosConsultorio cargarParametros() {

        ParametrosConsultorio parametrosConsultorio = new ParametrosConsultorio();
        parametrosConsultorio.setParametrosSecretaria(parametrosSecretaria);
        parametrosConsultorio.setParametrosTecnico(parametrosTecnico);
        parametrosConsultorio.setParametrosLlegadaEstudio(parametrosLlegadasConTurno);
        parametrosConsultorio.setParametrosLlegadaTurno(parametrosLlegadasSinTurno);
        float mediaLlegadasTurno = Float.parseFloat(tf_mediaLlegadasSinTurno.getText().trim());
        float mediaLlegadasEstudio = Float.parseFloat(tf_mediaLlegadasConTurno.getText().trim());
        float secretariaUnifA = Float.parseFloat(tf_secreatariaA.getText().trim());
        float secretariaUnifB = Float.parseFloat(tf_secretariaB.getText().trim());
        float tecnicoUnifA = Float.parseFloat(tf_tecnicoMedia.getText().trim());
        float tecnicoUnifB = Float.parseFloat(tf_tecnicoDesvEst.getText().trim());
        int nroTurnosDiarios = Integer.parseInt(tf_cantTurnos.getText().trim());
        int cantDiasSim = Integer.parseInt(tf_cantDiasSim.getText().trim());
        int primeraFila = Integer.parseInt(tf_primeraFila.getText().trim());
        int cantFilas = Integer.parseInt(tf_cantidadFilas.getText().trim());

        parametrosConsultorio.setLambdaLlegadaTurno((float)truncar(1/mediaLlegadasTurno,4));
        parametrosConsultorio.setLambdaLlegadaEstudio((float)truncar(1/mediaLlegadasEstudio,4));
        parametrosConsultorio.setUnifASecretaria(secretariaUnifA);
        parametrosConsultorio.setUnifBSecretaria(secretariaUnifB);
        parametrosConsultorio.setMediaAtTecnico(tecnicoUnifA);
        parametrosConsultorio.setDesvEstTecnico(tecnicoUnifB);
        parametrosConsultorio.setTurnosDisponiblesDiario(nroTurnosDiarios);
        parametrosConsultorio.setDiasASimular(cantDiasSim);
        parametrosConsultorio.setPrimeraFila(primeraFila);
        parametrosConsultorio.setCantFilasMostrar(cantFilas);

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
    public double truncar(double f, float precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }
}