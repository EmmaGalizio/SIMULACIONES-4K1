package emma.galzio.simulacionestp7consultorio.eventListener;

import emma.galzio.simulacionestp7consultorio.utils.StageManager;
import emma.galzio.simulacionestp7consultorio.Tp7SimulacionesFxApplication.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("${sim.tp7.scene.Main}")
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

            stageManager.loadStageParentScene(sceneResource.getURL());
            stageManager.showStage();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
