package k1.grupo.p.simulacionestp5colas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class Tp5SimulacionesFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        applicationContext = new SpringApplicationBuilder(SimulacionesTp5ColasApplication.class).run();
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
