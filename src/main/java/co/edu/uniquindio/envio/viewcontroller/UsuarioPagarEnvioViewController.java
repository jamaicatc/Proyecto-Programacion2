package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    ObservableList<MetodoPagoDto> listaMetodosPago = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnPagar;

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
    private ComboBox<MetodoPagoDto> cmbMetodoPago;

    @FXML
    void onPagar(ActionEvent event) {
        pagarEnvio();
    }

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();
    }

    private void initView() {
        initDataBinding();
        cargarEnviosPendientes();
        cargarMetodosPago();
        tableEnviosPendientes.setItems(listaEnviosPendientes);
        cmbMetodoPago.setItems(listaMetodosPago);

        tableEnviosPendientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnPagar.setDisable(newSelection == null || newSelection.costo() <= 0);
        });

        btnPagar.setDisable(true);
    }

    private void initDataBinding() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFechaCreacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcFechaEntrega.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaEntrega().toString()));
        tcOrigenDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen() + " - " + cellData.getValue().direccionOrigen()));
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

    private void cargarMetodosPago() {
        listaMetodosPago.clear();
        listaMetodosPago.addAll(modelFactory.getUsuarioServices().obtenerMetodosPago(ID_USUARIO_LOGUEADO));
    }

    private void pagarEnvio() {
        EnvioDto envioSeleccionado = tableEnviosPendientes.getSelectionModel().getSelectedItem();
        MetodoPagoDto metodoPagoSeleccionado = cmbMetodoPago.getSelectionModel().getSelectedItem();

        if (envioSeleccionado == null || metodoPagoSeleccionado == null) {
            mostrarMensaje("Error de Pago", "Debe seleccionar un envío y un método de pago.", "",Alert.AlertType.WARNING);
            return;
        }

        // Obtener el estado actual del envío desde el modelo para verificar si ya fue pagado o no existe
        EnvioDto originalEnvioInModel = modelFactory.getEnvioServices().obtenerEnvioDto(envioSeleccionado.id());

        if (originalEnvioInModel == null) {
            mostrarMensaje("Error de Pago", "El envío seleccionado no se encontró en el sistema.", "", Alert.AlertType.ERROR);
            return;
        }
        if (originalEnvioInModel.pago()) {
            mostrarMensaje("Error de Pago", "El envío seleccionado ya ha sido pagado.", "", Alert.AlertType.WARNING);
            return;
        }

        Factura facturaGenerada = modelFactory.getEnvioServices().pagarEnvio(envioSeleccionado.id(), metodoPagoSeleccionado);

        if (facturaGenerada != null) {
            // Crear un nuevo EnvioDto con la factura actualizada
            EnvioDto envioActualizado = new EnvioDto(
                    envioSeleccionado.id(),
                    envioSeleccionado.fechaCreacion(),
                    envioSeleccionado.fechaEntrega(),
                    envioSeleccionado.direccionOrigen(),
                    envioSeleccionado.direccionDestino(),
                    envioSeleccionado.estadoActual(), // Mantener el estado actual del envío
                    envioSeleccionado.peso(),
                    envioSeleccionado.largo(),
                    envioSeleccionado.ancho(),
                    envioSeleccionado.alto(),
                    envioSeleccionado.costo(),
                    envioSeleccionado.repartidorAsignado(),
                    true, // Marcar como pagado
                    envioSeleccionado.ultimaIncidenciaDescripcion(),
                    modelFactory.getMapper().facturaToFacturaDto(facturaGenerada) // Asignar la factura generada
            );

            // Actualizar el envío en el modelo
            modelFactory.getEnvioServices().actualizarEnvio(envioActualizado);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioComprobantePago.fxml"));
                Parent root = loader.load();

                UsuarioComprobantePagoViewController controller = loader.getController();
                controller.init(facturaGenerada, envioActualizado); // Pasar el envioActualizado

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Comprobante de Pago");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error", "No se pudo cargar la ventana del comprobante.", "", Alert.AlertType.ERROR);
            }

            // Notificar a toda la aplicación que los datos han cambiado.
            ModelFactory.datosActualizadosProperty.set(!ModelFactory.datosActualizadosProperty.get());

            // Recargar la lista de envíos pendientes para reflejar el cambio
            cargarEnviosPendientes();
            tableEnviosPendientes.getSelectionModel().clearSelection();
        } else {
            // Este bloque else solo debería alcanzarse si hay un problema inesperado
            mostrarMensaje("Error de Pago", "No se pudo procesar el pago por una razón desconocida.", "", Alert.AlertType.ERROR);
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
