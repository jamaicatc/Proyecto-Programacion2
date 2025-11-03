package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsuarioSolicitudesEnvioViewController {

    ObservableList<String> listaEstados = FXCollections.observableArrayList("Solicitado", "Asignado", "En ruta", "Entregado", "Incidencia");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnExportarCsv;

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnNuevaSolicitud;

    @FXML
    private Button btnRefrescar;

    @FXML
    private Button btnVerDetalle;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TableView<?> tableEnvios;

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
    private TableColumn<?, ?> tcPeso;

    @FXML
    void initialize() {
        cargarDatos();
    }

    @FXML
    void onBuscar(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {

    }

    @FXML
    void onExportarCsv(ActionEvent event) {

    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

    @FXML
    void onModificar(ActionEvent event) {

    }

    @FXML
    void onNuevaSolicitud(ActionEvent event) {

    }

    @FXML
    void onRefrescar(ActionEvent event) {

    }

    @FXML
    void onVerDetalle(ActionEvent event) {

    }

    private void cargarDatos() {
        cmbEstado.setItems(listaEstados);
    }



}
