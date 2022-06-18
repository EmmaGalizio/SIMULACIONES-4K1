package p.grupo.k1.simulacionestp6;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class Tp6SimulacionesFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        applicationContext = new SpringApplicationBuilder(SimulacionesTp6ModContinuosApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
