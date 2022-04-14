package emma.galzio.simulaciones_tp1_javafx.fxController;

import emma.galzio.simulaciones_tp1_javafx.controller.ControladorTP1;
import emma.galzio.simulaciones_tp1_javafx.controller.utils.ConstantesGenerador;
import emma.galzio.simulaciones_tp1_javafx.modelo.ParametrosGenerador;
import emma.galzio.simulaciones_tp1_javafx.modelo.Pseudoaleatorio;
import emma.galzio.simulaciones_tp1_javafx.utils.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

@Component
@Lazy
public class RandomsGeneradosFxController implements Initializable {

    @FXML
    private TextField tf_c;
    @FXML
    private TextField tf_a;
    @FXML
    private Button btn_generarPseudo;
    @FXML
    private RadioButton rbt_lenguaje;
    @FXML
    private RadioButton rbt_congruencial;
    @FXML
    private TextField tf_m;
    @FXML
    private ToggleGroup metodo_generador;
    @FXML
    private TextField tf_n;
    @FXML
    private TextField tf_k;
    @FXML
    private TableView<Pseudoaleatorio> tv_randoms_gen;

    @FXML
    private RadioButton rbt_multiplicativo;

    @FXML
    private TextField tf_g;
    @FXML
    private TextField tf_x0;
    @FXML
    private Button btn_siguiente;
    @FXML
    private Button btn_back;

    private String metodoSeleccionado;
    @Autowired
    private ControladorTP1 controladorTP1;
    @Autowired
    private StageManager stageManager;
    @Value("classpath:/fxml/main.fxml")
    private Resource mainSceneResource;

