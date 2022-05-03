package p.grupo.simulacionestp4montecarlo.fxController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import p.grupo.simulacionestp4montecarlo.modelo.montecarlo.VectorEstadoMontecarloPuerto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class ResultadoMuelleActualFxController implements Initializable {

    @FXML
    private TableView<VectorEstadoMontecarloPuerto> tv_SimUnMuelle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generarColumnasMontecarloUnMuelle(tv_SimUnMuelle);
    }

    public void mostrarResultados(List<VectorEstadoMontecarloPuerto> resultadoSimulacion){
        tv_SimUnMuelle.getItems().addAll(resultadoSimulacion);
        //Faltaría calcular los resultados y mostrarlos en algún lado
    }

    private void generarColumnasMontecarloUnMuelle(TableView<VectorEstadoMontecarloPuerto> tableView){

        tableView.getItems().removeAll(tableView.getItems());
        tableView.getColumns().removeAll(tableView.getColumns());
        TableColumn<VectorEstadoMontecarloPuerto,Long> diaColumna = new TableColumn<>();
        diaColumna.setCellValueFactory(new PropertyValueFactory<>("dia"));
        diaColumna.setText("Día");
        TableColumn<VectorEstadoMontecarloPuerto,Float> rndIngresosColumna = new TableColumn<>();
        rndIngresosColumna.setCellValueFactory(new PropertyValueFactory<>("randomIngresos"));
        rndIngresosColumna.setText("RND Ing.");
        TableColumn<VectorEstadoMontecarloPuerto,Integer> cantIngresosColumna = new TableColumn<>();
        cantIngresosColumna.setCellValueFactory(new PropertyValueFactory<>("cantIngresos"));
        cantIngresosColumna.setText("Cant. Ing.");
        TableColumn<VectorEstadoMontecarloPuerto,Integer> cantActualColumna = new TableColumn<>();
        cantActualColumna.setCellValueFactory(new PropertyValueFactory<>("cantActualMuelle"));
        cantActualColumna.setText("Cant. Act.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> rndDescargasColumna = new TableColumn<>();
        rndDescargasColumna.setCellValueFactory(new PropertyValueFactory<>("randomDescargas"));
        rndDescargasColumna.setText("RND Desc.");
        TableColumn<VectorEstadoMontecarloPuerto,Integer> cantDescargasColumna = new TableColumn<>();
        cantDescargasColumna.setCellValueFactory(new PropertyValueFactory<>("cantDescargas"));
        cantDescargasColumna.setText("Cant. Desc.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> costoDescargaColumna = new TableColumn<>();
        costoDescargaColumna.setCellValueFactory(new PropertyValueFactory<>("costoDescarga"));
        costoDescargaColumna.setText("Cos. Desc.");
        TableColumn<VectorEstadoMontecarloPuerto,Integer> cantPostergadasColumna = new TableColumn<>();
        cantPostergadasColumna.setCellValueFactory(new PropertyValueFactory<>("cantDescargasPostergadas"));
        cantPostergadasColumna.setText("Cant. Post.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> costoDemoraColumna = new TableColumn<>();
        costoDemoraColumna.setCellValueFactory(new PropertyValueFactory<>("costoDemora"));
        costoDemoraColumna.setText("Cos. Post.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> costoTotalDiaColumna = new TableColumn<>();
        costoTotalDiaColumna.setCellValueFactory(new PropertyValueFactory<>("costoTotalDia"));
        costoTotalDiaColumna.setText("Cos. Día.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> costoAcumColumna = new TableColumn<>();
        costoAcumColumna.setCellValueFactory(new PropertyValueFactory<>("costoAcumulado"));
        costoAcumColumna.setText("Cos. Acum.");
        TableColumn<VectorEstadoMontecarloPuerto,Float> costoDemoraAcumColumna = new TableColumn<>();
        costoDemoraAcumColumna.setCellValueFactory(new PropertyValueFactory<>("costoDemoraAcumulado"));
        costoDemoraAcumColumna.setText("Cos. Post. Ac.");
        TableColumn<VectorEstadoMontecarloPuerto,Long> diasDemoraAcumColumna = new TableColumn<>();
        diasDemoraAcumColumna.setCellValueFactory(new PropertyValueFactory<>("diasDemoraAcumulada"));
        diasDemoraAcumColumna.setText("Dias. Post.");
        TableColumn<VectorEstadoMontecarloPuerto,Integer> barcosPostAcumColumna = new TableColumn<>();
        barcosPostAcumColumna.setCellValueFactory(new PropertyValueFactory<>("barcosPostergadosAcum"));
        barcosPostAcumColumna.setText("Barcos Post. Ac.");
        tableView.getColumns().addAll(diaColumna,rndIngresosColumna,cantIngresosColumna,
                cantActualColumna,rndDescargasColumna,cantDescargasColumna,costoDescargaColumna,
                cantPostergadasColumna, costoDemoraColumna,costoTotalDiaColumna,costoAcumColumna,
                costoDemoraAcumColumna,diasDemoraAcumColumna,barcosPostAcumColumna);
        tableView.refresh();
    }
}