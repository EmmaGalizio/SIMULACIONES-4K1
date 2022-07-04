package emma.galzio.simulacionestp7consultorio.fxController;

import emma.galzio.simulacionestp7consultorio.modelo.VectorEstadoClinica;
import emma.galzio.simulacionestp7consultorio.utils.CommonFunc;
import emma.galzio.simulacionestp7consultorio.utils.StageManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

@Component
@Lazy
public class ResultadoSimulacionFxController  implements Initializable {

    @Autowired
    private MainFxController mainFxController;
    @FXML
    private TextField tf_promedioPermanencia;

    @FXML
    private TableView<VectorEstadoClinica> tv_resultadoSim;

    @FXML
    private TextField tf_porcOcupSecretaria;

    @FXML
    private TextField tf_TiempoColaTecnico;

    @FXML
    private Button btn_atras;

    @FXML
    private TextField tf_tiempoMedTecnico;

    @Value("${sim.tp7.scene.Main}")
    private Resource mainSceneResoure;
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void mostrarResultadosSimulacion(List<VectorEstadoClinica> resultadoSim){

        tv_resultadoSim.getColumns().clear();
        tv_resultadoSim.getItems().addAll(resultadoSim);
        tv_resultadoSim.refresh();
        this.generarColumnas();
        this.calcularEstadisticas(resultadoSim.get(resultadoSim.size()-1));
    }

    @FXML
    @SneakyThrows
    void regresarMain(ActionEvent event) {
        stageManager.loadStageParentScene(mainSceneResoure.getURL());
    }

