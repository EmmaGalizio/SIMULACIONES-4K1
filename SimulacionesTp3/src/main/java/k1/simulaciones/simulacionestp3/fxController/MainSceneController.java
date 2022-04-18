package k1.simulaciones.simulacionestp3.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import k1.simulaciones.simulacionestp3.utils.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Lazy
public class MainSceneController {

    @Autowired
    private StageManager stageManager;

    @FXML
    private Button btn_chiCuadrado;

    @FXML
    private Button btn_generarRandoms;
    @Value("classpath:/fxml/randoms-generados.fxml")
    private Resource generadorSceneResource;
    @Value("classpath:/fxml/prueba-chi-cuadrado.fxml")
    private Resource chiCuadradoSceneResource;

    @FXML
    void btnGenerarRandoms(ActionEvent event) throws IOException {
        stageManager.loadStageParentScene(generadorSceneResource.getURL(),1280,720);
    }

    @FXML
    void btnChiCuadrado(ActionEvent event) throws IOException{
        stageManager.loadStageParentScene(chiCuadradoSceneResource.getURL(),1280,720);
    }

}

