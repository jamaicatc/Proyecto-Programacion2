package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

public class UsuarioHistorialEnviosViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGenerarReporteCsv;

    @FXML
    private Button btnGenerarReportePdf;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVerDetalles;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TableColumn<?, ?> tcCosto;

    @FXML
    private TableColumn<?, ?> tcDestino;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcIdEnvio;

    @FXML
    private TableColumn<?, ?> tcOrigen;

    @FXML
    void onBuscar(ActionEvent event) {

    }

    @FXML
    void onGenerarReporteCsv(ActionEvent event) {

    }

    @FXML
    void onGenerarReportePdf(ActionEvent event) {

    }

    @FXML
    void onLimpiar(ActionEvent event) {

    }

    @FXML
    void onVerDetalles(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
