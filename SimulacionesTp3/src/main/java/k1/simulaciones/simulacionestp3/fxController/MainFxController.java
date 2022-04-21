package k1.simulaciones.simulacionestp3.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import k1.simulaciones.simulacionestp3.controller.ControladorTP3;
import k1.simulaciones.simulacionestp3.controller.utils.ConstantesCambioDistribucion;
import k1.simulaciones.simulacionestp3.controller.utils.DistribucionEsperadaChiCuadrado;
import k1.simulaciones.simulacionestp3.controller.utils.MetodoGeneradorRandom;
import k1.simulaciones.simulacionestp3.modelo.*;
import k1.simulaciones.simulacionestp3.utils.StageManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@Lazy
public class MainFxController implements Initializable {

    @FXML
    private BarChart<String, Integer> ch_histograma;

    @FXML
    private TableView<IntervaloKS> tv_pruebaKS;

    @FXML
    private ComboBox<MetodoGeneradorRandom> cb_metodoGenerador;

    @FXML
    private Button btn_cargarDatos;

    @FXML
    private TableView<Intervalo> tv_chiCuadrado;

    @FXML
    private ComboBox<DistribucionEsperadaChiCuadrado> cb_distribucionEsperada;

    @Value("classpath:/fxml/modalDistNormal.fxml")
    private Resource modalNormalResourse;
    @Value("classpath:/fxml/modalDistPoisson.fxml")
    private Resource modalPoissonResourse;
    @Value("classpath:/fxml/modalDistUniforme.fxml")
    private Resource modalUniformeResourse;
    @Value("classpath:/fxml/modalDistExpNegativa.fxml")
    private Resource modalExpNegResourse;

    private Resource modalAMostrar;
    private DistribucionEsperadaChiCuadrado distribucionSeleccionada;
    private MetodoGeneradorRandom metodoGeneradorSeleccionado;

