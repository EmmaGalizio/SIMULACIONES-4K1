package emma.galzio.simulaciones_tp1_javafx.fxController;

import emma.galzio.simulaciones_tp1_javafx.controller.ControladorTP1;
import emma.galzio.simulaciones_tp1_javafx.controller.utils.ConstantesGenerador;
import emma.galzio.simulaciones_tp1_javafx.controller.utils.DistribucionEsperadaChiCuadrado;
import emma.galzio.simulaciones_tp1_javafx.controller.utils.MetodoGeneradorRandom;
import emma.galzio.simulaciones_tp1_javafx.modelo.Intervalo;
import emma.galzio.simulaciones_tp1_javafx.modelo.ParametrosGenerador;
import emma.galzio.simulaciones_tp1_javafx.modelo.ResultadoBondadAjuste;
import emma.galzio.simulaciones_tp1_javafx.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class PruebaChiCuadradoController implements Initializable {
    @FXML
    private TextField tf_c;
    @FXML
    private TextField tf_a;
    @FXML
    private Button btn_generarDistribucion;
    @FXML
    private TextField tf_k;
    @FXML
    private Button btn_back;
    @FXML
    private TextField tf_g;
    @FXML
    private TextField tf_est_esp;
    @FXML
    private TextField tf_m;
    @FXML
    private TextField tf_n;
    @FXML
    private TextField tf_x0;
    @FXML
    private TextField tf_intervalos;
    @FXML
    private TableView<Intervalo> tv_dist_frecuencia;
    @FXML
    private ComboBox<MetodoGeneradorRandom> cb_metodoGenerador;
    @FXML
    private ComboBox<DistribucionEsperadaChiCuadrado> cb_distribucionComp;
    @FXML
    private BarChart<String, Integer> bc_histograma;

    private String metodoSeleccionado;
    private String distribucionComparacion;
    @Autowired
    private ControladorTP1 controladorTP1;
    @Autowired
    private StageManager stageManager;
    @Value("classpath:/fxml/main.fxml")
    private Resource mainSceneResource;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        formatearTextFileds();
        cb_metodoGenerador.getItems().addAll(MetodoGeneradorRandom.getInstanceLineal(),
                                                MetodoGeneradorRandom.getInstanceMultiplicativo(),
                                                MetodoGeneradorRandom.getInstanceLenguaje());
        cb_distribucionComp.getItems().add(DistribucionEsperadaChiCuadrado.getInstanceUniforme());
        cb_distribucionComp.getSelectionModel().selectFirst();
        cb_metodoGenerador.getSelectionModel().selectFirst();
        metodoSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem().getId();
        distribucionComparacion = cb_distribucionComp.getSelectionModel().getSelectedItem().getId();
        habilitarCamposLineal();
        generarColumnasChiCuadrado(tv_dist_frecuencia);
    }

    private void formatearTextFileds(){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tf_c.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_n.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_k.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_g.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_x0.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_intervalos.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),null,integerFilter));
        tf_a.setEditable(false);
        tf_m.setEditable(false);
    }

    @FXML
    void seleccionMetodoGenerador(ActionEvent event) {
        metodoSeleccionado = cb_metodoGenerador.getSelectionModel().getSelectedItem().getId();
        switch (cb_metodoGenerador.getSelectionModel().getSelectedItem().getId()){
            case ConstantesGenerador.LINEAL:

                habilitarCamposLineal();
                break;
            case ConstantesGenerador.MULTIPLICATIVO:
                habilitarCamposMultiplicativo();
                break;
            case ConstantesGenerador.LENGUAJE:
                habilitarCamposLenguaje();
                break;
        }

    }

    @FXML
    void generarDistribucionFrecuencia(ActionEvent event) {
        ParametrosGenerador parametrosGenerador = obtenerParametrosTf();
        int cantIntervalos = (!tf_intervalos.getText().isBlank()) ? Integer.parseInt(tf_intervalos.getText()):5;
        ResultadoBondadAjuste resultadoBondadAjuste = controladorTP1
                .generarPruebaChiCuadrado(parametrosGenerador, metodoSeleccionado,cantIntervalos,distribucionComparacion);
        llenarTablaResultado(resultadoBondadAjuste,tv_dist_frecuencia);
        llenarHistograma("Intervalo","Frecuencia Observada",bc_histograma,resultadoBondadAjuste);
        tf_est_esp.setText(String.valueOf(resultadoBondadAjuste.getEstatisticoEsp()));
        Alert resultadoAlert;
        if(resultadoBondadAjuste.getEstadisticoObs() > resultadoBondadAjuste.getEstatisticoEsp()){
            resultadoAlert = new Alert(Alert.AlertType.ERROR);
            resultadoAlert.setTitle("Resultado");
            resultadoAlert.setHeaderText("Se rechaza la hipotesis nula");
            resultadoAlert.setContentText(resultadoBondadAjuste.getEstadisticoObs()+">"+
                                                    resultadoBondadAjuste.getEstatisticoEsp());
        }else{
            resultadoAlert = new Alert(Alert.AlertType.INFORMATION);
            resultadoAlert.setTitle("Resultado");
            resultadoAlert.setHeaderText("No se rechaza la hipotesis nula!!");
            resultadoAlert.setContentText(resultadoBondadAjuste.getEstadisticoObs()+"<="+
                    resultadoBondadAjuste.getEstatisticoEsp());
        }
        resultadoAlert.showAndWait();


    }

    private void llenarTablaResultado(ResultadoBondadAjuste resultadoBondadAjuste, TableView<Intervalo> tableView){
        tableView.getItems().removeAll(tableView.getItems());
        tableView.getItems().addAll(resultadoBondadAjuste.getDistFrecuencia());
    }
    private void llenarHistograma(String ejeX, String ejeY, BarChart<String,Integer> histograma, ResultadoBondadAjuste resultado){

        histograma.getData().removeAll(histograma.getData());
        histograma.setCategoryGap(0);
        histograma.setBarGap(0);
        histograma.getXAxis().setLabel(ejeX);
        histograma.getYAxis().setLabel(ejeY);

        XYChart.Series<String,Integer> serie = new XYChart.Series();
        serie.setName("Histograma");
        for(Intervalo intervalo : resultado.getDistFrecuencia()){
            serie.getData().add(new XYChart.Data<>("["+intervalo.getLimInf()+"-"+intervalo.getLimSup()+"]",
                    intervalo.getFrecObs()));
        }
        histograma.getData().add(serie);


    }


    private ParametrosGenerador obtenerParametrosTf(){
        int n = !tf_n.getText().trim().isBlank() ? Integer.parseInt(tf_n.getText()): 0;
        int k = !tf_k.getText().trim().isBlank() ? Integer.parseInt(tf_k.getText()): 0;
        int g = !tf_g.getText().trim().isBlank() ? Integer.parseInt(tf_g.getText()): 0;
        int c = !tf_c.getText().trim().isBlank() ? Integer.parseInt(tf_c.getText()): 0;
        int x0 = !tf_x0.getText().trim().isBlank() ? Integer.parseInt(tf_x0.getText()): 0;
        return new ParametrosGenerador(n,x0,g,k,c,4);
    }

    @FXML
    void seleccionDistribucionComparacion(ActionEvent actionEvent){
        distribucionComparacion = cb_distribucionComp.getSelectionModel().getSelectedItem().getId();
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

    @SneakyThrows
    @FXML
    void btnBackMain(ActionEvent event) {
        stageManager.loadStageParentScene(mainSceneResource.getURL(),400,400);
    }

    private void desHabilitarCamposTexto(){
        tf_n.setDisable(true);
        tf_k.setDisable(true);
        tf_g.setDisable(true);
        tf_c.setDisable(true);
        tf_m.setDisable(true);
        tf_a.setDisable(true);
        tf_x0.setDisable(true);
    }
    private void habilitarCamposLineal(){
        tf_n.setDisable(false);
        tf_k.setDisable(false);
        tf_g.setDisable(false);
        tf_c.setDisable(false);
        tf_m.setDisable(false);
        tf_a.setDisable(false);
        tf_x0.setDisable(false);
    }
    private void habilitarCamposMultiplicativo(){
        tf_n.setDisable(false);
        tf_k.setDisable(false);
        tf_g.setDisable(false);
        tf_c.setDisable(true);
        tf_m.setDisable(false);
        tf_a.setDisable(false);
        tf_x0.setDisable(false);
    }
    private void habilitarCamposLenguaje(){
        tf_n.setDisable(false);
        tf_k.setDisable(true);
        tf_g.setDisable(true);
        tf_c.setDisable(true);
        tf_m.setDisable(true);
        tf_a.setDisable(true);
        tf_x0.setDisable(true);
    }
}
