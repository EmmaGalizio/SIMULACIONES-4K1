package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import k1.grupo.p.simulacionestp5colas.controller.utils.MetodoGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.utils.StageManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void seleccionMetodoGenerador(ActionEvent event) {

    }



    @FXML
    void generarSimulacionUnMuelle(ActionEvent event) {

    }

    @FXML
    void generarSimulacionDosMuelles(ActionEvent event) {

    }

}
