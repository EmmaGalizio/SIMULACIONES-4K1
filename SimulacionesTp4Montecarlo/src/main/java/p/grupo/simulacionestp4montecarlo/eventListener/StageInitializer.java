package p.grupo.simulacionestp4montecarlo.eventListener;

import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import p.grupo.simulacionestp4montecarlo.Tp4SimulacionesFxApplication.StageReadyEvent;
import org.springframework.stereotype.Component;
import p.grupo.simulacionestp4montecarlo.Tp4SimulacionesFxApplication;
import p.grupo.simulacionestp4montecarlo.utils.StageManager;

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

            stageManager.loadStageParentScene(sceneResource.getURL(),500,700);
            stageManager.showStage();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
