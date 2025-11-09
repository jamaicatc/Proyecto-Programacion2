package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.controller.EnvioController;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.model.decorator.Tarifa;
import co.edu.uniquindio.envio.model.decorator.TarifaBase;
import co.edu.uniquindio.envio.model.decorator.TarifaFragil;
import co.edu.uniquindio.envio.model.decorator.TarifaPrioritaria;
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
import java.util.stream.Collectors;

public class UsuarioSolicitudesEnvioViewController {

    private static final String ID_USUARIO_LOGUEADO = "2245"; // Simulación de usuario logueado
    private static final String NOMBRE_USUARIO_LOGUEADO = "Juan David"; // Simulación de usuario logueado

    EnvioController envioController;
    ModelFactory modelFactory;
    ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();
    EnvioDto envioSeleccionado;

    ObservableList<String> listaEstados = FXCollections.observableArrayList("Todos", "Solicitado", "Asignado", "En ruta", "Entregado", "Incidencia");
    ObservableList<String> listaPrioridades = FXCollections.observableArrayList("Normal", "Alta", "Urgente");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCancelarEnvio;

    @FXML
    private Button btnExportarCsv;

    @FXML
    private Button btnExportarPdf;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnNuevaSolicitud;

    @FXML
    private Button btnRefrescarTabla;

    @FXML
    private Button btnVerDetalle;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private ComboBox<String> cmbPrioridad;

    @FXML
    private CheckBox chkFragil;

    @FXML
    private CheckBox chkUrgente;

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
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionDestino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
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

