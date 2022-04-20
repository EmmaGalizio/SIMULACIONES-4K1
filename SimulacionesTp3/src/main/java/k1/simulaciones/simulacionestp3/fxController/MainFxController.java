package k1.simulaciones.simulacionestp3.fxController;

import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;


@Component
@Lazy
public class MainFxController {

    @FXML
    private BarChart<?, ?> ch_histograma;

    @FXML
    private TableView<?> tv_pruebaKS;

    @FXML
    private ComboBox<?> cb_metodoGenerador;

    @FXML
    private Button btn_cargarDatos;

    @FXML
    private TableView<?> tv_chiCuadrado;

    @FXML
    private ComboBox<?> cb_distribucionEsperada;

    @Value("classpath:/fxml/modalDistNormal.fxml")
    private Resource modalNormalResourse;
    @Value("classpath:/fxml/modalDistPoisson.fxml")
    private Resource modalPoissonResourse;
    @Value("classpath:/fxml/modalDistUniforme.fxml")
    private Resource modalUniformeResourse;
    @Value("classpath:/fxml/modalDistExpNegativa.fxml")
    private Resource modalExpNegResourse;


    @FXML
    void seleccionMetodoGenerador(ActionEvent event) {

    }

    @FXML
    void seleccionDistribucion(ActionEvent event) {

    }

    @FXML
    void mostrarModalCargaDatos(ActionEvent event) {

    }

}
