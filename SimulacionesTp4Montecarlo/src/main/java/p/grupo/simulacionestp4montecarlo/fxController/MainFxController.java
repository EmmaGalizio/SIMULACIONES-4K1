package p.grupo.simulacionestp4montecarlo.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import p.grupo.simulacionestp4montecarlo.controller.ControladorTp4MontecarloPuerto;
import p.grupo.simulacionestp4montecarlo.controller.utils.ConstantesGenerador;
import p.grupo.simulacionestp4montecarlo.controller.utils.MetodoGeneradorRandom;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosCambioDistribucion;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosGenerador;
import p.grupo.simulacionestp4montecarlo.modelo.ParametrosMontecarlo;
import p.grupo.simulacionestp4montecarlo.modelo.montecarlo.VectorEstadoMontecarloPuerto;
import p.grupo.simulacionestp4montecarlo.utils.StageManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


@Component
@Lazy
public class MainFxController implements Initializable {

    @FXML
    private TextField tf_nroDiasSimulacion;
    @FXML
    private TextField tf_devEstCostoDescargaDosMuelles;
    @FXML
    private TextField tf_devEstCostoDescarga;
    @FXML
    private TextField tf_cantIngresosDesde;
    @FXML
    private TextField tf_mediaCostoDescarga;
    @FXML
    private Button btn_generarDosMuelles;
    @FXML
    private TextField tf_cantIngresosHasta;
    @FXML
    private TextField tf_cantFilasMostrar;
    @FXML
    private TextField tf_filaDesdeDosMuelles;
    @FXML
    private Button btn_generarUnMuelle;
    @FXML
    private TextField tf_mediaCostoDescargaDosMuelles;
    @FXML
    private TextField tf_frecDescargaDosM;
    @FXML
    private TextField tf_nroDiasSimulacionDosMuelles;
    @FXML
    private TextField tf_cantFilasMostrarDosMuelles;
    @FXML
    private ComboBox<MetodoGeneradorRandom> cb_metodoGenerador;

    @FXML
    private TextField tf_cantDescargasDosM;

    @FXML
    private TextField tf_filaDesde;


    @Value("classpath:/fxml/modalGeneradorLineal.fxml")
    private Resource modalGeneradorLineal;
    @Value("classpath:/fxml/modalGeneradorMultiplicativo.fxml")
    private Resource modalGeneradorMultiplicativo;
    @Value("classpath:/fxml/modalResultadoUnMuelle.fxml")
    private Resource modalResultadoUnMuelle;

    private MetodoGeneradorRandom metodoGeneradorSeleccionado;
    private ParametrosGenerador parametrosGeneradorIngresos;
    private ParametrosGenerador parametrosGeneradorDescargas;
    private ParametrosGenerador parametrosGeneradorCostoDescarga;