    @Override
    /***
     * Se ejecuta despues de que termina de ejecutarse el constructor de la clase y despues que
     * terminan de inyectarse todos los campos, que en este caso son nodos de la escena.
     * Este en particular se usa para agregar un formateador de texto a los campos de texto para
     * que solo permitan el ingreso de números
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {


        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tf_c.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_n.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_k.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_g.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_x0.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),null,integerFilter));
        tf_a.setEditable(false);
        tf_m.setEditable(false);
        desHabilitarCamposTexto();
        btn_generarPseudo.setDisable(true);
        btn_siguiente.setDisable(true);

    }
    private void desHabilitarCamposTexto(){
        tf_n.setDisable(true);
        tf_k.setDisable(true);
        tf_g.setDisable(true);
        tf_c.setDisable(true);
        tf_m.setDisable(true);
        tf_a.setDisable(true);
        tf_x0.setDisable(true);
    }
    @FXML
    void generarLineal(ActionEvent event) {
        this.limpiarTabla();
        tv_randoms_gen.getColumns().addAll(generarColumnasCongruencial("A.Xi + C"));
        metodoSeleccionado = ConstantesGenerador.LINEAL;
        habilitarCamposLineal();
        btn_generarPseudo.setDisable(false);
        btn_siguiente.setDisable(true);
    }
    private void habilitarCamposLineal(){
        tf_n.setDisable(false);
        tf_k.setDisable(false);
        tf_g.setDisable(false);
        tf_c.setDisable(false);
        tf_m.setDisable(false);
        tf_a.setDisable(false);
        tf_x0.setDisable(false);
    }

    @FXML
    void generarMultiplicativo(ActionEvent event) {
        limpiarTabla();
        tv_randoms_gen.getColumns().addAll(generarColumnasCongruencial("A.Xi"));
        metodoSeleccionado = ConstantesGenerador.MULTIPLICATIVO;
        btn_generarPseudo.setDisable(false);
        btn_siguiente.setDisable(true);
        habilitarCamposMultiplicativo();
    }
    private void habilitarCamposMultiplicativo(){
        tf_n.setDisable(false);
        tf_k.setDisable(false);
        tf_g.setDisable(false);
        tf_c.setDisable(true);
        tf_m.setDisable(false);
        tf_a.setDisable(false);
        tf_x0.setDisable(false);
    }



    @FXML
    void generarLenguaje(ActionEvent event) {
        limpiarTabla();
        TableColumn<Pseudoaleatorio,Integer> countColumn = new TableColumn<>();
        countColumn.setCellValueFactory(new PropertyValueFactory<>("i"));
        countColumn.setText("i");
        TableColumn<Pseudoaleatorio,Integer> randomColumn = new TableColumn<>();
        randomColumn.setCellValueFactory(new PropertyValueFactory<>("random"));
        randomColumn.setText("Random");
        tv_randoms_gen.getColumns().addAll(FXCollections.observableArrayList(countColumn,randomColumn));
        metodoSeleccionado = ConstantesGenerador.LENGUAJE;
        habilitarCamposLenguaje();
        btn_generarPseudo.setDisable(false);
        btn_siguiente.setDisable(true);
    }
    private void habilitarCamposLenguaje(){
        tf_n.setDisable(false);
        tf_k.setDisable(true);
        tf_g.setDisable(true);
        tf_c.setDisable(true);
        tf_m.setDisable(true);
        tf_a.setDisable(true);
        tf_x0.setDisable(true);
    }

    @FXML
    void validarNumero(KeyEvent event) {
    }

    @FXML
    void btnSiguienteRandom(ActionEvent actionEvent){
        Pseudoaleatorio ultimoPseudo = tv_randoms_gen.getItems().get(tv_randoms_gen.getItems().size()-1);
        ultimoPseudo.setI(tv_randoms_gen.getItems().size());
        int n = !tf_n.getText().trim().isBlank() ? Integer.parseInt(tf_n.getText()): 0;
        int k = !tf_k.getText().trim().isBlank() ? Integer.parseInt(tf_k.getText()): 0;
        int g = !tf_g.getText().trim().isBlank() ? Integer.parseInt(tf_g.getText()): 0;
        int c = !tf_c.getText().trim().isBlank() ? Integer.parseInt(tf_c.getText()): 0;
        int x0 = !tf_x0.getText().trim().isBlank() ? Integer.parseInt(tf_x0.getText()): 0;
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador(n,x0,g,k,c,4);
        Pseudoaleatorio siguientePseudo = controladorTP1.generarSiguientePseudo(parametrosGenerador,ultimoPseudo,metodoSeleccionado);
        siguientePseudo.setI(ultimoPseudo.getI()+1);
        tv_randoms_gen.getItems().add(siguientePseudo);
        autoResizeColumns(tv_randoms_gen);
    }

    private ObservableList<TableColumn<Pseudoaleatorio,?>> generarColumnasCongruencial(String axiHeader){
        TableColumn<Pseudoaleatorio,Integer> countColumn = new TableColumn<>();
        countColumn.setCellValueFactory(new PropertyValueFactory<>("i"));
        countColumn.setText("i");
        TableColumn<Pseudoaleatorio,Integer> axipcColumn = new TableColumn<>();
        axipcColumn.setCellValueFactory(new PropertyValueFactory<>("axi"));
        axipcColumn.setText(axiHeader);
        TableColumn<Pseudoaleatorio,Integer> x0Column = new TableColumn<>();
        x0Column.setText("X0");
        x0Column.setCellValueFactory(new PropertyValueFactory<>("semilla"));
        TableColumn<Pseudoaleatorio,Double> randomColumn = new TableColumn<>();
        randomColumn.setText("Random");
        randomColumn.setCellValueFactory(new PropertyValueFactory<>("random"));

        return FXCollections.observableArrayList(countColumn,
                x0Column,axipcColumn,randomColumn);
    }

    @FXML
    void generarPseudoAleatoreo(ActionEvent actionEvent){

        int n = !tf_n.getText().trim().isBlank() ? Integer.parseInt(tf_n.getText()): 0;
        int k = !tf_k.getText().trim().isBlank() ? Integer.parseInt(tf_k.getText()): 0;
        int g = !tf_g.getText().trim().isBlank() ? Integer.parseInt(tf_g.getText()): 0;
        int c = !tf_c.getText().trim().isBlank() ? Integer.parseInt(tf_c.getText()): 0;
        int x0 = !tf_x0.getText().trim().isBlank() ? Integer.parseInt(tf_x0.getText()): 0;
        ParametrosGenerador parametrosGenerador = new ParametrosGenerador(n,x0,g,k,c,4);
        try {
            Pseudoaleatorio[] pseudoaleatorios = controladorTP1
                    .generarPseudoAleatoreos(parametrosGenerador, metodoSeleccionado);
            llenarTabla(pseudoaleatorios);
            autoResizeColumns(tv_randoms_gen);
            btn_siguiente.setDisable(false);
        }catch(IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    private void llenarTabla(Pseudoaleatorio [] pseudoaleatorios){
        ObservableList<Pseudoaleatorio> observableList = FXCollections.observableArrayList(pseudoaleatorios);
        tv_randoms_gen.setItems(observableList);
    }

    private void limpiarTabla(){
        tv_randoms_gen.getColumns().removeAll(tv_randoms_gen.getColumns());
        tv_randoms_gen.getItems().removeAll(tv_randoms_gen.getItems());
    }

    private void autoResizeColumns( TableView<?> table )
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth( max + 15.0d );
        } );
    }

    @FXML
    void btnBackMain(ActionEvent actionEvent)throws IOException {
        stageManager.loadStageParentScene(mainSceneResource.getURL(),400,400);
    }

}

