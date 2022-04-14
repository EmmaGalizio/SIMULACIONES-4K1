package emma.galzio.simulaciones_tp1_javafx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javafx.application.Application;

@SpringBootApplication
public class SimulacionesTp1JavafxApplication {

    public static void main(String[] args) {
        Application.launch(GeneradorRandomApplication.class,args);
    }

}
