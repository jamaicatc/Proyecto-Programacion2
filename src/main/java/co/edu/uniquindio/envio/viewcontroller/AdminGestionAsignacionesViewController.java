package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.IncidenciaDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminGestionAsignacionesViewController implements DataUpdateListener {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();
    private EnvioDto envioSeleccionado;

    // Fields to store current filter criteria
    private String currentFilterEstado;
    private RepartidorDto currentFilterRepartidor;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAsignarRepartidor;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardarIncidencia;

    @FXML
    private Button btnReasignar;

    @FXML
    private Button btnRegistrarIncidencia;

    @FXML
    private Button btnReiniciarFiltros;

    @FXML
    private ComboBox<String> cmbFiltrarEstado;

    @FXML
    private ComboBox<RepartidorDto> cmbRepartidor;

    @FXML
    private TableView<EnvioDto> tableEnviosAsignaciones;

    @FXML
    private TableColumn<EnvioDto, String> tcDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcEstadoActual;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaCreacion;

    @FXML
    private TableColumn<EnvioDto, String> tcIdEnvio;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigen;

    @FXML
    private TableColumn<EnvioDto, String> tcRepartidorAsignado;

    @FXML
    private TableColumn<EnvioDto, String> tcIncidencia; // Agregado

    @FXML
    private TextField txtAsunto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private DatePicker datePicker;

    @FXML
    private HBox hboxIncidencia;

    @FXML
    void onAsignarRepartidor(ActionEvent event) {
        if (envioSeleccionado != null && cmbRepartidor.getValue() != null) {
            modelFactory.getEnvioServices().asignarEnvio(envioSeleccionado, cmbRepartidor.getValue());
            mostrarAlerta("Éxito", "Repartidor asignado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un envío y un repartidor.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onBuscar(ActionEvent event) {
        currentFilterEstado = cmbFiltrarEstado.getValue();
        currentFilterRepartidor = cmbRepartidor.getValue();
        applyFilters();
    }


    @FXML
    void onGuardarIncidencia(ActionEvent event) {
        if (envioSeleccionado != null && txtAsunto.getText() != null && !txtAsunto.getText().isEmpty()
                && txtDescripcion.getText() != null && !txtDescripcion.getText().isEmpty() && datePicker.getValue() != null) {
            String asunto = txtAsunto.getText();
            String descripcion = txtDescripcion.getText();
            LocalDate fecha = datePicker.getValue();
            IncidenciaDto incidencia = new IncidenciaDto(asunto, descripcion, fecha);
            modelFactory.getEnvioServices().registrarIncidencia(envioSeleccionado, incidencia);
            mostrarAlerta("Éxito", "Incidencia registrada correctamente.", Alert.AlertType.INFORMATION);
            limpiarCamposIncidencia();
            hboxIncidencia.setDisable(true);
        } else {
            mostrarAlerta("Error", "Debe seleccionar un envío y completar todos los campos de la incidencia.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onReasignar(ActionEvent event) {
        if (envioSeleccionado != null) {
            RepartidorDto nuevoRepartidor = cmbRepartidor.getValue(); // Puede ser null para desasignar
            modelFactory.getEnvioServices().reasignarEnvio(envioSeleccionado, nuevoRepartidor);
            mostrarAlerta("Éxito", "Envío reasignado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un envío para reasignar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onRegistrarIncidencia(ActionEvent event) {
        if (envioSeleccionado != null) {
            hboxIncidencia.setDisable(false);
        } else {
            mostrarAlerta("Error", "Debe seleccionar un envío para registrar una incidencia.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onReiniciarFiltros(ActionEvent event) {
        cmbFiltrarEstado.setValue(null);
        cmbRepartidor.setValue(null);
        limpiarCamposIncidencia();
        hboxIncidencia.setDisable(true);
        currentFilterEstado = null;
        currentFilterRepartidor = null;
        cargarEnviosNoAsignados();
    }

    @FXML
    void initialize() {
        modelFactory.addDataUpdateListener(this);
        configurarTabla();
        cargarComboBoxes();
        cargarEnviosNoAsignados();

        tableEnviosAsignaciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            envioSeleccionado = newSelection;
            if (newSelection != null) {
                cmbRepartidor.setValue(newSelection.repartidorAsignado());
            }
        });

        cmbRepartidor.setConverter(new StringConverter<>() {
            @Override
            public String toString(RepartidorDto repartidor) {
                return repartidor == null ? null : repartidor.nombre();
            }

            @Override
            public RepartidorDto fromString(String string) {
                return cmbRepartidor.getItems().stream().filter(r ->
                        r.nombre().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void configurarTabla() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionDestino()));
        tcEstadoActual.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
        tcFechaCreacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcRepartidorAsignado.setCellValueFactory(cellData -> {
            RepartidorDto repartidor = cellData.getValue().repartidorAsignado();
            return new SimpleStringProperty(repartidor != null ? repartidor.nombre() : "Sin Asignar");
        });
        tcIncidencia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ultimaIncidenciaDescripcion()));
    }

    private void cargarComboBoxes() {
        RepartidorDto selectedRepartidor = cmbRepartidor.getValue();

        cmbFiltrarEstado.setItems(FXCollections.observableArrayList("Solicitado", "Asignado", "En Ruta", "Entregado", "Incidencia"));

        List<RepartidorDto> allRepartidores = modelFactory.obtenerRepartidores();
        
        List<RepartidorDto> activeRepartidores = (allRepartidores != null) ?
                allRepartidores.stream()
                        .filter(r -> "Activo".equalsIgnoreCase(r.disponibilidad()))
                        .collect(Collectors.toList()) :
                List.of();

        ObservableList<RepartidorDto> observableRepartidores = FXCollections.observableArrayList(activeRepartidores);
        cmbRepartidor.setItems(observableRepartidores);

        if (selectedRepartidor != null) {
            RepartidorDto foundRepartidor = observableRepartidores.stream()
                    .filter(r -> r.idRepartidor().equals(selectedRepartidor.idRepartidor()))
                    .findFirst()
                    .orElse(null);
            cmbRepartidor.setValue(foundRepartidor);
        } else {
            cmbRepartidor.setValue(null);
        }
    }

    private void cargarEnviosNoAsignados() {
        listaEnvios.setAll(modelFactory.getEnvioServices().obtenerEnviosNoAsignados());
        tableEnviosAsignaciones.setItems(listaEnvios);
    }

    private void applyFilters() {
        ObservableList<EnvioDto> allEnvios = FXCollections.observableArrayList(modelFactory.getEnvioServices().obtenerEnvios());
        ObservableList<EnvioDto> enviosFiltrados = allEnvios;

        if (currentFilterEstado != null && !currentFilterEstado.isEmpty() && !currentFilterEstado.equals("Todos")) {
            enviosFiltrados = enviosFiltrados.filtered(envio -> currentFilterEstado.equalsIgnoreCase(envio.estadoActual()));
        }

        if (currentFilterRepartidor != null) {
            enviosFiltrados = enviosFiltrados.filtered(envio -> {
                RepartidorDto repartidorAsignado = envio.repartidorAsignado();
                return repartidorAsignado != null && currentFilterRepartidor.idRepartidor().equals(repartidorAsignado.idRepartidor());
            });
        }

        tableEnviosAsignaciones.setItems(enviosFiltrados);
    }

    private void limpiarCamposIncidencia() {
        txtAsunto.clear();
        txtDescripcion.clear();
        datePicker.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void onDataChanged() {
        cargarComboBoxes();
        if (currentFilterEstado != null || currentFilterRepartidor != null) {
            applyFilters();
        } else {
            cargarEnviosNoAsignados();
        }
        tableEnviosAsignaciones.refresh();
    }
}
