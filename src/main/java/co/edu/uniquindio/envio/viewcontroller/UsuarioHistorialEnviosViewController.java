package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.controller.UsuarioController;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import co.edu.uniquindio.envio.model.adapter.ReportGenerator;
import co.edu.uniquindio.envio.model.adapter.CsvReportGeneratorAdapter;
import co.edu.uniquindio.envio.model.adapter.PdfReportGeneratorAdapter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioHistorialEnviosViewController implements DataUpdateListener {

    private final UsuarioController usuarioController = new UsuarioController(ModelFactory.getInstance());
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();
    private Usuario usuario;

    @FXML
    private Button btnGenerarReporteCsv;

    @FXML
    private Button btnGenerarReportePdf;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TableView<EnvioDto> tableEnvios;

    @FXML
    private TableColumn<EnvioDto, String> tcCosto;

    @FXML
    private TableColumn<EnvioDto, String> tcDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcEstado;

    @FXML
    private TableColumn<EnvioDto, String> tcFecha;

    @FXML
    private TableColumn<EnvioDto, String> tcIdEnvio;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigen;

    @FXML
    void onBuscar(ActionEvent event) {
        filtrarEnvios();
    }

    @FXML
    void onGenerarReporteCsv(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para generar el reporte CSV.", Alert.AlertType.ERROR);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(btnGenerarReporteCsv.getScene().getWindow());

        if (file != null) {
            try {
                ReportGenerator csvAdapter = new CsvReportGeneratorAdapter();
                String reportTitle = "Historial de Envíos del Usuario: " + (usuario != null ? usuario.getNombreCompleto() : "N/A");
                csvAdapter.generateReport(listaEnvios, reportTitle, file.getAbsolutePath());
                mostrarMensaje("Reporte CSV", "Generación Exitosa", "Reporte CSV generado exitosamente en: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al generar reporte CSV", "Error al generar el reporte CSV: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onGenerarReportePdf(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para generar el reporte PDF.", Alert.AlertType.ERROR);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(btnGenerarReportePdf.getScene().getWindow());

        if (file != null) {
            try {
                ReportGenerator pdfAdapter = new PdfReportGeneratorAdapter();
                String reportTitle = "Historial de Envíos del Usuario: " + (usuario != null ? usuario.getNombreCompleto() : "N/A");
                pdfAdapter.generateReport(listaEnvios, reportTitle, file.getAbsolutePath());
                mostrarMensaje("Reporte PDF", "Generación Exitosa", "Reporte PDF generado exitosamente en: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al generar reporte PDF", "Error al generar el reporte PDF: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        dpFechaDesde.setValue(null);
        dpFechaHasta.setValue(null);
        cmbEstado.setValue(null);
        cargarEnviosUsuario();
    }

    @FXML
    void onVerDetalles(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para ver detalles del envío.", Alert.AlertType.ERROR);
            return;
        }
        EnvioDto selectedEnvio = tableEnvios.getSelectionModel().getSelectedItem();
        if (selectedEnvio != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/envio/DetalleEnvio.fxml"));
                Parent root = loader.load();
                DetalleEnvioViewController controller = loader.getController();
                controller.init(usuario.getNombreCompleto(), selectedEnvio);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al cargar detalles", "Error al cargar la ventana de detalles: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Advertencia", "Envío no seleccionado", "No ha seleccionado ningún envío para ver detalles.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void initialize() {
        initTable();
        cmbEstado.setItems(FXCollections.observableArrayList("Solicitado", "Asignado", "En Ruta", "Entregado", "Incidencia"));
        ModelFactory.getInstance().addDataUpdateListener(this);
        onDataChanged();
    }

    private void initTable() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionDestino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().costo())));
        tableEnvios.setItems(listaEnvios);
    }

    private void cargarEnviosUsuario() {
        if (usuario != null) {
            List<EnvioDto> enviosDelUsuario = usuarioController.obtenerEnvios(usuario.getIdUsuario());
            if (enviosDelUsuario != null && !enviosDelUsuario.isEmpty()) {
                actualizarTabla(enviosDelUsuario);
            } else {
                actualizarTabla(Collections.emptyList());
                mostrarMensaje("Información", "Sin Envíos", "El usuario actual no tiene envíos registrados en su historial.", Alert.AlertType.INFORMATION);
            }
        } else {
            listaEnvios.clear();
        }
    }

    private void actualizarTabla(List<EnvioDto> envios) {
        listaEnvios.clear();
        listaEnvios.addAll(envios);
        tableEnvios.refresh();
    }

    private void filtrarEnvios() {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para filtrar envíos.", Alert.AlertType.ERROR);
            return;
        }

        List<EnvioDto> enviosBase = usuarioController.obtenerEnvios(usuario.getIdUsuario());
        List<EnvioDto> enviosFiltrados = enviosBase;

        if (dpFechaDesde.getValue() != null) {
            LocalDate fechaDesde = dpFechaDesde.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> !envio.fechaCreacion().isBefore(fechaDesde))
                    .collect(Collectors.toList());
        }

        if (dpFechaHasta.getValue() != null) {
            LocalDate fechaHasta = dpFechaHasta.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> !envio.fechaCreacion().isAfter(fechaHasta))
                    .collect(Collectors.toList());
        }

        if (cmbEstado.getValue() != null && !cmbEstado.getValue().isEmpty()) {
            String estadoSeleccionado = cmbEstado.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> envio.estadoActual().equalsIgnoreCase(estadoSeleccionado))
                    .collect(Collectors.toList());
        }

        actualizarTabla(enviosFiltrados);
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @Override
    public void onDataChanged() {
        ModelFactory factory = ModelFactory.getInstance();
        Object usuarioLogueado = factory.getUsuarioActual();

        // Condición para actuar: que haya un usuario logueado, pero que NO sea ni admin ni repartidor.
        if (usuarioLogueado instanceof Usuario && factory.getAdministradorActual() == null && factory.getRepartidorActual() == null) {
            this.usuario = (Usuario) usuarioLogueado;
            cargarEnviosUsuario();
        } else {
            // Si es admin, repartidor, o nadie está logueado, esta vista debe estar vacía.
            this.usuario = null;
            listaEnvios.clear();
        }
    }
}
