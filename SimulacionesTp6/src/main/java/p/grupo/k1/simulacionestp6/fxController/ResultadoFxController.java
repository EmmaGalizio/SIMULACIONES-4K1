package p.grupo.k1.simulacionestp6.fxController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import p.grupo.k1.simulacionestp6.dto.VectorEstadoDtoActual;
import p.grupo.k1.simulacionestp6.modelo.colas.VectorEstadoITV;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoRungeKutta;
import p.grupo.k1.simulacionestp6.modelo.rungeKutta.ResultadoSimulacion;

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
    private TextField tf_porcOcupCaseta;

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
    private TextField tf_porcOcupNave;
    @FXML
    private TextField tf_porcOcupOficina;
    @FXML
    private TableView<ResultadoRungeKutta> tv_ecLlegadaAtaque;
    @FXML
    private TableView<ResultadoRungeKutta> tv_ecDuracionBloqLlegadas;
    @FXML
    private TableView<ResultadoRungeKutta> tv_ecDuracionBloqNave;

    @Override
    public void mostrarResultadosSimulacion(ResultadoSimulacion resultadoSimulacion) {

        tv_SimItv.getColumns().clear();
        List<VectorEstadoDtoActual> resultadoActual = this.mapVectorEstado(resultadoSimulacion.getSimulacionItv());

        this.calcularEstadisticas(resultadoSimulacion); //Falta terminar
        //Esto debe ir al último
        tv_SimItv.getItems().addAll(resultadoActual);
        tv_SimItv.refresh();
        this.generarColumnasSimulacion(); //Falta terminar el método generarColumnasSimulacion
        this.generarColumnasLlegadaAtaque();
        this.generarColumnasDuracionBloqLlegadas();
        this.generarColumnasDuracionBloqNave();
        tv_ecLlegadaAtaque.getItems().addAll(resultadoSimulacion.getEcDiferencialLlegadaAtaque());
        if(resultadoSimulacion.getEcDiferencialFinBloqueoLlegadas() != null) {
            tv_ecDuracionBloqLlegadas.getItems().addAll(resultadoSimulacion.getEcDiferencialFinBloqueoLlegadas());
        }
        if(resultadoSimulacion.getEcDiferencialFinBloqueoNaveUno() != null) {
            tv_ecDuracionBloqNave.getItems().addAll(resultadoSimulacion.getEcDiferencialFinBloqueoNaveUno());
        }
        tv_ecDuracionBloqNave.refresh();
        tv_ecDuracionBloqLlegadas.refresh();
        tv_ecLlegadaAtaque.refresh();

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
        // set primitivos
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
        vectorActual.setFinInspeccion1(vector.getFinInspeccion()[0]);
        vectorActual.setFinInspeccion2(vector.getFinInspeccion()[1]);
        vectorActual.setFinAtencionOficina1(vector.getFinAtencionOficina()[0]);
        vectorActual.setFinAtencionOficina2(vector.getFinAtencionOficina()[1]);
        vectorActual.setEventoLlegadaCliente(vector.getProximaLlegadaCliente()); //revisar
        vectorActual.setFinAtencionCaseta(vector.getFinAtencionCaseta()[0]); //revisar
        vectorActual.setFinSimulacion(vector.getFinSimulacion()); //revisar
        vectorActual.setLlegadaAtaque(vector.getLlegadaAtaque());
        vectorActual.setFinBloqueoLlegada(vector.getFinBloqueoLlegada());
        vectorActual.setFinBloqueoNaveUno(vector.getFinBloqueoNaveUno());

        //set empleados
        vectorActual.setEmpleadoCaseta(vector.getEmpleadosCaseta().get(0));
        vectorActual.setInspector1(vector.getEmpleadosNave().get(0));
        vectorActual.setInspector2(vector.getEmpleadosNave().get(1));
        vectorActual.setOficinista1(vector.getEmpleadosOficina().get(0));
        vectorActual.setOficinista2(vector.getEmpleadosOficina().get(1));
        return vectorActual;    }

    private void generarColumnasSimulacion(){

        //tv_SimItv.getItems().clear();

        TableColumn<VectorEstadoDtoActual, String> nombreEvColumna = new TableColumn<>();
        nombreEvColumna.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));
        nombreEvColumna.setText("Nom.Ev.");
        TableColumn<VectorEstadoDtoActual, Float> relojColumna = new TableColumn<>();
        relojColumna.setCellValueFactory(new PropertyValueFactory<>("reloj"));
        relojColumna.setText("Reloj (min)");

        //---------------------------------------------------------
        //ACÁ VAN LAS COLUMNAS DE LOS EVENTOS
        //Eventos LLegada Cliente

        TableColumn<VectorEstadoDtoActual,String> MomentoEventoLLegadaCliente = new TableColumn<>();
        MomentoEventoLLegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ?
                            new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                                                                            .getEventoLlegadaCliente().getMomentoEvento())));
        MomentoEventoLLegadaCliente.setText("Momento Evento Llegada Cliente");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoLLegadaCliente = new TableColumn<>();
        RNDEventoLLegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getRandomProxLlegada().getRandom())));
        RNDEventoLLegadaCliente.setText("RND Llegada Cliente");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoLlegadaCliente = new TableColumn<>();
        TiempoEventoLlegadaCliente.setCellValueFactory(cellData -> cellData.getValue().getEventoLlegadaCliente()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getEventoLlegadaCliente().getTiempoHastaProxLlegada())));
        TiempoEventoLlegadaCliente.setText("Tiempo Llegada Cliente");

        //Eventos Fin Atencion Caseta

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionCaseta = new TableColumn<>();
        MomentoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getMomentoEvento())));
        MomentoFinAtencionCaseta.setText("Momento Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionCaseta = new TableColumn<>();
        RNDEventoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getRandomTiempoAtencion().getRandom())));
        RNDEventoFinAtencionCaseta.setText("RND Fin Atencion Caseta");

        TableColumn<VectorEstadoDtoActual,String> TiempoEventoFinAtencionCaseta = new TableColumn<>();
        TiempoEventoFinAtencionCaseta.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionCaseta()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionCaseta().getTiempoAtencion())));
        TiempoEventoFinAtencionCaseta.setText("Tiempo Fin Atencion Caseta");

        //Evento Fin Inspeccion 1

        TableColumn<VectorEstadoDtoActual,String> MomentoFinInspeccion1 = new TableColumn<>();
        MomentoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getMomentoEvento())));
        MomentoFinInspeccion1.setText("Momento Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinInspeccion1 = new TableColumn<>();
        RNDEventoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion1.setText("RND Fin Inspeccion 1");

        TableColumn<VectorEstadoDtoActual,String> tiempoEventoFinInspeccion1 = new TableColumn<>();
        tiempoEventoFinInspeccion1.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion1().getTiempoFinInspeccion())));
        tiempoEventoFinInspeccion1.setText("Tiempo Fin Inspeccion 1");

        //Evento Fin Inspeccion 2

        TableColumn<VectorEstadoDtoActual,String> MomentoFinInspeccion2 = new TableColumn<>();
        MomentoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getMomentoEvento())));
        MomentoFinInspeccion2.setText("Momento Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinInspeccion2 = new TableColumn<>();
        RNDEventoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getRandomFinInspeccion().getRandom())));
        RNDEventoFinInspeccion2.setText("RND Fin Inspeccion 2");

        TableColumn<VectorEstadoDtoActual,String> tiempoEventoFinInspeccion2 = new TableColumn<>();
        tiempoEventoFinInspeccion2.setCellValueFactory(cellData -> cellData.getValue().getFinInspeccion2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinInspeccion2().getTiempoFinInspeccion())));
        tiempoEventoFinInspeccion2.setText("Tiempo Fin Inspeccion 2");

        //Evento Fin Atencion Oficina 1

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionOficina1 = new TableColumn<>();
        MomentoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getMomentoEvento())));
        MomentoFinAtencionOficina1.setText("Momento Fin Atencion Oficina1");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionOficina1 = new TableColumn<>();
        RNDEventoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina1.setText("RND Fin Atencion Oficina 1");

        TableColumn<VectorEstadoDtoActual,String> TiempoFinAtencionOficina1 = new TableColumn<>();
        TiempoFinAtencionOficina1.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina1()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina1().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina1.setText("Tiempo Fin Atencion Oficina 1");

        //Evento Fin Atencion Oficina 2

        TableColumn<VectorEstadoDtoActual,String> MomentoFinAtencionOficina2 = new TableColumn<>();
        MomentoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getMomentoEvento())));
        MomentoFinAtencionOficina2.setText("Momento Fin Atencion Oficina2");

        TableColumn<VectorEstadoDtoActual,String> RNDEventoFinAtencionOficina2 = new TableColumn<>();
        RNDEventoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getRandomAtencionOficina().getRandom())));
        RNDEventoFinAtencionOficina2.setText("RND Fin Atencion Oficina 2");

        TableColumn<VectorEstadoDtoActual,String> TiempoFinAtencionOficina2 = new TableColumn<>();
        TiempoFinAtencionOficina2.setCellValueFactory(cellData -> cellData.getValue().getFinAtencionOficina2()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinAtencionOficina2().getTiempoAtencionOficina())));
        TiempoFinAtencionOficina2.setText("Tiempo Fin Atencion Oficina 2");

        //Eventos bloqueos
        TableColumn<VectorEstadoDtoActual,String> randomLlegadaAtaqueColumna = new TableColumn<>();
        randomLlegadaAtaqueColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaAtaque() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getLlegadaAtaque().getRandomMomentoAtaque())));
        randomLlegadaAtaqueColumna.setText("RND Llegada Ataque");

        TableColumn<VectorEstadoDtoActual,String> tiempoLlegadaAtaqueColumna = new TableColumn<>();
        tiempoLlegadaAtaqueColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaAtaque() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getLlegadaAtaque().getTiempoHastaLlegada())));
        tiempoLlegadaAtaqueColumna.setText("T. Llegada Ataque");

        TableColumn<VectorEstadoDtoActual,String> momentoLlegadaAtaqueColumna = new TableColumn<>();
        momentoLlegadaAtaqueColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaAtaque() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getLlegadaAtaque().getMomentoEvento())));
        momentoLlegadaAtaqueColumna.setText("Momento. Llegada Ataque");
        TableColumn<VectorEstadoDtoActual,String> rndTipoAtaqueColumna = new TableColumn<>();
        rndTipoAtaqueColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaAtaque() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getLlegadaAtaque().getRandomTipoAtaque())));
        rndTipoAtaqueColumna.setText("RND Tipo Ataque");
        TableColumn<VectorEstadoDtoActual,String> tipoAtaqueColumna = new TableColumn<>();
        tipoAtaqueColumna.setCellValueFactory(cellData -> cellData.getValue().getLlegadaAtaque() ==null || cellData.getValue()
                .getLlegadaAtaque().getTipoAtaque() == null?
                new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue()
                .getLlegadaAtaque().getTipoAtaque()));
        tipoAtaqueColumna.setText("Tipo Ataque");
        //Evento Fin Ataque Llegadas
        TableColumn<VectorEstadoDtoActual,String> tiempoFinAtaqueLlegadasColumna = new TableColumn<>();
        tiempoFinAtaqueLlegadasColumna.setCellValueFactory(cellData -> cellData.getValue().getFinBloqueoLlegada() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getFinBloqueoLlegada().getDuracionBloqueo())));
        tiempoFinAtaqueLlegadasColumna.setText("Durac. Bloq. Lleg.");
        TableColumn<VectorEstadoDtoActual,String> momentoFinAtaqueLlegadasColumna = new TableColumn<>();
        momentoFinAtaqueLlegadasColumna.setCellValueFactory(cellData -> cellData.getValue().getFinBloqueoLlegada() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getFinBloqueoLlegada().getMomentoEvento())));
        momentoFinAtaqueLlegadasColumna.setText("Fin. Bloq. Lleg.");

        //Evento Fin Ataque Servidor
        TableColumn<VectorEstadoDtoActual,String> tiempoFinAtaqueServidorColumna = new TableColumn<>();
        tiempoFinAtaqueServidorColumna.setCellValueFactory(cellData -> cellData.getValue().getFinBloqueoNaveUno() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getFinBloqueoNaveUno().getDuracionBloqueo())));
        tiempoFinAtaqueServidorColumna.setText("Durac. Bloq. Nave 1.");

        TableColumn<VectorEstadoDtoActual,String> momentoFinAtaqueServidorColumna = new TableColumn<>();
        momentoFinAtaqueServidorColumna.setCellValueFactory(cellData -> cellData.getValue().getFinBloqueoNaveUno() ==null ?
                new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue()
                .getFinBloqueoNaveUno().getMomentoEvento())));
        momentoFinAtaqueServidorColumna.setText("Fin. Bloq. Nave 1");

        //Evento Fin de la simulacion

        TableColumn<VectorEstadoDtoActual,String> MomentoFinSimulacion = new TableColumn<>();
        MomentoFinSimulacion.setCellValueFactory(cellData -> cellData.getValue().getFinSimulacion()==null ? new SimpleStringProperty("") : new SimpleStringProperty(Float.toString(cellData.getValue().getFinSimulacion().getMomentoEvento())));
        MomentoFinSimulacion.setText("Momento Fin Simulacion");

        //Y SEGUIDO DE ESO VAN LOS SERVIDORES
        //Servidores Empleado Caseta
        TableColumn<VectorEstadoDtoActual,String> idEmpleadoCaseta = new TableColumn<>();
        idEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getId())));
        idEmpleadoCaseta.setText("Id Emp Caseta");//revisar

        TableColumn<VectorEstadoDtoActual,String> estadoEmpleadoCaseta = new TableColumn<>();
        estadoEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getEmpleadoCaseta().getEstado().getEstado()));
        estadoEmpleadoCaseta.setText("Estado Emp Caseta");

        TableColumn<VectorEstadoDtoActual,String> ClienteEmpleadoCaseta = new TableColumn<>();
        ClienteEmpleadoCaseta.setCellValueFactory(cellData -> cellData.getValue().getEmpleadoCaseta() == null || cellData.getValue().getEmpleadoCaseta().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleadoCaseta().getClienteActual().getNumeroCliente())));
        ClienteEmpleadoCaseta.setText("Cliente Emp Caseta");

        //Servidor Inspector 1
        TableColumn<VectorEstadoDtoActual,String> idInspector1 = new TableColumn<>();
        idInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getId())));
        idInspector1.setText("Id Inspector1");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoInspector1 = new TableColumn<>();
        EstadoInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getInspector1().getEstado().getEstado()));
        EstadoInspector1.setText("Estado Inspector1");

        TableColumn<VectorEstadoDtoActual,String> ClienteInspector1 = new TableColumn<>();
        ClienteInspector1.setCellValueFactory(cellData -> cellData.getValue().getInspector1()== null || cellData.getValue().getInspector1().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector1().getClienteActual().getNumeroCliente())));
        ClienteInspector1.setText("Cliente Inspector1");
        //Servidores Inspector2

        TableColumn<VectorEstadoDtoActual,String> idInspector2 = new TableColumn<>();
        idInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getId())));
        idInspector2.setText("Id Inspector2");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoInspector2 = new TableColumn<>();
        EstadoInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getInspector2().getEstado().getEstado()));
        EstadoInspector2.setText("Estado Inspector2");

        TableColumn<VectorEstadoDtoActual,String> ClienteInspector2 = new TableColumn<>();
        ClienteInspector2.setCellValueFactory(cellData -> cellData.getValue().getInspector2()== null || cellData.getValue().getInspector2().getClienteActual() == null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getInspector2().getClienteActual().getNumeroCliente())));
        ClienteInspector2.setText("Cliente Inspector2");

        //Servidores Oficinista1

        TableColumn<VectorEstadoDtoActual,String> idOficinista1 = new TableColumn<>();
        idOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getId())));
        idOficinista1.setText("Id Oficinista1");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoOficinista1 = new TableColumn<>();
        EstadoOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getOficinista1().getEstado().getEstado()));
        EstadoOficinista1.setText("Estado Oficinista1");

        TableColumn<VectorEstadoDtoActual,String> ClienteOficinista1 = new TableColumn<>();
        ClienteOficinista1.setCellValueFactory(cellData -> cellData.getValue().getOficinista1()== null || cellData.getValue().getOficinista1().getClienteActual() == null? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista1().getClienteActual().getNumeroCliente())));
        ClienteOficinista1.setText("Cliente Oficinista1");

        //Servidores Oficinista2

        TableColumn<VectorEstadoDtoActual,String> idOficinista2 = new TableColumn<>();
        idOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getId())));
        idOficinista2.setText("Id Oficinista2");//revisar

        TableColumn<VectorEstadoDtoActual,String> EstadoOficinista2 = new TableColumn<>();
        EstadoOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null ? new SimpleStringProperty("") : new SimpleStringProperty(cellData.getValue().getOficinista2().getEstado().getEstado()));
        EstadoOficinista2.setText("Estado Oficinista2");

        TableColumn<VectorEstadoDtoActual,String> ClienteOficinista2 = new TableColumn<>();
        ClienteOficinista2.setCellValueFactory(cellData -> cellData.getValue().getOficinista2()== null || cellData.getValue().getOficinista2().getClienteActual() == null? new SimpleStringProperty("") : new SimpleStringProperty(Integer.toString(cellData.getValue().getOficinista2().getClienteActual().getNumeroCliente())));
        ClienteOficinista2.setText("Cliente Oficinista2");

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
        tiempoTotOfiColumna.setText("Ac. T. Ofi");

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


      tv_SimItv.getColumns().addAll(nombreEvColumna, relojColumna,RNDEventoLLegadaCliente,TiempoEventoLlegadaCliente,MomentoEventoLLegadaCliente,
              randomLlegadaAtaqueColumna, tiempoLlegadaAtaqueColumna, momentoLlegadaAtaqueColumna, rndTipoAtaqueColumna,
              tipoAtaqueColumna, tiempoFinAtaqueLlegadasColumna, momentoFinAtaqueLlegadasColumna,
              tiempoFinAtaqueServidorColumna, momentoFinAtaqueServidorColumna,
              RNDEventoFinAtencionCaseta,TiempoEventoFinAtencionCaseta,MomentoFinAtencionCaseta,
              RNDEventoFinInspeccion1, tiempoEventoFinInspeccion1,MomentoFinInspeccion1,
              RNDEventoFinInspeccion2, tiempoEventoFinInspeccion2,  MomentoFinInspeccion2,
              RNDEventoFinAtencionOficina1,TiempoFinAtencionOficina1,MomentoFinAtencionOficina1,
              RNDEventoFinAtencionOficina2,TiempoFinAtencionOficina2, MomentoFinAtencionOficina2,
              colaCasetaColumna, idEmpleadoCaseta, estadoEmpleadoCaseta,ClienteEmpleadoCaseta,colaNaveColumna,
              idInspector1, EstadoInspector1, ClienteInspector1,
              idInspector2, EstadoInspector2, ClienteInspector2,colaOficinaColumna,
              idOficinista1, EstadoOficinista1, ClienteOficinista1, idOficinista2,
              EstadoOficinista2, ClienteOficinista2,  contadorIngresosColumna, contadorNoAtendidosColumna,
              contadorAtFinColumna, tiempoEspColaCasetaColumna, tiempoAtCasetaColumna,
              tiempoTotCasetaColumna, contClientesAtCasetaColumna, tiempoEspColaNaveColumna,
              tiempoAtNaveColumna, tiempoTotNaveColumna, tiempoEspColaOfiColumna, tiempoAtOfiColumna,
              tiempoTotOfiColumna, tiempoTotSistemaColumna, tiempoLibreCasetaColumna,
              tiempoLibreNave, contLongColaNaveColumna, MomentoFinSimulacion);


      tv_SimItv.refresh();

    }

    private void generarColumnasLlegadaAtaque(){

        TableColumn<ResultadoRungeKutta, Double> tmColumna = new TableColumn<>();
        tmColumna.setCellValueFactory(new PropertyValueFactory<>("xm"));
        tmColumna.setText("Tm");
        TableColumn<ResultadoRungeKutta, Double> amColumna = new TableColumn<>();
        amColumna.setCellValueFactory(new PropertyValueFactory<>("ym"));
        amColumna.setText("Am");
        TableColumn<ResultadoRungeKutta, Double> tmp1Columna = new TableColumn<>();
        tmp1Columna.setCellValueFactory(new PropertyValueFactory<>("xmp1"));
        tmp1Columna.setText("Tm+1");
        TableColumn<ResultadoRungeKutta, Double> k1Columna = new TableColumn<>();
        k1Columna.setCellValueFactory(new PropertyValueFactory<>("k1"));
        k1Columna.setText("K1");
        TableColumn<ResultadoRungeKutta, Double> k2Columna = new TableColumn<>();
        k2Columna.setCellValueFactory(new PropertyValueFactory<>("k2"));
        k2Columna.setText("K2");
        TableColumn<ResultadoRungeKutta, Double> k3Columna = new TableColumn<>();
        k3Columna.setCellValueFactory(new PropertyValueFactory<>("k3"));
        k3Columna.setText("K3");
        TableColumn<ResultadoRungeKutta, Double> k4Columna = new TableColumn<>();
        k4Columna.setCellValueFactory(new PropertyValueFactory<>("k4"));
        k4Columna.setText("K4");
        TableColumn<ResultadoRungeKutta, Double> amp1Columna = new TableColumn<>();
        amp1Columna.setCellValueFactory(new PropertyValueFactory<>("ymp1"));
        amp1Columna.setText("Am+1");

        tv_ecLlegadaAtaque.getColumns().addAll(tmColumna, amColumna, tmp1Columna, k1Columna,
                                                    k2Columna, k3Columna, k4Columna, amp1Columna);
        tv_ecLlegadaAtaque.refresh();
    }
    private void generarColumnasDuracionBloqLlegadas(){

        TableColumn<ResultadoRungeKutta, Double> tmColumna = new TableColumn<>();
        tmColumna.setCellValueFactory(new PropertyValueFactory<>("xm"));
        tmColumna.setText("Tm");
        TableColumn<ResultadoRungeKutta, Double> lmColumna = new TableColumn<>();
        lmColumna.setCellValueFactory(new PropertyValueFactory<>("ym"));
        lmColumna.setText("Lm");
        TableColumn<ResultadoRungeKutta, Double> tmp1Columna = new TableColumn<>();
        tmp1Columna.setCellValueFactory(new PropertyValueFactory<>("xmp1"));
        tmp1Columna.setText("Tm+1");
        TableColumn<ResultadoRungeKutta, Double> k1Columna = new TableColumn<>();
        k1Columna.setCellValueFactory(new PropertyValueFactory<>("k1"));
        k1Columna.setText("K1");
        TableColumn<ResultadoRungeKutta, Double> k2Columna = new TableColumn<>();
        k2Columna.setCellValueFactory(new PropertyValueFactory<>("k2"));
        k2Columna.setText("K2");
        TableColumn<ResultadoRungeKutta, Double> k3Columna = new TableColumn<>();
        k3Columna.setCellValueFactory(new PropertyValueFactory<>("k3"));
        k3Columna.setText("K3");
        TableColumn<ResultadoRungeKutta, Double> k4Columna = new TableColumn<>();
        k4Columna.setCellValueFactory(new PropertyValueFactory<>("k4"));
        k4Columna.setText("K4");
        TableColumn<ResultadoRungeKutta, Double> lmp1Columna = new TableColumn<>();
        lmp1Columna.setCellValueFactory(new PropertyValueFactory<>("ymp1"));
        lmp1Columna.setText("Lm+1");

        tv_ecDuracionBloqLlegadas.getColumns().addAll(tmColumna, lmColumna, tmp1Columna, k1Columna,
                k2Columna, k3Columna, k4Columna, lmp1Columna);
        tv_ecDuracionBloqLlegadas.refresh();
    }
    private void generarColumnasDuracionBloqNave(){

        TableColumn<ResultadoRungeKutta, Double> tmColumna = new TableColumn<>();
        tmColumna.setCellValueFactory(new PropertyValueFactory<>("xm"));
        tmColumna.setText("Tm");
        TableColumn<ResultadoRungeKutta, Double> smColumna = new TableColumn<>();
        smColumna.setCellValueFactory(new PropertyValueFactory<>("ym"));
        smColumna.setText("Sm");
        TableColumn<ResultadoRungeKutta, Double> tmp1Columna = new TableColumn<>();
        tmp1Columna.setCellValueFactory(new PropertyValueFactory<>("xmp1"));
        tmp1Columna.setText("Tm+1");
        TableColumn<ResultadoRungeKutta, Double> k1Columna = new TableColumn<>();
        k1Columna.setCellValueFactory(new PropertyValueFactory<>("k1"));
        k1Columna.setText("K1");
        TableColumn<ResultadoRungeKutta, Double> k2Columna = new TableColumn<>();
        k2Columna.setCellValueFactory(new PropertyValueFactory<>("k2"));
        k2Columna.setText("K2");
        TableColumn<ResultadoRungeKutta, Double> k3Columna = new TableColumn<>();
        k3Columna.setCellValueFactory(new PropertyValueFactory<>("k3"));
        k3Columna.setText("K3");
        TableColumn<ResultadoRungeKutta, Double> k4Columna = new TableColumn<>();
        k4Columna.setCellValueFactory(new PropertyValueFactory<>("k4"));
        k4Columna.setText("K4");
        TableColumn<ResultadoRungeKutta, Double> smp1Columna = new TableColumn<>();
        smp1Columna.setCellValueFactory(new PropertyValueFactory<>("ymp1"));
        smp1Columna.setText("Sm+1");
        tv_ecDuracionBloqNave.getColumns().addAll(tmColumna, smColumna, tmp1Columna, k1Columna,
                k2Columna, k3Columna, k4Columna, smp1Columna);
        tv_ecDuracionBloqNave.refresh();
    }

    private void calcularEstadisticas(ResultadoSimulacion resultadoSimulacion){

        tf_longitudMediaColaNave.setText(Float.toString(resultadoSimulacion.getLongitudMediaColaNave()));
        tf_porcentajeAtFinalizada.setText(Float.toString(resultadoSimulacion.getPorcAtencionFinalizada()));
        tf_porcentajeNoAtendidos.setText(Float.toString(resultadoSimulacion.getPorcNoAtendidos()));
        tf_tiempoMedioAtOficina.setText(Float.toString(resultadoSimulacion.getTiempoMedioAtencionOficina()));
        tf_tiempoMedioColaCaseta.setText(Float.toString(resultadoSimulacion.getTiempoMedioColaCaseta()));
        tf_tiempoMedioColaNave.setText(Float.toString(resultadoSimulacion.getTiempoMedioColaNave()));
        tf_porcOcupCaseta.setText(Float.toString(resultadoSimulacion.getPorcTiempoLibreCaseta()));
        tf_porcOcupNave.setText(Float.toString(resultadoSimulacion.getPorcTiempoLibreNave()));
        tf_tiempoMedioPermanencia.setText(Float.toString(resultadoSimulacion.getTiempoMedioPermanenciaSistema()));
        tf_tiempoMedOficina.setText(Float.toString(resultadoSimulacion.getTiempoMedioOficina()));
        tf_porcOcupOficina.setText(Float.toString(resultadoSimulacion.getPorcTiempoLibreOficina()));
    }

}