        txtPeso.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtLargo.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAncho.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAlto.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        cmbPrioridad.valueProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        chkFragil.selectedProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        chkUrgente.selectedProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
    }

    private void mostrarInformacionEnvio(EnvioDto envioSeleccionado) {
        if (envioSeleccionado != null) {
            txtOrigen.setText(envioSeleccionado.direccionOrigen());
            txtDestino.setText(envioSeleccionado.direccionDestino());
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
        String estado = cmbEstado.getValue();
        LocalDate fechaDesde = dpFechaDesde.getValue();
        LocalDate fechaHasta = dpFechaHasta.getValue();

        ObservableList<EnvioDto> enviosFiltrados = listaEnvios.stream()
                .filter(envio -> {
                    boolean coincideEstado = true;
                    if (estado != null && !estado.isEmpty() && !estado.equals("Todos")) {
                        coincideEstado = envio.estadoActual().equalsIgnoreCase(estado);
                    }
                    return coincideEstado;
                })
                .filter(envio -> {
                    boolean coincideFecha = true;
                    LocalDate fechaEnvio = envio.fechaCreacion();
                    if (fechaDesde != null && fechaHasta != null) {
                        coincideFecha = !fechaEnvio.isBefore(fechaDesde) && !fechaEnvio.isAfter(fechaHasta);
                    } else if (fechaDesde != null) {
                        coincideFecha = !fechaEnvio.isBefore(fechaDesde);
                    } else if (fechaHasta != null) {
                        coincideFecha = !fechaEnvio.isAfter(fechaHasta);
                    }
                    return coincideFecha;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tableEnvios.setItems(enviosFiltrados);
    }


    @FXML
    void onCancelarEnvio(ActionEvent event) {
        cancelarSolicitud();
    }

    @FXML
    void onExportarCsv(ActionEvent event) {

    }

    @FXML
    void onExportarPdf(ActionEvent event) {

    }

    @FXML
    void onActualizar(ActionEvent event) {
        modificarSolicitud();
    }

    @FXML
    void onNuevaSolicitud(ActionEvent event) {
        crearSolicitud();
    }

    @FXML
    void onRefrescarTabla(ActionEvent event) {
        actualizarTabla();
        limpiarCampos();
        cmbEstado.getSelectionModel().clearSelection();
        dpFechaDesde.setValue(null);
        dpFechaHasta.setValue(null);
        tableEnvios.setItems(listaEnvios);
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

        if (!"Solicitado".equals(envioSeleccionado.estadoActual())) {
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
            if (envioController.eliminarEnvio(envioSeleccionado.id())) {
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
        double peso = 0.0, largo = 0.0, ancho = 0.0, alto = 0.0;
        try {
            peso = Double.parseDouble(txtPeso.getText());
            largo = Double.parseDouble(txtLargo.getText());
            ancho = Double.parseDouble(txtAncho.getText());
            alto = Double.parseDouble(txtAlto.getText());
        } catch (NumberFormatException e) {
            // El método datosValidos se encargará de mostrar el error
        }
        double costo = calcularCosto();
        return new EnvioDto(
                id,
                fecha,
                fechaEstimada,
                txtOrigen.getText(),
                txtDestino.getText(),
                "Solicitado",
                peso,
                largo,
                ancho,
                alto,
                costo,
                null,
                false,
                null, // ultimaIncidenciaDescripcion
                null // factura
        );
    }

    private EnvioDto crearEnvioDto(EnvioDto envioOriginal) {
        double peso = 0.0, largo = 0.0, ancho = 0.0, alto = 0.0;
        try {
            peso = Double.parseDouble(txtPeso.getText());
            largo = Double.parseDouble(txtLargo.getText());
            ancho = Double.parseDouble(txtAncho.getText());
            alto = Double.parseDouble(txtAlto.getText());
        } catch (NumberFormatException e) {
            // El método datosValidos se encargará de mostrar el error
        }
        double costo = calcularCosto();
        return new EnvioDto(
                envioOriginal.id(),
                envioOriginal.fechaCreacion(),
                envioOriginal.fechaEntrega(),
                txtOrigen.getText(),
                txtDestino.getText(),
                envioOriginal.estadoActual(),
                peso,
                largo,
                ancho,
                alto,
                costo,
                envioOriginal.repartidorAsignado(),
                envioOriginal.pago(),
                envioOriginal.ultimaIncidenciaDescripcion(),
                envioOriginal.factura()
        );
    }

    private boolean datosValidos(EnvioDto envioDto) {
        return envioDto.direccionOrigen() != null && !envioDto.direccionOrigen().isBlank() &&
                envioDto.direccionDestino() != null && !envioDto.direccionDestino().isBlank() &&
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
        cmbPrioridad.getSelectionModel().clearSelection();
        chkFragil.setSelected(false);
        chkUrgente.setSelected(false);
        lblCostoEnvio.setText("$0.00");
        tableEnvios.getSelectionModel().clearSelection();
    }

    private void cargarDatos() {
        cmbEstado.setItems(listaEstados);
        cmbPrioridad.setItems(listaPrioridades);
        cmbPrioridad.setValue("Normal");
    }

    private double calcularCosto() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double largo = Double.parseDouble(txtLargo.getText());
            double ancho = Double.parseDouble(txtAncho.getText());
            double alto = Double.parseDouble(txtAlto.getText());
            double volumen = largo * ancho * alto;
            String origen = txtOrigen.getText();
            String destino = txtDestino.getText();

            Tarifa tarifa = new TarifaBase(peso, volumen, 1, false, origen, destino); // Asumiendo cantidad=1 y no es documento

            if (chkFragil.isSelected()) {
                tarifa = new TarifaFragil(tarifa);
            }

            String prioridad = cmbPrioridad.getValue();
            if (chkUrgente.isSelected() || (prioridad != null && !prioridad.equals("Normal"))) {
                tarifa = new TarifaPrioritaria(tarifa, chkUrgente.isSelected() ? "urgente" : prioridad);
            }

            double costoFinal = tarifa.calcularCosto();

            lblCostoEnvio.setText(String.format("$%,.2f", costoFinal));
            return costoFinal;

        } catch (NumberFormatException e) {
            lblCostoEnvio.setText("Datos inválidos");
        } catch (Exception e) {
            lblCostoEnvio.setText("Error de cálculo");
            e.printStackTrace(); // Para depuración
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
