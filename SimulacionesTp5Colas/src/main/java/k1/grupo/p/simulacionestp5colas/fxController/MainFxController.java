package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import k1.grupo.p.simulacionestp5colas.controller.ControladorTp5Colas;
import k1.grupo.p.simulacionestp5colas.controller.utils.ConstantesGenerador;
import k1.grupo.p.simulacionestp5colas.controller.utils.EstadoItvSimulacion;
import k1.grupo.p.simulacionestp5colas.controller.utils.MetodoGeneradorRandom;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoActual;
import k1.grupo.p.simulacionestp5colas.modelo.ParametrosGenerador;
import k1.grupo.p.simulacionestp5colas.modelo.colas.ParametrosItv;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import k1.grupo.p.simulacionestp5colas.utils.StageManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


@Component
@Lazy
public class MainFxController implements Initializable{

    @Value("classpath:/fxml/resultadoSimItv.fxml")
    private Resource modalResultadoActual;
    @Value("classpath:/fxml/resultadoSimItvDosCasetas.fxml")
    private Resource modalResultadoDosCasetas;
    @Value("classpath:/fxml/resultadoSimTresOficinas.fxml")
    private Resource modalResultadoTresOficinas;

    @Autowired
    private ControladorTp5Colas controladorTp5Colas;

    @FXML
    private TextField tf_mostrarFilaDesde;

    @FXML
    private TextField tf_tasaAtCaseta;
    @FXML
    private Button btn_simularItv;
    @FXML
    private TextField tf_tasaAtOficina;
    @FXML
    private TextField tf_mediaIngresos;
    @FXML
    private TextField tf_tasaAtNave;
    @FXML
    private TextField tf_tiempoSimulacion;
    @FXML
    private TextField tf_cantFilasMostrar;
    @FXML
    private ComboBox<EstadoItvSimulacion> cb_seleccionEstadoSim;