    @Autowired
    private ControladorTP3 controladorTP3;
    @Autowired
    private StageManager stageManager;
    @Autowired
    private ApplicationContext applicationContext;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_metodoGenerador.getItems().addAll(MetodoGeneradorRandom.getInstanceLenguaje(),
                MetodoGeneradorRandom.getInstanceLineal(),MetodoGeneradorRandom.getInstanceMultiplicativo());
        cb_metodoGenerador.getSelectionModel().select(0);
        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();
        cb_distribucionEsperada.getItems().addAll(
                DistribucionEsperadaChiCuadrado.getInstanceUniforme(),
                DistribucionEsperadaChiCuadrado.getInstanceNormalBoxMuller(),
                DistribucionEsperadaChiCuadrado.getInstanceNormalConvolucion(),
                DistribucionEsperadaChiCuadrado.getInstanceExpNegativa(),
                DistribucionEsperadaChiCuadrado.getInstancePoisson());
        cb_distribucionEsperada.getSelectionModel().select(0);
        distribucionSeleccionada = cb_distribucionEsperada.getSelectionModel().getSelectedItem();
        modalAMostrar = modalUniformeResourse;
        generarColumnasChiCuadrado(tv_chiCuadrado);
        generarColumnasKS(tv_pruebaKS);

    }

    @FXML
    void seleccionMetodoGenerador(ActionEvent event) {
        //No hace falta !!!!!!!!!!!
        metodoGeneradorSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem();

    }

    @FXML
    void seleccionDistribucion(ActionEvent event) {

         distribucionSeleccionada = cb_distribucionEsperada.getSelectionModel().getSelectedItem();

        switch (distribucionSeleccionada.getId()){
            case ConstantesCambioDistribucion.NORMAL_BOXMULLER:
            case ConstantesCambioDistribucion.NORMAL_CONVOLUCION:
                modalAMostrar = modalNormalResourse;
                break;
            case ConstantesCambioDistribucion.EXP_NEG:
                modalAMostrar = modalExpNegResourse;
                break;
            case ConstantesCambioDistribucion.POISSON:
                modalAMostrar = modalPoissonResourse;
                break;
            case ConstantesCambioDistribucion.UNIFORME:
                modalAMostrar = modalUniformeResourse;
                break;
        }


    }

    @FXML
    void mostrarModalCargaDatos(ActionEvent event) {
        //Es el actionevent del boton cargar datos, acá se lanza el modal,

        Stage modalStage = new Stage();
        //Hay que cargar la escena correspondiente
        //Obtener el controlador y pasarle este controlador, para que cuando
        //el usuario ingrese los datos y le de a aceptar el controlador del
        //modal setea los parametros en este, y este llama al controlador.

        setModalScene(modalStage);
        modalStage.setTitle("Parametros Generador Random");
        modalStage.initOwner(stageManager.getStage());
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.showAndWait();
    }

    public void generarPruebasBondadAjuste(ParametrosCambioDistribucion parametrosCambioDistribucion,
                                           ParametrosGenerador... parametrosGeneradors){

        for(ParametrosGenerador parametrosGenerador: parametrosGeneradors){
            parametrosGenerador.setMetodoGeneradorRandom(metodoGeneradorSeleccionado.getId());
        }
        ResultadoBondadAjuste resultadoBondadAjuste = controladorTP3
                .generarDistribucionFrecuencia(parametrosCambioDistribucion,
                                                distribucionSeleccionada,
                                                parametrosGeneradors);
        llenarTableChiCuadrado(resultadoBondadAjuste, tv_chiCuadrado);
        llenarHistograma("Intervalo","Frecuencia Obserbada", ch_histograma,resultadoBondadAjuste);
        llenarTablaKS(resultadoBondadAjuste,tv_pruebaKS);
        String resultadoChi = (resultadoBondadAjuste.getEstadisticoObsChiCuadrado()<= resultadoBondadAjuste.getEstatisticoEspChiCuadrado())
                            ?(resultadoBondadAjuste.getEstadisticoObsChiCuadrado())+"<="+ resultadoBondadAjuste.getEstatisticoEspChiCuadrado()+
                            " No se rechaza la hipotesis nula":
                (resultadoBondadAjuste.getEstadisticoObsChiCuadrado())+">="+ resultadoBondadAjuste.getEstatisticoEspChiCuadrado()+
                        " Se rechaza la hipotesis nula";
        String resultadoKS = (resultadoBondadAjuste.getEstadisticoObsKS()<= resultadoBondadAjuste.getEstatisticoEspKS())
                ?(resultadoBondadAjuste.getEstadisticoObsKS())+"<="+ resultadoBondadAjuste.getEstatisticoEspKS()+
                " No se rechaza la hipotesis nula":
                (resultadoBondadAjuste.getEstadisticoObsKS())+">="+ resultadoBondadAjuste.getEstatisticoEspKS()+
                        " Se rechaza la hipotesis nula";

        Alert resultadoAlert = new Alert(Alert.AlertType.INFORMATION);
        resultadoAlert.setTitle("Resultado Pruebas Bondad Ajuste");
        resultadoAlert.setContentText(resultadoChi+"\n"+resultadoKS);
        resultadoAlert.showAndWait();

    }
    private void llenarHistograma(String ejeX, String ejeY, BarChart<String,Integer> histograma, ResultadoBondadAjuste resultado){

        histograma.getData().removeAll(histograma.getData());
        histograma.setCategoryGap(0);
        histograma.setBarGap(0);
        histograma.getXAxis().setLabel(ejeX);
        histograma.getYAxis().setLabel(ejeY);
        XYChart.Series<String,Integer> serie = new XYChart.Series();
        serie.setName("Histograma (K-Inicial)");
        for(Intervalo intervalo : resultado.getDistFrecuencia()){
            serie.getData().add(new XYChart.Data<>("["+intervalo.getLimInf()+"-"+intervalo.getLimSup()+"]",
                    intervalo.getFrecObs()));
        }
        histograma.getData().add(serie);
    }

    private void llenarTableChiCuadrado(ResultadoBondadAjuste resultadoBondadAjuste, TableView<Intervalo> tableView){
        tableView.getItems().removeAll(tableView.getItems());
        tableView.getItems().addAll(resultadoBondadAjuste.getDistribucionChiCuadrado());
    }
    private void llenarTablaKS(ResultadoBondadAjuste resultadoBondadAjuste, TableView<IntervaloKS> tableView){
        tableView.getItems().removeAll(tableView.getItems());
        tableView.getItems().addAll(resultadoBondadAjuste.getDistribucionKS());
        tableView.refresh();
    }

    @SneakyThrows
    private void setModalScene(Stage modalStage){

        FXMLLoader fxmlLoader = new FXMLLoader(modalAMostrar.getURL());
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent parent = fxmlLoader.load();
        ITp3FxController tp3FxController = (ITp3FxController) fxmlLoader.getController();
        modalStage.setScene(new Scene(parent,500,450));
        tp3FxController.setMainFxController(this);
        tp3FxController.setSelfStage(modalStage);
    }

    private void generarColumnasChiCuadrado(TableView<Intervalo> tableView){

        tableView.getItems().removeAll(tableView.getItems());
        tableView.getColumns().removeAll(tableView.getColumns());
        TableColumn<Intervalo,Float> limInfColumna = new TableColumn<>();
        limInfColumna.setCellValueFactory(new PropertyValueFactory<>("limInf"));
        limInfColumna.setText("Lim. Inf.");
        TableColumn<Intervalo,Float> limSupColumna = new TableColumn<>();
        limSupColumna.setCellValueFactory(new PropertyValueFactory<>("limSup"));
        limSupColumna.setText("Lim. Sup.");
        TableColumn<Intervalo,Float> mcColumna = new TableColumn<>();
        mcColumna.setCellValueFactory(new PropertyValueFactory<>("marcaClase"));
        mcColumna.setText("MC");
        TableColumn<Intervalo,Integer> frecObsColumna = new TableColumn<>();
        frecObsColumna.setCellValueFactory(new PropertyValueFactory<>("frecObs"));
        frecObsColumna.setText("FO");
        TableColumn<Intervalo,Float> probObsColumna = new TableColumn<>();
        probObsColumna.setCellValueFactory(new PropertyValueFactory<>("probObs"));
        probObsColumna.setText("PO");
        TableColumn<Intervalo,Float> probEspColumna = new TableColumn<>();
        probEspColumna.setCellValueFactory(new PropertyValueFactory<>("probEsp"));
        probEspColumna.setText("PE");
        TableColumn<Intervalo,Float> frecEspColumna = new TableColumn<>();
        frecEspColumna.setCellValueFactory(new PropertyValueFactory<>("frecEsp"));
        frecEspColumna.setText("FE.");
        TableColumn<Intervalo,Float> estCalcColumna = new TableColumn<>();
        estCalcColumna.setCellValueFactory(new PropertyValueFactory<>("estadistico"));
        estCalcColumna.setText("C");
        TableColumn<Intervalo,Float> estAcumColumna = new TableColumn<>();
        estAcumColumna.setCellValueFactory(new PropertyValueFactory<>("estadisticoAcumulado"));
        estAcumColumna.setText("C(Ac)");

        tableView.getColumns().addAll(limInfColumna,limSupColumna,mcColumna,frecObsColumna,probObsColumna,
                probEspColumna,frecEspColumna,estCalcColumna,estAcumColumna);
        tableView.refresh();
    }

    private void generarColumnasKS(TableView<IntervaloKS> tableView){

        tableView.getItems().removeAll(tableView.getItems());
        tableView.getColumns().removeAll(tableView.getColumns());
        TableColumn<IntervaloKS,Float> limInfColumna = new TableColumn<>();
        limInfColumna.setCellValueFactory(new PropertyValueFactory<>("limInf"));
        limInfColumna.setText("Lim. Inf.");
        TableColumn<IntervaloKS,Float> limSupColumna = new TableColumn<>();
        limSupColumna.setCellValueFactory(new PropertyValueFactory<>("limSup"));
        limSupColumna.setText("Lim. Sup.");
        TableColumn<IntervaloKS,Float> mcColumna = new TableColumn<>();
        mcColumna.setCellValueFactory(new PropertyValueFactory<>("marcaClase"));
        mcColumna.setText("MC");
        TableColumn<IntervaloKS,Integer> frecObsColumna = new TableColumn<>();
        frecObsColumna.setCellValueFactory(new PropertyValueFactory<>("frecObs"));
        frecObsColumna.setText("FO");
        TableColumn<IntervaloKS,Float> probObsColumna = new TableColumn<>();
        probObsColumna.setCellValueFactory(new PropertyValueFactory<>("probObs"));
        probObsColumna.setText("PO");
        TableColumn<IntervaloKS,Float> probEspColumna = new TableColumn<>();
        probEspColumna.setCellValueFactory(new PropertyValueFactory<>("probEsp"));
        probEspColumna.setText("PE");
        TableColumn<IntervaloKS,Float> frecEspColumna = new TableColumn<>();
        frecEspColumna.setCellValueFactory(new PropertyValueFactory<>("frecEsp"));
        frecEspColumna.setText("FE.");
        TableColumn<IntervaloKS,Float> probEspAcumColumna = new TableColumn<>();
        probEspAcumColumna.setCellValueFactory(new PropertyValueFactory<>("probEspAC"));
        probEspAcumColumna.setText("PE(Ac)");
        TableColumn<IntervaloKS,Float> probObsAcumColumna = new TableColumn<>();
        probObsAcumColumna.setCellValueFactory(new PropertyValueFactory<>("probObsAC"));
        probObsAcumColumna.setText("PO(Ac)");
        TableColumn<IntervaloKS,Float> difAcumColumna = new TableColumn<>();
        difAcumColumna.setCellValueFactory(new PropertyValueFactory<>("difAbs"));
        difAcumColumna.setText("|PO(Ac)-PE(Ac)|");

        tableView.getColumns().addAll(limInfColumna,limSupColumna,mcColumna,frecObsColumna,probObsColumna,
                probEspColumna,frecEspColumna,probEspAcumColumna,probObsAcumColumna,difAcumColumna);
        tableView.refresh();
    }

}
