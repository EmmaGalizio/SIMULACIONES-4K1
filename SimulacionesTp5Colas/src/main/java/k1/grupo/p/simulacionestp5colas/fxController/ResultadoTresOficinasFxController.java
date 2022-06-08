package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoActual;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoTresOficinas;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class ResultadoTresOficinasFxController implements IResultadoSImulacion{
    @FXML
    private TextField tf_tiempoMedioAtOficina;

    @FXML
    private TextField tf_tiempoMedioColaCaseta;

    @FXML
    private TextField tf_porcOcupCaseta;

    @FXML
    private TextField tf_porcentajeAtFinalizada;

    @FXML
    private TextField tf_tiempoMedOficina;

    @FXML
    private TableView<VectorEstadoDtoTresOficinas> tv_SimItv;

    @FXML
    private TextField tf_tiempoMedioPermanencia;

    @FXML
    private TextField tf_tiempoMedioColaNave;

    @FXML
    private TextField tf_longitudMediaColaNave;

    @FXML
    private TextField tf_porcentajeNoAtendidos;

    @FXML
    private TextField tf_porcOcupNave;


    @Override
    public void mostrarResultadosSimulacion(List<VectorEstadoITV> resultadoSimulacion) {
        tv_SimItv.getColumns().clear();
        List<VectorEstadoDtoTresOficinas> resultadoActual = this.mapVectorEstado(resultadoSimulacion);

        VectorEstadoITV vectorFinSim = resultadoSimulacion.get(resultadoSimulacion.size()-1);
        this.calcularEstadisticas(vectorFinSim); //Falta terminar

        //Esto debe ir al último
        tv_SimItv.getItems().addAll(resultadoActual);
        tv_SimItv.refresh();
        this.generarColumnasSimulacion(); //Falta terminar el método generarColumnasSimulacion
    }

    private List<VectorEstadoDtoTresOficinas> mapVectorEstado(List<VectorEstadoITV> resultado) {
        List<VectorEstadoDtoTresOficinas> resultadoActual = new ArrayList<>();
        for (VectorEstadoITV vector : resultado){
            VectorEstadoDtoTresOficinas vectorActual = new VectorEstadoDtoTresOficinas();
            //
            //revisar set de las colas

            vectorActual.setColaCaseta(vector.getColaCaseta().size()); // revisar
            vectorActual.setColaNave(vector.getColaNave().size()); //revisar
            vectorActual.setColaOficina(vector.getColaOficina().size()); //revisar

            //set primitivos

            vectorActual.setNombreEvento(vector.getNombreEvento());
            vectorActual.setReloj(vector.getReloj());
            vectorActual.setContadorVehiculos(vector.getContadorVehiculos());
            vectorActual.setContadorClientesNoAtendidos(vector.getContadorClientesNoAtendidos());
            vectorActual.setContadorVehiculosAtencionFinalizada(vector.getContadorVehiculosAtencionFinalizada());
            vectorActual.setAcumuladorTiempoEsperaColaCaseta(vector.getAcumuladorTiempoEsperaColaCaseta());
            vectorActual.setAcumuladorTiempoEsperaCaseta(vector.getAcumuladorTiempoEsperaCaseta());
            vectorActual.setAcumuladorTiempoAtencionCaseta(vector.getAcumuladorTiempoEsperaCaseta());
            vectorActual.setContadorClientesAtendidosCaseta(vector.getContadorClientesAtendidosCaseta());
            vectorActual.setAcumuladorTiempoEsperaColaNave(vector.getAcumuladorTiempoEsperaColaNave());
            vectorActual.setAcumuladorTiempoEsperaNave(vector.getAcumuladorTiempoEsperaNave());
            vectorActual.setAcumuladorTiempoAtencionNave(vector.getAcumuladorTiempoAtencionNave());
            vectorActual.setContadorClientesAtendidosNave(vector.getContadorClientesAtendidosNave());
            vectorActual.setAcumuladorTiempoEsperaColaOficina(vector.getAcumuladorTiempoEsperaColaOficina());
            vectorActual.setAcumuladorTiempoEsperaOficina(vector.getAcumuladorTiempoEsperaOficina());
            vectorActual.setAcumuladorTiempoAtencionOficina(vector.getAcumuladorTiempoAtencionOficina());
            vectorActual.setAcumuladorTiempoAtencion(vector.getAcumuladorTiempoAtencion());

            vectorActual.setAcumuladorTotalEsperaCola(vector.getAcumuladorTotalEsperaCola());
            vectorActual.setAcumuladorTiempoLibreEmpleadosCaseta(vector.getAcumuladorTiempoLibreEmpleadosCaseta());
            vectorActual.setAcumuladorTiempoLibreEmpleadosNave(vector.getAcumuladorTiempoLibreEmpleadosNave());
            vectorActual.setAcumuladorTiempoLibreEmpleadosOficina(vector.getAcumuladorTiempoLibreEmpleadosOficina());
            vectorActual.setAcumuladorLongitudColaNave(vector.getAcumuladorLongitudColaNave());
            vectorActual.setAcumuladorLongColaNaveXTiempoLong(vector.getAcumuladorLongColaNaveXTiempoLong());

            //set eventos
            vectorActual.setFinAtencionCaseta(vector.getFinAtencionCaseta()[0]);

            vectorActual.setFinInspeccion1(vector.getFinInspeccion()[0]);
            vectorActual.setFinInspeccion2(vector.getFinInspeccion()[1]);

            vectorActual.setFinAtencionOficina1(vector.getFinAtencionOficina()[0]);
            vectorActual.setFinAtencionOficina2(vector.getFinAtencionOficina()[1]);
            vectorActual.setFinAtencionOficina3(vector.getFinAtencionOficina()[2]);



            vectorActual.setEventoLlegadaCliente(vector.getProximaLlegadaCliente()); //revisar
            vectorActual.setFinSimulacion(vector.getFinSimulacion()); //revisar

            //set empleados

            vectorActual.setEmpleadoCaseta(vector.getEmpleadosCaseta().get(0));

            vectorActual.setInspector1(vector.getEmpleadosNave().get(0));
            vectorActual.setInspector2(vector.getEmpleadosNave().get(1));

            vectorActual.setOficinista1(vector.getEmpleadosOficina().get(0));
            vectorActual.setOficinista2(vector.getEmpleadosOficina().get(1));
            vectorActual.setOficinista3(vector.getEmpleadosOficina().get(3));

            resultadoActual.add(vectorActual);

        }return resultadoActual;


    }
    private void generarColumnasSimulacion(){

        //tv_SimItv.getItems().clear();

        TableColumn<VectorEstadoDtoTresOficinas, String> nombreEvColumna = new TableColumn<>();
        nombreEvColumna.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));
        nombreEvColumna.setText("Nom.Ev.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> relojColumna = new TableColumn<>();
        relojColumna.setCellValueFactory(new PropertyValueFactory<>("reloj"));
        relojColumna.setText("Reloj (min)");

        //---------------------------------------------------------
        //ACÁ VAN LAS COLUMNAS DE LOS EVENTOS

        //Y SEGUIDO DE ESO VAN LOS SERVIDORES

        //=============================================================

        TableColumn<VectorEstadoDtoTresOficinas, Integer> colaCasetaColumna = new TableColumn<>();
        colaCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("colaCaseta"));
        colaCasetaColumna.setText("Cola Cas.");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> colaNaveColumna = new TableColumn<>();
        colaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("colaNave"));
        colaNaveColumna.setText("Cola Nav.");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> colaOficinaColumna = new TableColumn<>();
        colaOficinaColumna.setCellValueFactory(new PropertyValueFactory<>("colaOficina"));
        colaOficinaColumna.setText("Cola Ofi.");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> contadorIngresosColumna = new TableColumn<>();
        contadorIngresosColumna.setCellValueFactory(new PropertyValueFactory<>("contadorVehiculos"));
        contadorIngresosColumna.setText("Cant. Vehículos");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> contadorNoAtendidosColumna = new TableColumn<>();
        contadorNoAtendidosColumna.setCellValueFactory(new PropertyValueFactory<>("contadorClientesNoAtendidos"));
        contadorNoAtendidosColumna.setText("Cant. No At.");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> contadorAtFinColumna = new TableColumn<>();
        contadorAtFinColumna.setCellValueFactory(new PropertyValueFactory<>("contadorVehiculosAtencionFinalizada"));
        contadorAtFinColumna.setText("Cant. Fin At.");

        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoEspColaCasetaColumna = new TableColumn<>();
        tiempoEspColaCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaCaseta"));
        tiempoEspColaCasetaColumna.setText("Ac. T. Cola Cas.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoAtCasetaColumna = new TableColumn<>();
        tiempoAtCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionCaseta"));
        tiempoAtCasetaColumna.setText("Ac. T. At. Cas.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoTotCasetaColumna = new TableColumn<>();
        tiempoTotCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaCaseta"));
        tiempoTotCasetaColumna.setText("Ac. T. Caseta");
        TableColumn<VectorEstadoDtoTresOficinas, Integer> contClientesAtCasetaColumna = new TableColumn<>();
        contClientesAtCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("contadorClientesAtendidosCaseta"));
        contClientesAtCasetaColumna.setText("Cont. At. Caseta");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoEspColaNaveColumna = new TableColumn<>();
        tiempoEspColaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaNave"));
        tiempoEspColaNaveColumna.setText("Ac. T. Cola Nav.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoAtNaveColumna = new TableColumn<>();
        tiempoAtNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionNave"));
        tiempoAtNaveColumna.setText("Ac. T. At. Nav.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoTotNaveColumna = new TableColumn<>();
        tiempoTotNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaNave"));
        tiempoTotNaveColumna.setText("Ac. T. Nave");

        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoEspColaOfiColumna = new TableColumn<>();
        tiempoEspColaOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaOficina"));
        tiempoEspColaOfiColumna.setText("Ac. T. Cola Ofi.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoAtOfiColumna = new TableColumn<>();
        tiempoAtOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionOficina"));
        tiempoAtOfiColumna.setText("Ac. T. At. Ofi.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoTotOfiColumna = new TableColumn<>();
        tiempoTotOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaOficina"));
        tiempoTotNaveColumna.setText("Ac. T. Nave");

        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoTotSistemaColumna = new TableColumn<>();
        tiempoTotSistemaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencion"));
        tiempoTotSistemaColumna.setText("Ac. T. Sist.");

        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoLibreCasetaColumna = new TableColumn<>();
        tiempoLibreCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoLibreEmpleadosCaseta"));
        tiempoLibreCasetaColumna.setText("Ac. Lib. Cas.");
        TableColumn<VectorEstadoDtoTresOficinas, Float> tiempoLibreNave = new TableColumn<>();
        tiempoLibreNave.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoLibreEmpleadosNave"));
        tiempoLibreNave.setText("Ac. Lib. Nav");

        TableColumn<VectorEstadoDtoTresOficinas, Integer> contLongColaNaveColumna = new TableColumn<>();
        contLongColaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorLongitudColaNave"));
        contLongColaNaveColumna.setText("Ac. Lon. Col. Nav");


        //Servidores Empleado Caseta
        TableColumn<VectorEstadoDtoTresOficinas,String> idEmpleadoCaseta = new TableColumn<>();
        idEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getId())));
        idEmpleadoCaseta.setText("Id Emp Caseta");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoEmpleadoCaseta = new TableColumn<>();
        EstadoEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getEmpleadoCaseta().getEstado().getEstado()));
        EstadoEmpleadoCaseta.setText("Estado Emp Caseta");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteEmpleadoCaseta = new TableColumn<>();
        ClienteEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta() == null || cellData.getValue().getEmpleadoCaseta().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getClienteActual().getNumeroCliente())));
        ClienteEmpleadoCaseta.setText("Cliente Emp Caseta");

        //Servidor Inspector 1
        TableColumn<VectorEstadoDtoTresOficinas,String> idInspector1 = new TableColumn<>();
        idInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getId())));
        idInspector1.setText("Id Inspector1");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoInspector1 = new TableColumn<>();
        EstadoInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getInspector1().getEstado().getEstado()));
        EstadoInspector1.setText("Estado Inspector1");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteInspector1 = new TableColumn<>();
        ClienteInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null || cellData.getValue().getInspector1().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getClienteActual().getNumeroCliente())));
        ClienteInspector1.setText("Cliente Inspector1");

        //Servidores Inspector2

        TableColumn<VectorEstadoDtoTresOficinas,String> idInspector2 = new TableColumn<>();
        idInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getId())));
        idInspector2.setText("Id Inspector2");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoInspector2 = new TableColumn<>();
        EstadoInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getInspector2().getEstado().getEstado()));
        EstadoInspector2.setText("Estado Inspector2");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteInspector2 = new TableColumn<>();
        ClienteInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null || cellData.getValue().getInspector2().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getClienteActual().getNumeroCliente())));
        ClienteInspector2.setText("Cliente Inspector2");


        //Servidores Oficinista1

        TableColumn<VectorEstadoDtoTresOficinas,String> idOficinista1 = new TableColumn<>();
        idOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getId())));
        idOficinista1.setText("Id Oficinista1");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoOficinista1 = new TableColumn<>();
        EstadoOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getOficinista1().getEstado().getEstado()));
        EstadoOficinista1.setText("Estado Oficinista1");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteOficinista1 = new TableColumn<>();
        ClienteOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null || cellData.getValue().getOficinista1().getClienteActual() == null? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getClienteActual().getNumeroCliente())));
        ClienteOficinista1.setText("Cliente Oficinista1");


        //Servidores Oficinista2

        TableColumn<VectorEstadoDtoTresOficinas,String> idOficinista2 = new TableColumn<>();
        idOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getId())));
        idOficinista2.setText("Id Oficinista2");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoOficinista2 = new TableColumn<>();
        EstadoOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getOficinista2().getEstado().getEstado()));
        EstadoOficinista2.setText("Estado Oficinista2");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteOficinista2 = new TableColumn<>();
        ClienteOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null || cellData.getValue().getOficinista2().getClienteActual() == null? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getClienteActual().getNumeroCliente())));
        ClienteOficinista2.setText("Cliente Oficinista2");

        //Servidores Oficinista3

        TableColumn<VectorEstadoDtoTresOficinas,String> idOficinista3 = new TableColumn<>();
        idOficinista3.setCellValueFactory(cellData -> cellData.getValue().getOficinista3()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista3().getId())));
        idOficinista3.setText("Id Oficinista 3");//revisar

        TableColumn<VectorEstadoDtoTresOficinas,String> EstadoOficinista3 = new TableColumn<>();
        EstadoOficinista3.setCellValueFactory(cellData -> cellData.getValue().getOficinista3()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getOficinista3().getEstado().getEstado()));
        EstadoOficinista3.setText("Estado Oficinista 3");

        TableColumn<VectorEstadoDtoTresOficinas,String> ClienteOficinista3 = new TableColumn<>();
        ClienteOficinista3.setCellValueFactory(cellData -> cellData.getValue().getOficinista3()== null || cellData.getValue().getOficinista3().getClienteActual() == null? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista3().getClienteActual().getNumeroCliente())));
        ClienteOficinista3.setText("Cliente Oficinista 3");


        //Eventos LLegada Cliente

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoEventoLLegadaCliente = new TableColumn<>();
        MomentoEventoLLegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getMomentoEvento())));
        MomentoEventoLLegadaCliente.setText("Momento Evento Llegada Cliente");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoLLegadaCliente = new TableColumn<>();
        RNDEventoLLegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getRandomProxLlegada().getRandom())));
        RNDEventoLLegadaCliente.setText("RND Llegada Cliente");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoEventoLlegadaCliente = new TableColumn<>();
        TiempoEventoLlegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getTiempoHastaProxLlegada())));
        TiempoEventoLlegadaCliente.setText("Tiempo Llegada Cliente");

        //Eventos Fin Atencion Caseta

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinAtencionCaseta = new TableColumn<>();
        MomentoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getMomentoEvento())));
        MomentoFinAtencionCaseta.setText("Momento Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinAtencionCaseta = new TableColumn<>();
        RNDEventoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getRandomTiempoAtencion().getRandom())));
        RNDEventoFinAtencionCaseta.setText("RND Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoEventoFinAtencionCaseta = new TableColumn<>();
        TiempoEventoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getTiempoAtencion())));
        TiempoEventoFinAtencionCaseta.setText("Tiempo Fin Atencion Caseta");

        //Evento Fin Inspeccion 1

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinInspeccion1 = new TableColumn<>();
        MomentoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getMomentoEvento())));
        MomentoFinInspeccion1.setText("Momento Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinInspeccion1 = new TableColumn<>();
        RNDEventoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion1.setText("RND Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoEventoFinInspeccion1 = new TableColumn<>();
        TiempoEventoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getTiempoFinInspeccion())));
        TiempoEventoFinInspeccion1.setText("Tiempo Fin Inspeccion 1");

        //Evento Fin Inspeccion 2

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinInspeccion2 = new TableColumn<>();
        MomentoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getMomentoEvento())));
        MomentoFinInspeccion2.setText("Momento Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinInspeccion2 = new TableColumn<>();
        RNDEventoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion2.setText("RND Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoEventoFinInspeccion2 = new TableColumn<>();
        TiempoEventoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getTiempoFinInspeccion())));
        TiempoEventoFinInspeccion2.setText("Tiempo Fin Inspeccion 2");

        //Evento Fin Atencion Oficina 1

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinAtencionOficina1 = new TableColumn<>();
        MomentoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") :new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getMomentoEvento())));
        MomentoFinAtencionOficina1.setText("Momento Fin Atencion Oficina1");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinAtencionOficina1 = new TableColumn<>();
        RNDEventoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") :new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina1.setText("RND Fin Atencion Oficina 1");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoFinAtencionOficina1 = new TableColumn<>();
        TiempoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") :new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina1.setText("Tiempo Fin Atencion Oficina 1");

        //Evento Fin Atencion Oficina 2

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinAtencionOficina2 = new TableColumn<>();
        MomentoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getMomentoEvento())));
        MomentoFinAtencionOficina2.setText("Momento Fin Atencion Oficina2");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinAtencionOficina2 = new TableColumn<>();
        RNDEventoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina2.setText("RND Fin Atencion Oficina 2");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoFinAtencionOficina2 = new TableColumn<>();
        TiempoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina2.setText("Tiempo Fin Atencion Oficina 2");

        //Evento Fin Atencion Oficina 3

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinAtencionOficina3= new TableColumn<>();
        MomentoFinAtencionOficina3.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina3()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina3().getMomentoEvento())));
        MomentoFinAtencionOficina3.setText("Momento Fin Atencion Oficina 3");

        TableColumn<VectorEstadoDtoTresOficinas,String> RNDEventoFinAtencionOficina3 = new TableColumn<>();
        RNDEventoFinAtencionOficina3.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina3()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina3().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina3.setText("RND Fin Atencion Oficina 3");

        TableColumn<VectorEstadoDtoTresOficinas,String> TiempoFinAtencionOficina3 = new TableColumn<>();
        TiempoFinAtencionOficina3.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina3()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina3().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina3.setText("Tiempo Fin Atencion Oficina 3");

        //Evento Fin de la simulacion

        TableColumn<VectorEstadoDtoTresOficinas,String> MomentoFinSimulacion = new TableColumn<>();
        MomentoFinSimulacion.setCellValueFactory(cellData -> cellData.getValue().getFinSimulacion()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinSimulacion().getMomentoEvento())));
        MomentoFinSimulacion.setText("Momento Fin Simulacion");

        tv_SimItv.getColumns().addAll(nombreEvColumna, relojColumna,RNDEventoLLegadaCliente,TiempoEventoLlegadaCliente,MomentoEventoLLegadaCliente,
                RNDEventoFinAtencionCaseta,TiempoEventoFinAtencionCaseta,MomentoFinAtencionCaseta,
                RNDEventoFinInspeccion1, TiempoEventoFinInspeccion1,MomentoFinInspeccion1,
                RNDEventoFinInspeccion2, TiempoEventoFinInspeccion2,  MomentoFinInspeccion2,
                RNDEventoFinAtencionOficina1,TiempoFinAtencionOficina1,MomentoFinAtencionOficina1,
                RNDEventoFinAtencionOficina2,TiempoFinAtencionOficina2, MomentoFinAtencionOficina2,
                RNDEventoFinAtencionOficina3, TiempoFinAtencionOficina3, MomentoFinAtencionOficina3,
                colaCasetaColumna, idEmpleadoCaseta, EstadoEmpleadoCaseta,ClienteEmpleadoCaseta,colaNaveColumna,
                idInspector1, EstadoInspector1, ClienteInspector1,
                idInspector2, EstadoInspector2, ClienteInspector2,colaOficinaColumna,
                idOficinista1, EstadoOficinista1, ClienteOficinista1, idOficinista2,
                EstadoOficinista2, ClienteOficinista2,idOficinista3, EstadoOficinista3, ClienteOficinista3,contadorIngresosColumna,
                contadorNoAtendidosColumna, contadorAtFinColumna, tiempoEspColaCasetaColumna, tiempoAtCasetaColumna,
                tiempoTotCasetaColumna, contClientesAtCasetaColumna, tiempoEspColaNaveColumna,
                tiempoAtNaveColumna, tiempoTotNaveColumna, tiempoEspColaOfiColumna, tiempoAtOfiColumna,
                tiempoTotOfiColumna, tiempoTotSistemaColumna, tiempoLibreCasetaColumna,
                tiempoLibreNave, contLongColaNaveColumna, MomentoFinSimulacion);

        tv_SimItv.refresh();

    }
    private void calcularEstadisticas(VectorEstadoITV vectorEstadoITV){

        //Al ser el último evento, es un evento de fin de simulación, tiene el reloj seteado a
        //La cantidad de minutos seteados para cortar la simulacion
        float tiempoMedioOfi = vectorEstadoITV.getAcumuladorTiempoEsperaOficina() / vectorEstadoITV.getContadorVehiculosAtencionFinalizada();
        //Se debería hacer con los atendidos en la oficina, pero como al salir de la oficina
        //se termina la tención, entonces es lo mismo poner los clientes con at. finalizada

        float tiempoMedioAtOfi = vectorEstadoITV.getAcumuladorTiempoAtencionOficina()/vectorEstadoITV.getContadorVehiculosAtencionFinalizada();

        float tiempoMedioPermanencia = vectorEstadoITV.getAcumuladorTiempoAtencion()/vectorEstadoITV.getContadorVehiculosAtencionFinalizada();

        float porcLibreCaseta = vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosCaseta()*100/ vectorEstadoITV.getReloj();
        float porcLibreNave = vectorEstadoITV.getAcumuladorTiempoLibreEmpleadosNave()*100/ vectorEstadoITV.getReloj();

        float tiempoMedioColaCaseta = vectorEstadoITV.getAcumuladorTiempoEsperaColaCaseta()/vectorEstadoITV.getContadorClientesAtendidosCaseta();
        float tiempoMedioColaNave = vectorEstadoITV.getAcumuladorTiempoEsperaColaNave()/vectorEstadoITV.getContadorClientesAtendidosNave();

        int cantTotalLlegadas = vectorEstadoITV.getContadorVehiculos()+ vectorEstadoITV.getContadorClientesNoAtendidos();
        //Se calcula sobre el total de llegadas, teniendo en cuenta atendidos y no atendidos
        float porcentajeAtFin = ((float)vectorEstadoITV.getContadorVehiculosAtencionFinalizada()*100) /cantTotalLlegadas;

        float porcentajeNoAt = ((float)vectorEstadoITV.getContadorClientesNoAtendidos()*100)/cantTotalLlegadas;

        float longMediaColaNave = ((float)vectorEstadoITV.getAcumuladorLongitudColaNave())/vectorEstadoITV.getContadorClientesAtendidosNave();

        tf_longitudMediaColaNave.setText(Float.toString(longMediaColaNave));
        tf_porcentajeAtFinalizada.setText(Float.toString(porcentajeAtFin));
        tf_porcentajeNoAtendidos.setText(Float.toString(porcentajeNoAt));
        tf_tiempoMedioAtOficina.setText(Float.toString(tiempoMedioAtOfi));
        tf_tiempoMedioColaCaseta.setText(Float.toString(tiempoMedioColaCaseta));
        tf_tiempoMedioColaNave.setText(Float.toString(tiempoMedioColaNave));
        tf_porcOcupCaseta.setText(Float.toString(porcLibreCaseta));
        tf_porcOcupNave.setText(Float.toString(porcLibreNave));
        tf_tiempoMedioPermanencia.setText(Float.toString(tiempoMedioPermanencia));
        tf_tiempoMedOficina.setText(Float.toString(tiempoMedioOfi));

    }
}
