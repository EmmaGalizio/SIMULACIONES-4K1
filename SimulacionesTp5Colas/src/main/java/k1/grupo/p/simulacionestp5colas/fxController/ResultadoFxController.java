package k1.grupo.p.simulacionestp5colas.fxController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoActual;
import k1.grupo.p.simulacionestp5colas.dto.VectorEstadoDtoDosCasetas;
import k1.grupo.p.simulacionestp5colas.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class ResultadoFxController implements IResultadoSImulacion{
    @FXML
    private TextField tf_tiempoMedioAtOficina;

    @FXML
    private TextField tf_tiempoMedioColaCaseta;

    @FXML
    private TextField tf_tiempoMedioLibreCaseta;

    @FXML
    private TextField tf_porcentajeAtFinalizada;

    @FXML
    private TextField tf_tiempoMedOficina;

    @FXML
    private TableView<VectorEstadoDtoActual> tv_SimItv;

    @FXML
    private TextField tf_tiempoMedioPermanencia;

    @FXML
    private TextField tf_tiempoMedioColaNave;

    @FXML
    private TextField tf_longitudMediaColaNave;

    @FXML
    private TextField tf_porcentajeNoAtendidos;

    @FXML
    private TextField tf_tiempoMedioLibreNave;


    @Override
    public void mostrarResultadosSimulacion(List<VectorEstadoITV> resultadoSimulacion) {

        List<VectorEstadoDtoActual> resultadoActual = this.mapVectorEstado(resultadoSimulacion);
        this.generarColumnasSimulacion(); //Falta terminar el método generarColumnasSimulacion
        VectorEstadoITV vectorFinSim = resultadoSimulacion.get(resultadoSimulacion.size()-1);
        this.calcularEstadisticas(vectorFinSim); //Falta terminar

        //Esto debe ir al último
        tv_SimItv.getItems().addAll(resultadoActual);
    }

    private List<VectorEstadoDtoActual> mapVectorEstado(List<VectorEstadoITV> resultado) {
        List<VectorEstadoDtoActual> resultadoActual = new ArrayList<>();
        for (VectorEstadoITV vector : resultado){
            VectorEstadoDtoActual vectorActual = mapVectorEstado(vector);
            resultadoActual.add(vectorActual);

        }return resultadoActual;
    }

    private VectorEstadoDtoActual mapVectorEstado(VectorEstadoITV vector){
        VectorEstadoDtoActual vectorActual = new VectorEstadoDtoActual();
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

        //set eventos
        vectorActual.setFinInspeccion1(vector.getFinInspeccion()[0]);
        vectorActual.setFinInspeccion2(vector.getFinInspeccion()[1]);

        vectorActual.setFinAtencionOficina1(vector.getFinAtencionOficina()[0]);
        vectorActual.setFinAtencionOficina2(vector.getFinAtencionOficina()[1]);

        vectorActual.setEventoLlegadaCliente(vector.getProximaLlegadaCliente()); //revisar
        vectorActual.setFinAtencionCaseta(vector.getFinAtencionCaseta()[0]); //revisar
        vectorActual.setFinSimulacion(vector.getFinSimulacion()); //revisar
        //set empleados
        vectorActual.setEmpleadoCaseta(vector.getEmpleadosCaseta().get(0));
        vectorActual.setInspector1(vector.getEmpleadosNave().get(0));
        vectorActual.setInspector2(vector.getEmpleadosNave().get(1));
        vectorActual.setOficinista1(vector.getEmpleadosOficina().get(0));
        vectorActual.setOficinista2(vector.getEmpleadosOficina().get(1));
        return vectorActual;
    }

    private void generarColumnasSimulacion(){

        tv_SimItv.getItems().clear();
        tv_SimItv.getItems().clear();

        TableColumn<VectorEstadoDtoActual, Integer> nombreEvColumna = new TableColumn<>();
        nombreEvColumna.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));
        nombreEvColumna.setText("Nom.Ev.");
        TableColumn<VectorEstadoDtoActual, Integer> relojColumna = new TableColumn<>();
        relojColumna.setCellValueFactory(new PropertyValueFactory<>("reloj"));
        relojColumna.setText("Reloj (min)");

        //---------------------------------------------------------
        //ACÁ VAN LAS COLUMNAS DE LOS EVENTOS
        //Y SEGUIDO DE ESO VAN LOS SERVIDORES

        //=============================================================

        TableColumn<VectorEstadoDtoActual, Integer> colaCasetaColumna = new TableColumn<>();
        colaCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("colaCaseta"));
        colaCasetaColumna.setText("Cola Cas.");
        TableColumn<VectorEstadoDtoActual, Integer> colaNaveColumna = new TableColumn<>();
        colaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("colaNave"));
        colaNaveColumna.setText("Cola Nav.");
        TableColumn<VectorEstadoDtoActual, Integer> colaOficinaColumna = new TableColumn<>();
        colaOficinaColumna.setCellValueFactory(new PropertyValueFactory<>("colaOficina"));
        colaOficinaColumna.setText("Cola Ofi.");
        TableColumn<VectorEstadoDtoActual, Integer> contadorIngresosColumna = new TableColumn<>();
        contadorIngresosColumna.setCellValueFactory(new PropertyValueFactory<>("contadorVehiculos"));
        contadorIngresosColumna.setText("Cant. Vehículos");
        TableColumn<VectorEstadoDtoActual, Integer> contadorNoAtendidosColumna = new TableColumn<>();
        contadorNoAtendidosColumna.setCellValueFactory(new PropertyValueFactory<>("contadorClientesNoAtendidos"));
        contadorNoAtendidosColumna.setText("Cant. No At.");
        TableColumn<VectorEstadoDtoActual, Integer> contadorAtFinColumna = new TableColumn<>();
        contadorAtFinColumna.setCellValueFactory(new PropertyValueFactory<>("contadorVehiculosAtencionFinalizada"));
        contadorAtFinColumna.setText("Cant. Fin At.");

        TableColumn<VectorEstadoDtoActual, Float> tiempoEspColaCasetaColumna = new TableColumn<>();
        tiempoEspColaCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaCaseta"));
        tiempoEspColaCasetaColumna.setText("Ac. T. Cola Cas.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoAtCasetaColumna = new TableColumn<>();
        tiempoAtCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionCaseta"));
        tiempoAtCasetaColumna.setText("Ac. T. At. Cas.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoTotCasetaColumna = new TableColumn<>();
        tiempoTotCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaCaseta"));
        tiempoTotCasetaColumna.setText("Ac. T. Caseta");
        TableColumn<VectorEstadoDtoActual, Integer> contClientesAtCasetaColumna = new TableColumn<>();
        contClientesAtCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("contadorClientesAtendidosCaseta"));
        contClientesAtCasetaColumna.setText("Cont. At. Caseta");
        TableColumn<VectorEstadoDtoActual, Float> tiempoEspColaNaveColumna = new TableColumn<>();
        tiempoEspColaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaNave"));
        tiempoEspColaNaveColumna.setText("Ac. T. Cola Nav.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoAtNaveColumna = new TableColumn<>();
        tiempoAtNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionNave"));
        tiempoAtNaveColumna.setText("Ac. T. At. Nav.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoTotNaveColumna = new TableColumn<>();
        tiempoTotNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaNave"));
        tiempoTotNaveColumna.setText("Ac. T. Nave");

        TableColumn<VectorEstadoDtoActual, Float> tiempoEspColaOfiColumna = new TableColumn<>();
        tiempoEspColaOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaColaOficina"));
        tiempoEspColaOfiColumna.setText("Ac. T. Cola Ofi.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoAtOfiColumna = new TableColumn<>();
        tiempoAtOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencionOficina"));
        tiempoAtOfiColumna.setText("Ac. T. At. Ofi.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoTotOfiColumna = new TableColumn<>();
        tiempoTotOfiColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoEsperaOficina"));
        tiempoTotNaveColumna.setText("Ac. T. Nave");

        TableColumn<VectorEstadoDtoActual, Float> tiempoTotSistemaColumna = new TableColumn<>();
        tiempoTotSistemaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoAtencion"));
        tiempoTotSistemaColumna.setText("Ac. T. Sist.");

        TableColumn<VectorEstadoDtoActual, Float> tiempoLibreCasetaColumna = new TableColumn<>();
        tiempoLibreCasetaColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoLibreEmpleadosCaseta"));
        tiempoLibreCasetaColumna.setText("Ac. Lib. Cas.");
        TableColumn<VectorEstadoDtoActual, Float> tiempoLibreNave = new TableColumn<>();
        tiempoLibreNave.setCellValueFactory(new PropertyValueFactory<>("acumuladorTiempoLibreEmpleadosNave"));
        tiempoLibreNave.setText("Ac. Lib. Nav");

        TableColumn<VectorEstadoDtoActual, Integer> contLongColaNaveColumna = new TableColumn<>();
        contLongColaNaveColumna.setCellValueFactory(new PropertyValueFactory<>("acumuladorLongitudColaNave"));
        contLongColaNaveColumna.setText("Ac. Lon. Col. Nav");

        //Servidores Empleado Caseta
        TableColumn<VectorEstadoDtoActual,String> idEmpleadoCaseta = new TableColumn<>();
        idEmpleadoCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getId())));
        idEmpleadoCaseta.setText("Id Emp Caseta");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoEmpleadoCaseta = new TableColumn<>();
        EstadoEmpleadoCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmpleadoCaseta().getEstado().getEstado()));
        EstadoEmpleadoCaseta.setText("Estado Emp Caseta");

        TableColumn<VectorEstadoDtoActual,String> ClienteEmpleadoCaseta = new TableColumn<>();
        ClienteEmpleadoCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getClienteActual().getNumeroCliente())));
        ClienteEmpleadoCaseta.setText("Cliente Emp Caseta");

        //Servidor Inspector 1
        TableColumn<VectorEstadoDtoActual,String> idInspector1 = new TableColumn<>();
        idInspector1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getId())));
        idInspector1.setText("Id Inspector1");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoInspector1 = new TableColumn<>();
        EstadoInspector1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInspector1().getEstado().getEstado()));
        EstadoInspector1.setText("Estado Inspector1");

        TableColumn<VectorEstadoDtoActual,String> ClienteInspector1 = new TableColumn<>();
        ClienteInspector1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getClienteActual().getNumeroCliente())));
        ClienteInspector1.setText("Cliente Inspector1");

        //Servidores Inspector2

        TableColumn<VectorEstadoDtoActual,String> idInspector2 = new TableColumn<>();
        idInspector2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getId())));
        idInspector2.setText("Id Inspector2");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoInspector2 = new TableColumn<>();
        EstadoInspector2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInspector2().getEstado().getEstado()));
        EstadoInspector2.setText("Estado Inspector2");

        TableColumn<VectorEstadoDtoActual,String> ClienteInspector2 = new TableColumn<>();
        ClienteInspector2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getClienteActual().getNumeroCliente())));
        ClienteInspector2.setText("Cliente Inspector2");


        //Servidores Oficinista1

        TableColumn<VectorEstadoDtoActual,String> idOficinista1 = new TableColumn<>();
        idOficinista1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getId())));
        idOficinista1.setText("Id Oficinista1");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoOficinista1 = new TableColumn<>();
        EstadoOficinista1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOficinista1().getEstado().getEstado()));
        EstadoOficinista1.setText("Estado Oficinista1");

        TableColumn<VectorEstadoDtoActual,String> ClienteOficinista1 = new TableColumn<>();
        ClienteOficinista1.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getClienteActual().getNumeroCliente())));
        ClienteOficinista1.setText("Cliente Oficinista1");


        //Servidores Oficinista2

        TableColumn<VectorEstadoDtoActual,String> idOficinista2 = new TableColumn<>();
        idOficinista2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getId())));
        idOficinista2.setText("Id Oficinista2");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoOficinista2 = new TableColumn<>();
        EstadoOficinista2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOficinista2().getEstado().getEstado()));
        EstadoOficinista2.setText("Estado Oficinista2");

        TableColumn<VectorEstadoDtoActual,String> ClienteOficinista2 = new TableColumn<>();
        ClienteOficinista2.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getClienteActual().getNumeroCliente())));
        ClienteOficinista2.setText("Cliente Oficinista2");


        //Eventos LLegada Cliente
        TableColumn<VectorEstadoDtoActual,String> NombreEventoLLegadaCliente = new TableColumn<>();
        NombreEventoLLegadaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventoLlegadaCliente().getNombreEvento()));
        NombreEventoLLegadaCliente.setText("Nombre Evento Llegada Cliente");

        TableColumn<VectorEstadoDtoActual,String> MomentoEventoLLegadaCliente = new TableColumn<>();
        MomentoEventoLLegadaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getMomentoEvento())));
        MomentoEventoLLegadaCliente.setText("Momento Evento Llegada Cliente");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoLLegadaCliente = new TableColumn<>();
        RNDEventoLLegadaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getRandomProxLlegada().getRandom())));
        RNDEventoLLegadaCliente.setText("RND Llegada Cliente");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoLlegadaCliente = new TableColumn<>();
        TiempoEventoLlegadaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getTiempoHastaProxLlegada())));
        TiempoEventoLlegadaCliente.setText("Tiempo Llegada Cliente");

        //Eventos Fin Atencion Caseta

        TableColumn<VectorEstadoDtoActual,String> NombreFinAtencionCaseta = new TableColumn<>();
        NombreFinAtencionCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinAtencionCaseta().getNombreEvento()));
        NombreFinAtencionCaseta.setText("Nombre Evento Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionCaseta = new TableColumn<>();
        MomentoFinAtencionCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getMomentoEvento())));
        MomentoFinAtencionCaseta.setText("Momento Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionCaseta = new TableColumn<>();
        RNDEventoFinAtencionCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getRandomTiempoAtencion().getRandom())));
        RNDEventoFinAtencionCaseta.setText("RND Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoFinAtencionCaseta = new TableColumn<>();
        TiempoEventoFinAtencionCaseta.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getTiempoAtencion())));
        TiempoEventoFinAtencionCaseta.setText("Tiempo Fin Atencion Caseta");

        //Evento Fin Inspeccion 1

        TableColumn<VectorEstadoDtoActual,String> NombreFinInspeccion1 = new TableColumn<>();
        NombreFinInspeccion1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinInspeccion1().getNombreEvento()));
        NombreFinInspeccion1.setText("Nombre Evento Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinInspeccion1 = new TableColumn<>();
        MomentoFinInspeccion1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getMomentoEvento())));
        MomentoFinInspeccion1.setText("Momento Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinInspeccion1 = new TableColumn<>();
        RNDEventoFinInspeccion1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion1.setText("RND Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoFinInspeccion1 = new TableColumn<>();
        TiempoEventoFinInspeccion1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getTiempoFinInspeccion())));
        TiempoEventoFinInspeccion1.setText("Tiempo Fin Inspeccion 1");

        //Evento Fin Inspeccion 2

        TableColumn<VectorEstadoDtoActual,String> NombreFinInspeccion2 = new TableColumn<>();
        NombreFinInspeccion2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinInspeccion2().getNombreEvento()));
        NombreFinInspeccion2.setText("Nombre Evento Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinInspeccion2 = new TableColumn<>();
        MomentoFinInspeccion2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getMomentoEvento())));
        MomentoFinInspeccion2.setText("Momento Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinInspeccion2 = new TableColumn<>();
        RNDEventoFinInspeccion2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion2.setText("RND Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoFinInspeccion2 = new TableColumn<>();
        TiempoEventoFinInspeccion2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getTiempoFinInspeccion())));
        TiempoEventoFinInspeccion2.setText("Tiempo Fin Inspeccion 2");

        //Evento Fin Atencion Oficina 1

        TableColumn<VectorEstadoDtoActual,String> NombreFinAtencionOficina1 = new TableColumn<>();
        NombreFinAtencionOficina1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinAtencionOficina1().getNombreEvento()));
        NombreFinAtencionOficina1.setText("NombreFinAtencionOficina1");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionOficina1 = new TableColumn<>();
        MomentoFinAtencionOficina1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getMomentoEvento())));
        MomentoFinAtencionOficina1.setText("Momento Fin Atencion Oficina1");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionOficina1 = new TableColumn<>();
        RNDEventoFinAtencionOficina1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina1.setText("RND Fin Atencion Oficina 1");

        TableColumn<VectorEstadoDtoActual,String> TiempoFinAtencionOficina1 = new TableColumn<>();
        TiempoFinAtencionOficina1.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina1.setText("Tiempo Fin Atencion Oficina 1");

        //Evento Fin Atencion Oficina 2

        TableColumn<VectorEstadoDtoActual,String> NombreFinAtencionOficina2 = new TableColumn<>();
        NombreFinAtencionOficina2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinAtencionOficina2().getNombreEvento()));
        NombreFinAtencionOficina2.setText("NombreFinAtencionOficina2");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionOficina2 = new TableColumn<>();
        MomentoFinAtencionOficina2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getMomentoEvento())));
        MomentoFinAtencionOficina2.setText("Momento Fin Atencion Oficina2");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionOficina2 = new TableColumn<>();
        RNDEventoFinAtencionOficina2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina2.setText("RND Fin Atencion Oficina 2");

        TableColumn<VectorEstadoDtoActual,String> TiempoFinAtencionOficina2 = new TableColumn<>();
        TiempoFinAtencionOficina2.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina2.setText("Tiempo Fin Atencion Oficina 2");

        //Evento Fin de la simulacion

        TableColumn<VectorEstadoDtoActual,String> NombreEventoFinSimulacion = new TableColumn<>();
        NombreEventoFinSimulacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFinSimulacion().getNombreEvento()));
        NombreEventoFinSimulacion.setText("Nombre Fin Simulacion");

        TableColumn<VectorEstadoDtoActual,String> MomentoFinSimulacion = new TableColumn<>();
        MomentoFinSimulacion.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getFinSimulacion().getMomentoEvento())));
        MomentoFinSimulacion.setText("Momento Fin Simulacion");

        //tv_SimItv.getColumns().addAll(contadorAtFinColumna);




        //FALTAN TODOS LOS EVENTOS; Y LOS SERVIDORES
        //ME PARECE QUE NO SERÍA BUENO MOSTRAR CLIENTES
        //FALTA AGREGAR TODAS LAS COLUMNAS A LA TABLA EN EL TableView:
        //tv_SimItv.getColumns().addAll(nombreEvColumna,relojColumna,).....Así pero pasando todas las
        //columnas por parámetro.
    }

    private void calcularEstadisticas(VectorEstadoITV vectorEstadoITV){

        //Al ser el último evento, es un evento de fin de simulación, tiene el reloj seteado a
        //La cantidad de minutos seteados para cortar la simulacion
        float tiempoMedioOfi = vectorEstadoITV.getAcumuladorTiempoEsperaOficina() / vectorEstadoITV.getContadorVehiculosAtencionFinalizada();
        //Se debería hacer con los atendidos en la oficina, pero como al salir de la oficina
        //se termina la tención, entonces es lo mismo poner los clientes con at. finalizada

        float tiempoMedioAtOfi = vectorEstadoITV.getAcumuladorTiempoAtencionOficina()/vectorEstadoITV.getContadorVehiculosAtencionFinalizada();

        float tiempoMedioPermanencia = vectorEstadoITV.getAcumuladorTiempoAtencion()/vectorEstadoITV.getContadorVehiculosAtencionFinalizada();

        float tiempoMedioLibreCaseta; //FALTAN CALCULAR
        float tiempoMedioLibreNave; //FALTAN CALCULAR

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
        //tf_tiempoMedioLibreCaseta.setText(Float.toString(tiempoMedioLibreCaseta));
        //tf_tiempoMedioLibreNave.setText(Float.toString(tiempoMedioLibreNave));
        tf_tiempoMedioPermanencia.setText(Float.toString(tiempoMedioPermanencia));
        tf_tiempoMedOficina.setText(Float.toString(tiempoMedioOfi));

    }

}