    private void calcularEstadisticas(VectorEstadoClinica estadoClinica) {
        //Falta calcular estadísticas
        double mediaPermPacEstudio = estadoClinica.getAcumuladorPermanenciaConTurno() /
                                                    estadoClinica.getCantEstudiosFinalizados();
        //mediaPermPacEstudio = truncar(mediaPermPacEstudio,4);
        mediaPermPacEstudio = CommonFunc.round(mediaPermPacEstudio,4);

        double mediaTotalEsperaEstudio = estadoClinica.getAcumuladorTiempoTotalEsperaTecnico() /
                                            estadoClinica.getCantEstudiosFinalizados();
        //mediaTotalEsperaEstudio =  truncar(mediaTotalEsperaEstudio,4);
        mediaTotalEsperaEstudio =  CommonFunc.round(mediaTotalEsperaEstudio,4);

        double mediaEsperaColaEstudio = estadoClinica.getAcumuladorTiempoColaTecnico() /
                                        estadoClinica.getCantEstudiosFinalizados();
        //mediaEsperaColaEstudio =  truncar(mediaEsperaColaEstudio,4);
        mediaEsperaColaEstudio =  CommonFunc.round(mediaEsperaColaEstudio,4);

        double porcOcupSecretaria = ((estadoClinica.getAcumuladorTiempoLibreSecretaria()* 100) /
                                    estadoClinica.getAcumuladorTiempoJornadasLaborales());
        //porcOcupSecretaria = truncar(porcOcupSecretaria, 4);
        porcOcupSecretaria = 100.0f - porcOcupSecretaria;
        //porcOcupSecretaria =  truncar(porcOcupSecretaria, 4);
        porcOcupSecretaria = CommonFunc.round(porcOcupSecretaria, 4);

        tf_porcOcupSecretaria.setText(Double.toString(porcOcupSecretaria));
        tf_promedioPermanencia.setText(Double.toString(mediaPermPacEstudio));
        tf_TiempoColaTecnico.setText(Double.toString(mediaEsperaColaEstudio));
        tf_tiempoMedTecnico.setText(Double.toString(mediaTotalEsperaEstudio));
    }
    private void generarColumnas() {

        TableColumn<VectorEstadoClinica, String> nombreEvColumna = new TableColumn<>();
        nombreEvColumna.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));
        nombreEvColumna.setText("Nom.Ev.");

        TableColumn<VectorEstadoClinica, Double> relojColumna = new TableColumn<>();
        relojColumna.setCellValueFactory(new PropertyValueFactory<>("reloj"));
        relojColumna.setText("Reloj (min)");
        TableColumn<VectorEstadoClinica, Double> diaColumna = new TableColumn<>();
        diaColumna.setCellValueFactory(new PropertyValueFactory<>("dia"));
        diaColumna.setText("Día");
        TableColumn<VectorEstadoClinica, Double> minutoDiaColumna = new TableColumn<>();
        minutoDiaColumna.setCellValueFactory(new PropertyValueFactory<>("minutoDelDia"));
        minutoDiaColumna.setText("Minuto Dia");
        TableColumn<VectorEstadoClinica, Double> momentoInicioJornadaColumna = new TableColumn<>();
        momentoInicioJornadaColumna.setCellValueFactory(new PropertyValueFactory<>("momentoInicioJornada"));
        momentoInicioJornadaColumna.setText("Inicio Jornada (min)");

        //Llegadas sin turno
        TableColumn<VectorEstadoClinica,String> randomLlegadaPacTurnoColumna = new TableColumn<>();
        randomLlegadaPacTurnoColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteTurno()==null ? new SimpleStringProperty("") :
                                                        new SimpleStringProperty(Float.toString(cellData.getValue()
                                                                        .getLlegadaPacienteTurno().getRandomLlegadaPacienteTurno())));
        randomLlegadaPacTurnoColumna.setText("RND Llegada s/Turno");

        TableColumn<VectorEstadoClinica,String> tiempoLlegadaPacTurnoColumna = new TableColumn<>();
        tiempoLlegadaPacTurnoColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteTurno()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getLlegadaPacienteTurno().getTiempoHastaEvento())));
        tiempoLlegadaPacTurnoColumna.setText("Tiempo Llegada s/Turno");

        TableColumn<VectorEstadoClinica,String> momentoLlegadaPacTurnoColumna = new TableColumn<>();
        momentoLlegadaPacTurnoColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteTurno()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getLlegadaPacienteTurno().getMomentoEvento())));
        momentoLlegadaPacTurnoColumna.setText("Sig. Llegada s/Turno");

        //Llegadas Con turno
        TableColumn<VectorEstadoClinica,String> randomLlegadaPacEstudioColumna = new TableColumn<>();
        randomLlegadaPacEstudioColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Float.toString(cellData.getValue()
                        .getLlegadaPacienteEstudio().getRandomLlegadaPacienteEstudio())));
        randomLlegadaPacEstudioColumna.setText("RND Llegada c/Turno");

        TableColumn<VectorEstadoClinica,String> tiempoLlegadaPacEstudioColumna = new TableColumn<>();
        tiempoLlegadaPacEstudioColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getLlegadaPacienteEstudio().getTiempoHastaEvento())));
        tiempoLlegadaPacEstudioColumna.setText("Tiempo Llegada c/Turno");

        TableColumn<VectorEstadoClinica,String> momentoLlegadaPacEstudioColumna = new TableColumn<>();
        momentoLlegadaPacEstudioColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaPacienteEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getLlegadaPacienteEstudio().getMomentoEvento())));
        momentoLlegadaPacEstudioColumna.setText("Sig. Llegada c/Turno");

        //Fin Atencion Secretaria;
        TableColumn<VectorEstadoClinica,String> randomFinAtSecretariaColumna = new TableColumn<>();
        randomFinAtSecretariaColumna.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionSecretaria()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Float.toString(cellData.getValue()
                        .getFinAtencionSecretaria().getRandomAtencionSecretaria())));
        randomFinAtSecretariaColumna.setText("RND At. Sec");

        TableColumn<VectorEstadoClinica,String> tiempoFinAtSecretariaColumna = new TableColumn<>();
        tiempoFinAtSecretariaColumna.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionSecretaria()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getFinAtencionSecretaria().getTiempoHastaEvento())));
        tiempoFinAtSecretariaColumna.setText("Tiempo At. Sec");

        TableColumn<VectorEstadoClinica,String> momentoFinAtSecretariaColumna = new TableColumn<>();
        momentoFinAtSecretariaColumna.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionSecretaria()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getFinAtencionSecretaria().getMomentoEvento())));
        momentoFinAtSecretariaColumna.setText("Fin At. Sec.");

        //Fin Atencion Tecnico
        TableColumn<VectorEstadoClinica,String> randomFinAtTecnicoColumna = new TableColumn<>();
        randomFinAtTecnicoColumna.setCellValueFactory(cellData -> cellData.getValue().getFinEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Float.toString(cellData.getValue()
                        .getFinEstudio().getRandomFinEstudio())));
        randomFinAtTecnicoColumna.setText("RND At. Tec.");

        TableColumn<VectorEstadoClinica,String> tiempoFinAtTecnicoColumna = new TableColumn<>();
        tiempoFinAtTecnicoColumna.setCellValueFactory(cellData -> cellData.getValue().getFinEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getFinEstudio().getTiempoHastaEvento())));
        tiempoFinAtTecnicoColumna.setText("Tiempo At. Tec.");

        TableColumn<VectorEstadoClinica,String> momentoFinAtTecnicoColumna = new TableColumn<>();
        momentoFinAtTecnicoColumna.setCellValueFactory(cellData -> cellData.getValue().getFinEstudio()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getFinEstudio().getMomentoEvento())));
        momentoFinAtTecnicoColumna.setText("Fin At. Tec.");

        //Evento inicio jornada
        TableColumn<VectorEstadoClinica,String> eventoInicioJornadaColumna = new TableColumn<>();
        eventoInicioJornadaColumna.setCellValueFactory(cellData -> cellData.getValue().getInicioJornada()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getInicioJornada().getMomentoEvento())));
        eventoInicioJornadaColumna.setText("Ev. Inicio Jornada");
        TableColumn<VectorEstadoClinica,String> eventoFinJornadaColumna = new TableColumn<>();
        eventoFinJornadaColumna.setCellValueFactory(cellData -> cellData.getValue().getFinJornada()==null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Double.toString(cellData.getValue()
                        .getFinJornada().getMomentoEvento())));
        eventoFinJornadaColumna.setText("Ev. Fin Jornada");


        //Servidor: Secretaria
        TableColumn<VectorEstadoClinica,String> estadoSecretaria = new TableColumn<>();
        estadoSecretaria.setCellValueFactory(cellData -> cellData.getValue().getSecretaria()== null ? new SimpleStringProperty("") :
                new SimpleStringProperty(cellData.getValue().getSecretaria().getEstado().getEstado()));
        estadoSecretaria.setText("Estado Secretaria");
        TableColumn<VectorEstadoClinica,String> colaSecretariaColumna = new TableColumn<>();
        colaSecretariaColumna.setCellValueFactory(cellData -> cellData.getValue().getColaSecretaria()== null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Integer.toString(cellData.getValue().getColaSecretaria().size())));
        colaSecretariaColumna.setText("Cola Sec.");
        //TableColumn<VectorEstadoClinica,String> pacienteActualSecColumna = new TableColumn<>();
        //pacienteActualSecColumna.setCellValueFactory(cellData -> cellData.getValue().getSecretaria().estaLibre() ? new SimpleStringProperty("") :
        //        new SimpleStringProperty(cellData.getValue().getSecretaria().getPacienteActual().getIdentificadorPaciente()));
        //pacienteActualSecColumna.setText("Paciente Act.");

        //Servidor: Técnico

        TableColumn<VectorEstadoClinica,String> estadoTecnico = new TableColumn<>();
        estadoTecnico.setCellValueFactory(cellData -> cellData.getValue().getTecnico()== null ? new SimpleStringProperty("") :
                new SimpleStringProperty(cellData.getValue().getTecnico().getEstado().getEstado()));
        estadoTecnico.setText("Estado Tecnico");
        TableColumn<VectorEstadoClinica,String> colaTecnicoColumna = new TableColumn<>();
        colaTecnicoColumna.setCellValueFactory(cellData -> cellData.getValue().getColaTecnico()== null ? new SimpleStringProperty("") :
                new SimpleStringProperty(Integer.toString(cellData.getValue().getColaTecnico().size())));
        colaTecnicoColumna.setText("Cola Tec.");
        //TableColumn<VectorEstadoClinica,String> pacienteActualTecColumna = new TableColumn<>();
        //pacienteActualTecColumna.setCellValueFactory(cellData -> cellData.getValue().getTecnico().estaLibre() ? new SimpleStringProperty("") :
        //        new SimpleStringProperty(cellData.getValue().getTecnico().getPacienteActual().getIdentificadorPaciente()));
        //pacienteActualTecColumna.setText("Paciente Act.");

        //Acumuladores y contadores.

        TableColumn<VectorEstadoClinica, Integer> cantLlegadasTurnoColumna = new TableColumn<>();
        cantLlegadasTurnoColumna.setCellValueFactory(new PropertyValueFactory<>("cantLlegadasTurno"));
        cantLlegadasTurnoColumna.setText("Cant. Lleg. s/T");

        TableColumn<VectorEstadoClinica, Integer> cantLlegadasEstudioColumna = new TableColumn<>();
        cantLlegadasEstudioColumna.setCellValueFactory(new PropertyValueFactory<>("cantLlegadasEstudio"));
        cantLlegadasEstudioColumna.setText("Cant. Lleg. c/T");
        TableColumn<VectorEstadoClinica, Integer> cantLlegadasTurnoDiaActualColumna = new TableColumn<>();
        cantLlegadasTurnoDiaActualColumna.setCellValueFactory(new PropertyValueFactory<>("cantLlegadasTurnoDiaActual"));
        cantLlegadasTurnoDiaActualColumna.setText("Cant. Lleg. s/T. Act.");

        TableColumn<VectorEstadoClinica, Integer> cantLlegadasEstudioDiaActualColumna = new TableColumn<>();
        cantLlegadasEstudioDiaActualColumna.setCellValueFactory(new PropertyValueFactory<>("cantLlegadasEstudioDiaActual"));
        cantLlegadasEstudioDiaActualColumna.setText("Cant. Lleg. c/T. Act.");

        TableColumn<VectorEstadoClinica, Integer> cantAtFinColumna = new TableColumn<>();
        cantAtFinColumna.setCellValueFactory(new PropertyValueFactory<>("cantAtencionFinalizadas"));
        cantAtFinColumna.setText("Cant. At. Fin.");

        TableColumn<VectorEstadoClinica, Integer> cantEstudiosFinColumna = new TableColumn<>();
        cantEstudiosFinColumna.setCellValueFactory(new PropertyValueFactory<>("cantEstudiosFinalizados"));
        cantEstudiosFinColumna.setText("Cant. Est. Fin.");

        //Para calcular la tasa de ocupacion de la secretaria
        TableColumn<VectorEstadoClinica, Double> acTiempoJornadasColumna = new TableColumn<>();
        acTiempoJornadasColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoJornadasLaborales"));
        acTiempoJornadasColumna.setText("Ac. T. Jornadas");
        TableColumn<VectorEstadoClinica, Double> acTiempoLibSecColumna = new TableColumn<>();
        acTiempoLibSecColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoLibreSecretaria"));
        acTiempoLibSecColumna.setText("Ac. T. Lib. Sec.");

        TableColumn<VectorEstadoClinica, Double> acPermEstudioColumna = new TableColumn<>();
        acPermEstudioColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorPermanenciaConTurno"));
        acPermEstudioColumna.setText("Ac. T. Perm. Est.");

        TableColumn<VectorEstadoClinica, Double> acEsperaColaTecColumna = new TableColumn<>();
        acEsperaColaTecColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoColaTecnico"));
        acEsperaColaTecColumna.setText("Ac. T. Cola Tec.");

        TableColumn<VectorEstadoClinica, Double> acEsperaTecnico = new TableColumn<>();
        acEsperaTecnico.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoTotalEsperaTecnico"));
        acEsperaTecnico.setText("Ac. T. Esp. Tec.");

        tv_resultadoSim.getColumns().addAll(nombreEvColumna, relojColumna, diaColumna, minutoDiaColumna, momentoInicioJornadaColumna,
                randomLlegadaPacTurnoColumna, tiempoLlegadaPacTurnoColumna, momentoLlegadaPacTurnoColumna,
                randomLlegadaPacEstudioColumna, tiempoLlegadaPacEstudioColumna, momentoLlegadaPacEstudioColumna,
                randomFinAtSecretariaColumna, tiempoFinAtSecretariaColumna, momentoFinAtSecretariaColumna,
                randomFinAtTecnicoColumna, tiempoFinAtTecnicoColumna, momentoFinAtTecnicoColumna,
                eventoInicioJornadaColumna, eventoFinJornadaColumna, estadoSecretaria, colaSecretariaColumna,
                estadoTecnico, colaTecnicoColumna ,cantLlegadasTurnoColumna, cantLlegadasEstudioColumna,
                cantLlegadasTurnoDiaActualColumna, cantLlegadasEstudioDiaActualColumna, cantAtFinColumna, cantEstudiosFinColumna,
                acTiempoJornadasColumna,acTiempoLibSecColumna, acPermEstudioColumna, acEsperaColaTecColumna, acEsperaTecnico);
        tv_resultadoSim.refresh();
    }

    private double truncar(double f, int precision){
        double multiplicador = Math.pow(10, precision);
        return Math.round(f*multiplicador)/multiplicador;
    }

}