    private Resource modalAMostrar;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private StageManager stageManager;
    @Autowired
    private ControladorTp4MontecarloPuerto controladorTp4;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_metodoGenerador.getItems().addAll(MetodoGeneradorRandom.getInstanceLenguaje(),
                MetodoGeneradorRandom.getInstanceLineal(),MetodoGeneradorRandom.getInstanceMultiplicativo());
        cb_metodoGenerador.getSelectionModel().select(0);
        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();
        parametrosGeneradorIngresos = new ParametrosGenerador();
        parametrosGeneradorIngresos.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        parametrosGeneradorIngresos.setPresicion(4);
        parametrosGeneradorDescargas = parametrosGeneradorIngresos;
        parametrosGeneradorCostoDescarga = parametrosGeneradorIngresos;
    }

    @FXML
    void seleccionMetodoGenerador(ActionEvent event) {

        Stage modalStage = new Stage();
        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();

        switch (metodoGeneradorSeleccionado.getId()){

            case ConstantesGenerador.LENGUAJE:
                //Todos referencian al mismo objeto porque da igual, no se usa
                parametrosGeneradorIngresos = new ParametrosGenerador();
                parametrosGeneradorIngresos.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
                parametrosGeneradorIngresos.setPresicion(4);
                parametrosGeneradorDescargas = parametrosGeneradorIngresos;
                parametrosGeneradorCostoDescarga = parametrosGeneradorIngresos;
                break;
            case ConstantesGenerador.LINEAL:
                modalAMostrar = modalGeneradorLineal;
                setModalScene(modalStage);
                mostrarModalGenerador(modalStage);
                break;
            case ConstantesGenerador.MULTIPLICATIVO:
                modalAMostrar = modalGeneradorMultiplicativo;
                setModalScene(modalStage);
                mostrarModalGenerador(modalStage);
                break;
        }
    }
    private void mostrarModalGenerador(Stage modalStage){
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setTitle("Parametros Generador Random");
        modalStage.initOwner(stageManager.getStage());
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.showAndWait();
    }
    @SneakyThrows
    private void setModalScene(Stage modalStage ){

        FXMLLoader fxmlLoader = new FXMLLoader(modalAMostrar.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent parent = fxmlLoader.load();
        ITp4FxController tp4FxController = (ITp4FxController) fxmlLoader.getController();
        modalStage.setScene(new Scene(parent,600,400));
        modalStage.centerOnScreen();
        modalStage.setMaximized(true);
        tp4FxController.setSelfStage(modalStage);
    }

    @FXML
    void generarSimulacionUnMuelle(ActionEvent event) {

        try{
            ParametrosCambioDistribucion parametrosCostoDescNormal = new ParametrosCambioDistribucion();
            parametrosCostoDescNormal.setPresicion(4);
            parametrosCostoDescNormal.setMedia(Integer.parseInt(tf_mediaCostoDescarga.getText().trim()));
            parametrosCostoDescNormal.setDesvEst(Integer.parseInt(tf_devEstCostoDescarga.getText().trim()));

            ParametrosMontecarlo parametrosMontecarlo = new ParametrosMontecarlo();
            parametrosMontecarlo.setN(Long.parseLong(tf_nroDiasSimulacion.getText().trim()));
            parametrosMontecarlo.setMostrarVectorDesde(Long.parseLong(tf_filaDesde.getText().trim()));
            parametrosMontecarlo.setCantFilasMostrar(Integer.parseInt(tf_cantFilasMostrar.getText().trim()));

            List<VectorEstadoMontecarloPuerto> tablaMontecarlo = controladorTp4
                    .generarSimulacionEstActual(parametrosCostoDescNormal,
                            parametrosGeneradorIngresos,parametrosGeneradorDescargas,
                            parametrosGeneradorCostoDescarga,parametrosMontecarlo);
            //List<VectorEstadoMontecarloPuerto> tablaMontecarlo = Collections.emptyList();
            mostrarModalResultadoSimulacion(tablaMontecarlo);

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos Incompletos");
            alert.setContentText("Todos los campos deben\nestar completos con un número entero");
            alert.showAndWait();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @SneakyThrows
    private void mostrarModalResultadoSimulacion(List<VectorEstadoMontecarloPuerto> resultadoSimulacion){
        Stage modalStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(modalResultadoUnMuelle.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent parent = fxmlLoader.load();
        ResultadoMuelleActualFxController resultadoUnMuelleController = fxmlLoader.getController();
        resultadoUnMuelleController.mostrarResultados(resultadoSimulacion);
        modalStage.setScene(new Scene(parent,1240,700));
        modalStage.initStyle(StageStyle.DECORATED);
        modalStage.setTitle("Resultados Montecarlo");
        modalStage.initOwner(stageManager.getStage());
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.showAndWait();
    }

    public void setParametrosGeneradorIngresos(ParametrosGenerador parametrosGeneradorIngresos) {
        this.parametrosGeneradorIngresos = parametrosGeneradorIngresos;
        this.parametrosGeneradorIngresos.setMetodoGeneradorRandom(metodoGeneradorSeleccionado.getId());
    }


    public void setParametrosGeneradorDescargas(ParametrosGenerador parametrosGeneradorDescargas) {
        this.parametrosGeneradorDescargas = parametrosGeneradorDescargas;
        this.parametrosGeneradorDescargas.setMetodoGeneradorRandom(metodoGeneradorSeleccionado.getId());
    }

    public void setParametrosGeneradorCostoDescarga(ParametrosGenerador parametrosGeneradorCostoDescarga) {
        this.parametrosGeneradorCostoDescarga = parametrosGeneradorCostoDescarga;
        this.parametrosGeneradorCostoDescarga.setMetodoGeneradorRandom(metodoGeneradorSeleccionado.getId());
    }

    @FXML
    void generarSimulacionDosMuelles(ActionEvent event) {
        try{
            ParametrosCambioDistribucion parametrosCambioDistribucion = new ParametrosCambioDistribucion();
            parametrosCambioDistribucion.setPresicion(4);
            parametrosCambioDistribucion.setMedia(Integer.parseInt(tf_mediaCostoDescarga.getText().trim()));
            parametrosCambioDistribucion.setDesvEst(Integer.parseInt(tf_devEstCostoDescarga.getText().trim()));
            parametrosCambioDistribucion.setUnifA(Integer.parseInt(tf_cantIngresosDesde.getText().trim()));
            parametrosCambioDistribucion.setUnifB(Integer.parseInt(tf_cantIngresosHasta.getText().trim()));
            int cantDescargas = Integer.parseInt(tf_cantDescargasDosM.getText().trim());
            int horas = Integer.parseInt(tf_frecDescargaDosM.getText().trim());
            int lambda = cantDescargas/horas;
            parametrosCambioDistribucion.setLambda(lambda);

            ParametrosMontecarlo parametrosMontecarlo = new ParametrosMontecarlo();
            parametrosMontecarlo.setN(Long.parseLong(tf_nroDiasSimulacion.getText().trim()));
            parametrosMontecarlo.setMostrarVectorDesde(Long.parseLong(tf_filaDesde.getText().trim()));
            parametrosMontecarlo.setCantFilasMostrar(Integer.parseInt(tf_cantFilasMostrar.getText().trim()));

            List<VectorEstadoMontecarloPuerto> tablaMontecarlo = controladorTp4
                    .generarSimulacionDosPuertos(parametrosCambioDistribucion,
                            parametrosGeneradorIngresos,parametrosGeneradorDescargas,
                            parametrosGeneradorCostoDescarga,parametrosMontecarlo);
            //List<VectorEstadoMontecarloPuerto> tablaMontecarlo = Collections.emptyList();
            mostrarModalResultadoSimulacion(tablaMontecarlo);

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos Incompletos");
            alert.setContentText("Todos los campos deben\nestar completos con un número entero");
            alert.showAndWait();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