    private EstadoItvSimulacion estadoItvSeleccionado;
    private Resource modalResultadoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*UnaryOperator<TextFormatter.Change> floatFilter = t -> {
            String newText = t.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return t;
            }
            return null;
        };*/
        UnaryOperator<TextFormatter.Change> floatFilter = t -> {
            if (t.isReplaced())
                if(t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
        setTfTextFormatter(tf_mediaIngresos,floatFilter);
        setTfTextFormatter(tf_tasaAtCaseta,floatFilter);
        setTfTextFormatter(tf_tasaAtNave,floatFilter);
        setTfTextFormatter(tf_tasaAtOficina,floatFilter);
        setTfTextFormatter(tf_tiempoSimulacion,floatFilter);
        setTfTextFormatter(tf_cantFilasMostrar,floatFilter);
        setTfTextFormatter(tf_mostrarFilaDesde,floatFilter);

        cb_seleccionEstadoSim.getItems().addAll(EstadoItvSimulacion.getInstanciaActual()
                ,EstadoItvSimulacion.getInstanciaDosCasetas()
                ,EstadoItvSimulacion.getInstanciaTresOficinas());
        cb_seleccionEstadoSim.getSelectionModel().select(0);
        estadoItvSeleccionado = cb_seleccionEstadoSim.getSelectionModel().getSelectedItem();
    }

    private void setTfTextFormatter(TextField textField, UnaryOperator<TextFormatter.Change> floatFilter){
        textField.setTextFormatter(new TextFormatter<Float>(floatFilter));

        VectorEstadoITV vectorEstadoITV = new VectorEstadoITV();
        VectorEstadoDtoActual vectorEstadoDtoActual = new VectorEstadoDtoActual();

        /*
        //revisar set de las colas

        vectorEstadoDtoActual.setColaCaseta(vectorEstadoITV.getColaCaseta().getSize()); // revisar
        vectorEstadoDtoActual.setColaNave(vectorEstadoITV.getColaNave().getSize()); //revisar
        vectorEstadoDtoActual.setColaOficina(vectorEstadoITV.getColaOficina().getSize()); //revisar

        //set primitivos

        vectorEstadoDtoActual.setNombreEvento(vectorEstadoITV.getNombreEvento());
        vectorEstadoDtoActual.setReloj(vectorEstadoITV.getReloj());
        vectorEstadoDtoActual.setContadorVehiculos(vectorEstadoITV.getContadorVehiculos());
        vectorEstadoDtoActual.setContadorClientesNoAtendidos(vectorEstadoITV.getContadorClientesNoAtendidos());
        vectorEstadoDtoActual.setContadorVehiculosAtencionFinalizada(vectorEstadoITV.getContadorVehiculosAtencionFinalizada());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaColaCaseta(vectorEstadoITV.getAcumuladorTiempoEsperaColaCaseta());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaCaseta(vectorEstadoITV.getAcumuladorTiempoEsperaCaseta());
        vectorEstadoDtoActual.setAcumuladorTiempoAtencionCaseta(vectorEstadoITV.getAcumuladorTiempoEsperaCaseta());
        vectorEstadoDtoActual.setContadorClientesAtendidosCaseta(vectorEstadoITV.getContadorClientesAtendidosCaseta());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaColaNave(vectorEstadoITV.getAcumuladorTiempoEsperaColaNave());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaNave(vectorEstadoITV.getAcumuladorTiempoEsperaNave());
        vectorEstadoDtoActual.setAcumuladorTiempoAtencionNave(vectorEstadoITV.getAcumuladorTiempoAtencionNave());
        vectorEstadoDtoActual.setContadorClientesAtendidosNave(vectorEstadoITV.getContadorClientesAtendidosNave());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaColaOficina(vectorEstadoITV.getAcumuladorTiempoEsperaColaOficina());
        vectorEstadoDtoActual.setAcumuladorTiempoEsperaOficina(vectorEstadoITV.getAcumuladorTiempoEsperaOficina());
        vectorEstadoDtoActual.setAcumuladorTiempoAtencionOficina(vectorEstadoITV.getAcumuladorTiempoAtencionOficina());
        vectorEstadoDtoActual.setAcumuladorTiempoAtencion(vectorEstadoITV.getAcumuladorTiempoAtencion());

        vectorEstadoDtoActual.setAcumuladorTotalEsperaCola(vectorEstadoITV.getAcumuladorTotalEsperaCola());
        vectorEstadoDtoActual.setAcumuladorTiempoLibreEmpleadosCaseta(vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosCaseta());
        vectorEstadoDtoActual.setAcumuladorTiempoLibreEmpleadosNave(vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosNave());
        vectorEstadoDtoActual.setAcumuladorTiempoLibreEmpleadosOficina(vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosOficina());
        vectorEstadoDtoActual.setAcumuladorLongitudColaNave(vectorEstadoITV.getAcumuladorLongitudColaNave());

        //set eventos
        vectorEstadoDtoActual.setFinInspeccion1(vectorEstadoITV.getFinInspeccion()[0]);
        vectorEstadoDtoActual.setFinInspeccion2(vectorEstadoITV.getFinInspeccion()[1]);

        vectorEstadoDtoActual.setFinAtencionOficina1(vectorEstadoITV.getFinAtencionOficina()[0]);
        vectorEstadoDtoActual.setFinAtencionOficina2(vectorEstadoITV.getFinAtencionOficina()[1]);

        vectorEstadoDtoActual.setEventoLlegadaCliente(vectorEstadoITV.getProximaLlegadaCliente()); //revisar
        vectorEstadoDtoActual.setFinAtencionCaseta(vectorEstadoITV.getFinAtencionCaseta()[0]); //revisar
        vectorEstadoDtoActual.setFinSimulacion(vectorEstadoITV.getFinSimulacion()); //revisar

        //set empleados

        vectorEstadoDtoActual.setEmpleadoCaseta(vectorEstadoITV.getEmpleadosCaseta().get(0));

        vectorEstadoDtoActual.setInspector1(vectorEstadoITV.getEmpleadosNave().get(0));
        vectorEstadoDtoActual.setInspector2(vectorEstadoITV.getEmpleadosNave().get(1));

        vectorEstadoDtoActual.setOficinista1(vectorEstadoITV.getEmpleadosOficina().get(0));
        vectorEstadoDtoActual.setOficinista2(vectorEstadoITV.getEmpleadosOficina().get(1));

        */



    }


    @FXML
    void generarSimulacionItv(ActionEvent event) {

        ParametrosItv parametrosItv = new ParametrosItv();
        obtenerParametrosItv(parametrosItv);
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador();
        parametrosGenerador.setMetodoGeneradorRandom(ConstantesGenerador.LENGUAJE);
        parametrosGenerador.setPresicion(4);
        parametrosGenerador.setN(1);

        List<VectorEstadoITV> simulacion = controladorTp5Colas
                                               .generarSimulacion(parametrosItv,parametrosGenerador);

        Stage modalStage = new Stage();


    }

    private void obtenerParametrosItv(ParametrosItv parametrosItv){

        try{
            float lambdaExpLlegadas = Float.parseFloat(tf_mediaIngresos.getText().trim());
            //Media de clientes por minuto. Al invertirse de nuevo en el generador pasa a ser
            //Media de tiempo por cliente
            lambdaExpLlegadas = lambdaExpLlegadas/60;
            float lambdaExpAtCaseta = Float.parseFloat(tf_tasaAtCaseta.getText().trim());
            lambdaExpAtCaseta = 1/lambdaExpAtCaseta; //Tiene que ser así porque en el generador se usa lambda
            //se invierte de nuevo

            float lambdaExpAtNave =Float.parseFloat(tf_tasaAtNave.getText().trim());
            //Igual que las llegadas
            lambdaExpAtNave /=60;

            float lambdaExpAtOficina = Float.parseFloat(tf_tasaAtOficina.getText().trim());
            lambdaExpAtOficina = 1/lambdaExpAtOficina;
            parametrosItv.setLambdaExpLlegadasClientes(lambdaExpLlegadas);
            parametrosItv.setLambdaExpServCaseta(lambdaExpAtCaseta);
            parametrosItv.setLambdaExpServicioNave(lambdaExpAtNave);
            parametrosItv.setLambdaExpServicioOficina(lambdaExpAtOficina);

            parametrosItv.setMaxMinutosSimular(Float.parseFloat(tf_tiempoSimulacion.getText().trim()));
            parametrosItv.setCantFilasMostrar(Integer.parseInt(tf_cantFilasMostrar.getText().trim()));
            parametrosItv.setMostrarFilaDesde(Integer.parseInt(tf_mostrarFilaDesde.getText().trim()));
            parametrosItv.validar();

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos incorrectos");
            alert.setContentText("Algunos de los datos ingresados no poseen el formato correcto");
            alert.showAndWait();

        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos incorrectos");
            alert.setHeaderText("Algunos de los valores ingresados son incorrectos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void seleccionarEstadoSimulacion(ActionEvent event){

        //Me parece que está de más
        estadoItvSeleccionado = cb_seleccionEstadoSim.getSelectionModel().getSelectedItem();
        switch (estadoItvSeleccionado.getPrompt()){
            case EstadoItvSimulacion.ACTUAL:
                modalResultadoActual = modalResultadoActual;
                break;
            case EstadoItvSimulacion.DOS_CASETAS:
                modalResultadoActual = modalResultadoDosCasetas;
                break;
            case EstadoItvSimulacion.TRES_OFIC:
                modalResultadoActual = modalResultadoTresOficinas;
                break;
        }

    }

}
