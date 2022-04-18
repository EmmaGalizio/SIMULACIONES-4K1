package k1.simulaciones.simulacionestp3.eventListener;

import javafx.stage.Stage;
import k1.simulaciones.simulacionestp3.Tp3SimulacionesFxApplication;
import k1.simulaciones.simulacionestp3.utils.StageManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import k1.simulaciones.simulacionestp3.Tp3SimulacionesFxApplication.StageReadyEvent;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/fxml/main.fxml")
    private Resource sceneResource;
    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event){

        try {
            Stage stage = event.getStage();
            StageManager stageManager = applicationContext.getBean(StageManager.class,stage,applicationContext);

            stageManager.loadStageParentScene(sceneResource.getURL(),400,400);
            stageManager.showStage();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
