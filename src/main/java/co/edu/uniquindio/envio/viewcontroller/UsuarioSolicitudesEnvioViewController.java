package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.controller.EnvioController;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.model.strategy.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

public class UsuarioSolicitudesEnvioViewController {

    private static final String ID_USUARIO_LOGUEADO = "2245"; // Simulación de usuario logueado
    private static final String NOMBRE_USUARIO_LOGUEADO = "Juan David"; // Simulación de usuario logueado

    EnvioController envioController;
    ModelFactory modelFactory;
    ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();
    EnvioDto envioSeleccionado;

    ObservableList<String> listaEstados = FXCollections.observableArrayList("Solicitado", "Asignado", "En ruta", "Entregado", "Incidencia");
    ObservableList<String> listaTiposTarifa = FXCollections.observableArrayList("Estándar", "Rápida", "Frágil", "Con Seguro");

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
    private ComboBox<String> cmbTipoTarifa;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TableView<EnvioDto> tableEnvios;

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
    private TableColumn<EnvioDto, String> tcPeso;

    @FXML
    private TextField txtOrigen;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtLargo;

    @FXML
    private TextField txtAncho;

    @FXML
    private TextField txtAlto;

    @FXML
    private Label lblCostoEnvio;

    @FXML
    void initialize() {
        envioController = new EnvioController();
        modelFactory = ModelFactory.getInstance();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEnvios();
        tableEnvios.getItems().clear();
        tableEnvios.setItems(listaEnvios);
        listenerSelection();
        cargarDatos();
    }

