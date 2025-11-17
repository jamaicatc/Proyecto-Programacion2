package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.factory.pago.*;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.FacturaDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.model.Factura;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UsuarioPagarEnvioViewController {

    private static final String ID_USUARIO_LOGUEADO = "2245"; // Simulación de usuario logueado

    ModelFactory modelFactory;
    ObservableList<EnvioDto> listaEnviosPendientes = FXCollections.observableArrayList();
    private Pago pagoSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnRecargar;

    @FXML
    private Button btnActualizarTabla; // New button

    @FXML
    private TableView<EnvioDto> tableEnviosPendientes;

    @FXML
    private TableColumn<EnvioDto, String> tcIdEnvio;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaCreacion;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaEntrega;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigenDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcEstado;

    @FXML
    private TableColumn<EnvioDto, String> tcPeso;

    @FXML
    private TableColumn<EnvioDto, String> tcVolumen;

    @FXML
    private TableColumn<EnvioDto, String> tcCosto;

    @FXML
    private ComboBox<String> cmbMetodoPago;

    @FXML
    private TextField txtMontoRecarga;

    @FXML
    private Label lblSaldo;

    @FXML
    void onPagar(ActionEvent event) {
        pagarEnvio();
    }

    @FXML
    void onRecargar(ActionEvent event) {
        recargarSaldo();
    }

    @FXML
    void onActualizarTabla(ActionEvent event) { // New method
        cargarEnviosPendientes();
        tableEnviosPendientes.getSelectionModel().clearSelection();
        actualizarEstadoControles();
    }

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();
    }

    private void initView() {
        initDataBinding();
        cargarEnviosPendientes();
        tableEnviosPendientes.setItems(listaEnviosPendientes);
        cmbMetodoPago.setItems(FXCollections.observableArrayList("Tarjeta", "PSE", "Efectivo"));

        cmbMetodoPago.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                seleccionarMetodoPago(newSelection);
            }
        });

        tableEnviosPendientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            actualizarEstadoControles();
        });

        actualizarEstadoControles();
    }

    private void initDataBinding() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFechaCreacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcFechaEntrega.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaEntrega().toString()));
        tcOrigenDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen() + " - " + cellData.getValue().direccionDestino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
        tcPeso.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f kg", cellData.getValue().peso())));
        tcVolumen.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.0fx%.0fx%.0f cm", cellData.getValue().largo(), cellData.getValue().ancho(), cellData.getValue().alto())));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%,.2f", cellData.getValue().costo())));
    }

    private void cargarEnviosPendientes() {
        listaEnviosPendientes.clear();
        listaEnviosPendientes.addAll(
                modelFactory.getEnvioServices().obtenerEnvios(ID_USUARIO_LOGUEADO).stream()
                        .filter(envio -> !envio.pago() &&
                                (envio.estadoActual().equalsIgnoreCase("Solicitado") ||
                                 envio.estadoActual().equalsIgnoreCase("En Bodega")))
                        .collect(Collectors.toList())
        );
    }

    private void seleccionarMetodoPago(String tipoPago) {
        switch (tipoPago) {
            case "Tarjeta":
                pagoSeleccionado = modelFactory.getPagoTarjeta();
                break;
            case "PSE":
                pagoSeleccionado = modelFactory.getPagoPSE();
                break;
            case "Efectivo":
                pagoSeleccionado = modelFactory.getPagoEfectivo();
                break;
            default:
                pagoSeleccionado = null;
                break;
        }
        actualizarSaldoLabel();
        actualizarEstadoControles();
    }

    private void pagarEnvio() {
        EnvioDto envioSeleccionado = tableEnviosPendientes.getSelectionModel().getSelectedItem();
        String tipoPagoSeleccionado = cmbMetodoPago.getSelectionModel().getSelectedItem();

        if (envioSeleccionado == null || pagoSeleccionado == null || tipoPagoSeleccionado == null) {
            mostrarMensaje("Error de Pago", "Debe seleccionar un envío y un método de pago.", "", Alert.AlertType.WARNING);
            return;
        }

        if (!pagoSeleccionado.pagar(envioSeleccionado.costo())) {
            mostrarMensaje("Saldo Insuficiente", "El saldo de su método de pago no es suficiente.", "Por favor, recargue saldo.", Alert.AlertType.WARNING);
            txtMontoRecarga.setDisable(false);
            btnRecargar.setDisable(false);
            return;
        }

        String alias = "Pago con " + tipoPagoSeleccionado;
        String numero = "N/A";
        MetodoPagoDto metodoPagoDtoParaFactura = new MetodoPagoDto(alias, numero, tipoPagoSeleccionado);

        Factura facturaGenerada = modelFactory.getEnvioServices().pagarEnvio(envioSeleccionado.id(), metodoPagoDtoParaFactura);

        if (facturaGenerada != null) {
            mostrarComprobante(facturaGenerada, envioSeleccionado);
            cargarEnviosPendientes();
            tableEnviosPendientes.getSelectionModel().clearSelection();
            actualizarSaldoLabel();
            ModelFactory.datosActualizadosProperty.set(!ModelFactory.datosActualizadosProperty.get()); // Notify data change
        } else {
            mostrarMensaje("Error de Pago", "No se pudo procesar el pago por una razón desconocida.", "", Alert.AlertType.ERROR);
        }
    }

    private void recargarSaldo() {
        try {
            double monto = Double.parseDouble(txtMontoRecarga.getText());
            if (monto > 0) {
                pagoSeleccionado.recargar(monto);
                actualizarSaldoLabel();
                txtMontoRecarga.clear();
                mostrarMensaje("Recarga Exitosa", "El saldo ha sido actualizado.", "", Alert.AlertType.INFORMATION);
                ModelFactory.datosActualizadosProperty.set(!ModelFactory.datosActualizadosProperty.get()); // Notify data change
            } else {
                mostrarMensaje("Error de Recarga", "El monto debe ser positivo.", "", Alert.AlertType.WARNING);
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error de Recarga", "Por favor, ingrese un monto válido.", "", Alert.AlertType.WARNING);
        }
        actualizarEstadoControles();
    }

    private void actualizarEstadoControles() {
        boolean envioSeleccionado = tableEnviosPendientes.getSelectionModel().getSelectedItem() != null;
        boolean metodoPagoSeleccionado = pagoSeleccionado != null;
        boolean esEfectivo = pagoSeleccionado instanceof PagoEfectivo;

        btnPagar.setDisable(!envioSeleccionado || !metodoPagoSeleccionado);
        txtMontoRecarga.setDisable(esEfectivo || !metodoPagoSeleccionado);
        btnRecargar.setDisable(esEfectivo || !metodoPagoSeleccionado);
    }

    private void actualizarSaldoLabel() {
        if (pagoSeleccionado != null) {
            if (pagoSeleccionado instanceof PagoEfectivo) {
                lblSaldo.setText("Saldo: N/A");
            } else {
                lblSaldo.setText(String.format("Saldo: $%,.2f", pagoSeleccionado.getSaldo()));
            }
        } else {
            lblSaldo.setText("Saldo: ");
        }
    }

    private void mostrarComprobante(Factura factura, EnvioDto envio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioComprobantePago.fxml"));
            Parent root = loader.load();

            UsuarioComprobantePagoViewController controller = loader.getController();
            controller.init(factura, envio);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Comprobante de Pago");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error", "No se pudo cargar la ventana del comprobante.", "", Alert.AlertType.ERROR);
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}
