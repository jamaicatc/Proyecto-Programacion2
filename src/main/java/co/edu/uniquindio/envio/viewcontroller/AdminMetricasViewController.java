package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminMetricasViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarMetricas;

    @FXML
    private Button btnAplicarFiltros;

    @FXML
    private Button btnExportarReporte;

    @FXML
    private Button btnReiniciar;

    @FXML
    private ComboBox<?> cmbZona;

    @FXML
    private DatePicker dpPeriodoFin;

    @FXML
    private DatePicker dpPeriodoInicio;

    @FXML
    private Label lblIncidenciasRegistradas;

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblServiciosAdicional;

    @FXML
    private Label lblTiempoPromedio;

    @FXML
    private TableView<?> tableMetricas;

    @FXML
    private TableColumn<?, ?> tcIndicador;

    @FXML
    private TableColumn<?, ?> tcUltimaActualizacion;

    @FXML
    private TableColumn<?, ?> tcUnidad;

    @FXML
    private TableColumn<?, ?> tcValor;

    @FXML
    void onActualizarMetricas(ActionEvent event) {

    }

    @FXML
    void onAplicarFiltros(ActionEvent event) {

    }

    @FXML
    void onExportarReporte(ActionEvent event) {

    }

    @FXML
    void onReiniciar(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