    private void initDataBinding() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idEnvio()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fecha().toString()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().origen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().destino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));
        tcPeso.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().peso())));
    }

    private void obtenerEnvios() {
        listaEnvios.addAll(envioController.obtenerEnvios(ID_USUARIO_LOGUEADO));
    }

    private void listenerSelection() {
        tableEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            envioSeleccionado = newSelection;
            mostrarInformacionEnvio(envioSeleccionado);
        });

        // Listener para calcular la tarifa cuando se cambia un valor
        txtPeso.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtLargo.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAncho.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAlto.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        cmbTipoTarifa.valueProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
    }

    private void mostrarInformacionEnvio(EnvioDto envioSeleccionado) {
        if (envioSeleccionado != null) {
            txtOrigen.setText(envioSeleccionado.origen());
            txtDestino.setText(envioSeleccionado.destino());
            txtPeso.setText(String.valueOf(envioSeleccionado.peso()));
            txtLargo.setText(String.valueOf(envioSeleccionado.largo()));
            txtAncho.setText(String.valueOf(envioSeleccionado.ancho()));
            txtAlto.setText(String.valueOf(envioSeleccionado.alto()));
            lblCostoEnvio.setText(String.format("$%,.2f", envioSeleccionado.costo()));
        } else {
            limpiarCampos();
        }
    }

    @FXML
    void onBuscar(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {
        cancelarSolicitud();
    }

    @FXML
    void onExportarCsv(ActionEvent event) {

    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

    @FXML
    void onModificar(ActionEvent event) {
        modificarSolicitud();
    }

    @FXML
    void onNuevaSolicitud(ActionEvent event) {
        crearSolicitud();
    }

    @FXML
    void onRefrescar(ActionEvent event) {
        actualizarTabla();
        limpiarCampos();
    }

    @FXML
    void onVerDetalle(ActionEvent event) {
        if (envioSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/envio/DetalleEnvio.fxml"));
                Parent root = loader.load();

                DetalleEnvioViewController controller = loader.getController();
                controller.init(NOMBRE_USUARIO_LOGUEADO, envioSeleccionado);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Detalle del Envío");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarMensaje("Ver detalle", "", "Debe seleccionar un envío", Alert.AlertType.WARNING);
        }
    }

    private void crearSolicitud() {
        EnvioDto envioDto = crearEnvioDto();
        if (datosValidos(envioDto)) {
            if (envioController.agregarEnvio(ID_USUARIO_LOGUEADO, envioDto)) {
                listaEnvios.add(envioDto);
                limpiarCampos();
                mostrarMensaje("Crear envío", "", "El envío se ha creado con éxito", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Crear envío", "", "No se pudo crear el envío (posible ID duplicado)", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Crear envío", "", "Los datos del envío no son válidos. Asegúrese de llenar todos los campos.", Alert.AlertType.WARNING);
        }
    }

    private void modificarSolicitud() {
        if (envioSeleccionado == null) {
            mostrarMensaje("Modificar envío", "", "Debe seleccionar un envío", Alert.AlertType.WARNING);
            return;
        }

        if (!"Solicitado".equals(envioSeleccionado.estado())) {
            mostrarMensaje("Modificar envío", "", "Solo se pueden modificar envíos en estado 'Solicitado'", Alert.AlertType.WARNING);
            return;
        }

        EnvioDto envioModificado = crearEnvioDto(envioSeleccionado);

        if (datosValidos(envioModificado)) {
            if (envioController.actualizarEnvio(envioModificado)) {
                actualizarTabla();
                limpiarCampos();
                mostrarMensaje("Modificar envío", "", "El envío se ha modificado con éxito", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Modificar envío", "", "No se pudo modificar el envío", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Modificar envío", "", "Los datos del envío no son válidos", Alert.AlertType.WARNING);
        }
    }

    private void cancelarSolicitud() {
        if (envioSeleccionado != null) {
            if (envioController.eliminarEnvio(envioSeleccionado.idEnvio())) {
                listaEnvios.remove(envioSeleccionado);
                envioSeleccionado = null;
                tableEnvios.getSelectionModel().clearSelection();
                limpiarCampos();
                mostrarMensaje("Cancelar envío", "", "El envío se ha cancelado con éxito", Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje("Cancelar envío", "", "No se pudo cancelar el envío", Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Cancelar envío", "", "Debe seleccionar un envío", Alert.AlertType.WARNING);
        }
    }

    private EnvioDto crearEnvioDto() {
        String id = "env-" + UUID.randomUUID().toString().substring(0, 4);
        LocalDate fecha = LocalDate.now();
        LocalDate fechaEstimada = fecha.plusDays(3);
        double costo = calcularCosto();
        return new EnvioDto(
                id,
                fecha,
                fechaEstimada,
                txtOrigen.getText(),
                txtDestino.getText(),
                "Solicitado",
                Double.parseDouble(txtPeso.getText()),
                Double.parseDouble(txtLargo.getText()),
                Double.parseDouble(txtAncho.getText()),
                Double.parseDouble(txtAlto.getText()),
                costo
        );
    }

    private EnvioDto crearEnvioDto(EnvioDto envioOriginal) {
        double costo = calcularCosto();
        return new EnvioDto(
                envioOriginal.idEnvio(),
                envioOriginal.fecha(),
                envioOriginal.fechaEntregaEstimada(),
                txtOrigen.getText(),
                txtDestino.getText(),
                envioOriginal.estado(),
                Double.parseDouble(txtPeso.getText()),
                Double.parseDouble(txtLargo.getText()),
                Double.parseDouble(txtAncho.getText()),
                Double.parseDouble(txtAlto.getText()),
                costo
        );
    }

    private boolean datosValidos(EnvioDto envioDto) {
        return envioDto.origen() != null && !envioDto.origen().isBlank() &&
                envioDto.destino() != null && !envioDto.destino().isBlank() &&
                envioDto.peso() > 0 &&
                envioDto.largo() > 0 &&
                envioDto.ancho() > 0 &&
                envioDto.alto() > 0;
    }

    private void actualizarTabla() {
        listaEnvios.clear();
        listaEnvios.addAll(envioController.obtenerEnvios(ID_USUARIO_LOGUEADO));
    }

    private void limpiarCampos() {
        txtOrigen.clear();
        txtDestino.clear();
        txtPeso.clear();
        txtLargo.clear();
        txtAncho.clear();
        txtAlto.clear();
        cmbTipoTarifa.getSelectionModel().clearSelection();
        lblCostoEnvio.setText("$0.00");
        tableEnvios.getSelectionModel().clearSelection();
    }

    private void cargarDatos() {
        cmbEstado.setItems(listaEstados);
        cmbTipoTarifa.setItems(listaTiposTarifa);
    }

    private double calcularCosto() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double largo = Double.parseDouble(txtLargo.getText());
            double ancho = Double.parseDouble(txtAncho.getText());
            double alto = Double.parseDouble(txtAlto.getText());

            Envio envio = new Envio(
                    "", LocalDate.now(), LocalDate.now(), "", "", "",
                    peso, largo, ancho, alto, 0.0
            );

            ITarifaStrategy estrategia = null;
            String tipoTarifa = cmbTipoTarifa.getValue();

            if (tipoTarifa != null) {
                switch (tipoTarifa) {
                    case "Rápida":
                        estrategia = new TarifaRapidaStrategy();
                        break;
                    case "Frágil":
                        estrategia = new TarifaFragilStrategy();
                        break;
                    case "Con Seguro":
                        estrategia = new TarifaConSeguroStrategy();
                        break;
                    default:
                        estrategia = new TarifaEstandarStrategy();
                        break;
                }
            }

            if (estrategia != null) {
                double costo = modelFactory.calcularTarifa(envio, estrategia);
                lblCostoEnvio.setText(String.format("$%,.2f", costo));
                return costo;
            }
        } catch (NumberFormatException e) {
            lblCostoEnvio.setText("Datos inválidos");
        }
        return 0.0;
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}
