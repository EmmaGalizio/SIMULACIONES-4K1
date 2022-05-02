package p.grupo.simulacionestp4montecarlo.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;


@Component
@Lazy
public class MainFxController implements Initializable {


    @Value("classpath:/fxml/modalDistNormal.fxml")
    private Resource modalNormalResourse;
    @Value("classpath:/fxml/modalDistPoisson.fxml")
    private Resource modalPoissonResourse;
    @Value("classpath:/fxml/modalDistUniforme.fxml")
    private Resource modalUniformeResourse;
    @Value("classpath:/fxml/modalDistExpNegativa.fxml")
    private Resource modalExpNegResourse;

    private Resource modalAMostrar;

    @Autowired
    private ApplicationContext applicationContext;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}
