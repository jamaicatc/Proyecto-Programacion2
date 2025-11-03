package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AdminVisualizacionMetricasViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarGraficos;

    @FXML
    private Button btnExportarImagen;

    @FXML
    private Button btnGenerarGrafico;

    @FXML
    private Button btnReiniciar;

    @FXML
    private ComboBox<?> cmbTipoGrafico;

    @FXML
    private DatePicker dpPeriodoFin;

    @FXML
    private DatePicker dpPeriodoInicio;

    @FXML
    private Label lblPromedioEntrega;

    @FXML
    private Label lblTotalIncidencias;

    @FXML
    private Label lblZonaMayorActividad;

    @FXML
    void onActualizarGraficos(ActionEvent event) {

    }

    @FXML
    void onExportarImagen(ActionEvent event) {

    }

    @FXML
    void onGenerarGrafico(ActionEvent event) {

    }

    @FXML
    void onReiniciar(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
