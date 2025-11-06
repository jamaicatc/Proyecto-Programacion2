package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.controller.EnvioController;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UsuarioRastrearEnvioViewController {

    private EnvioController envioController;
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscar;

    @FXML
    private TableView<EnvioDto> tableHistorial;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigen;

    @FXML
    private TableColumn<EnvioDto, String> tcDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaCreacion;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaLlegada;

    @FXML
    private TableColumn<EnvioDto, String> tcEstado;

    @FXML
    private TableColumn<EnvioDto, String> tcIncidencia;

    @FXML
    private TextField txtNumeroSeguimiento;

    @FXML
    void onBuscar(ActionEvent event) {
        String idEnvio = txtNumeroSeguimiento.getText();
        if (idEnvio != null && !idEnvio.isBlank()) {
            EnvioDto envio = envioController.obtenerEnvio(idEnvio);
            listaEnvios.clear();
            if (envio != null) {
                listaEnvios.add(envio);
            } else {
                mostrarMensaje("Rastrear Envío", "", "No se encontró ningún envío con el ID proporcionado.", Alert.AlertType.WARNING);
            }
        } else {
            listaEnvios.clear();
            mostrarMensaje("Rastrear Envío", "", "Por favor, ingrese un número de seguimiento.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void initialize() {
        envioController = new EnvioController();
        initDataBinding();
    }

    private void initDataBinding() {
        tableHistorial.setItems(listaEnvios);
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().origen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().destino()));
        tcFechaCreacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        tcFechaLlegada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaEntregaEstimada().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));
        tcIncidencia.setCellValueFactory(cellData -> new SimpleStringProperty("")); // No hay campo de incidencia en EnvioDto
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
