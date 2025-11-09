package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.controller.UsuarioController;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioGestionarMetodosPagoViewController {

    private final UsuarioController usuarioController = new UsuarioController();
    private UsuarioDto usuarioActual;
    private MetodoPagoDto metodoPagoSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cmbTipoPago;

    @FXML
    private TableView<MetodoPagoDto> tableMetodosPago;

    @FXML
    private TableColumn<MetodoPagoDto, String> tcEstado; // Mapeado a 'alias'
    @FXML
    private TableColumn<MetodoPagoDto, String> tcNumeroReferencia; // Mapeado a 'numero'
    @FXML
    private TableColumn<MetodoPagoDto, Double> tcSaldo; // No hay campo directo en MetodoPagoDto
    @FXML
    private TableColumn<MetodoPagoDto, String> tcTipo; // Mapeado a 'tipo'

    @FXML
    private TextField txtReferencia;
    @FXML
    private TextField txtSaldo; // Este campo no se usará directamente para MetodoPagoDto

    @FXML
    void onAgregar(ActionEvent event) {
        limpiarCampos();
        activarCampos(true);
        btnGuardar.setDisable(false);
        btnAgregar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        metodoPagoSeleccionado = null; // Para indicar que es un nuevo método de pago
    }

    @FXML
    void onCancelar(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
        limpiarCampos();
        activarCampos(false);
        btnGuardar.setDisable(true);
        btnAgregar.setDisable(false);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        tableMetodosPago.getSelectionModel().clearSelection();
        metodoPagoSeleccionado = null;
    }

    @FXML
    void onEditar(ActionEvent event) {
        if (metodoPagoSeleccionado != null) {
            activarCampos(true);
            btnGuardar.setDisable(false);
            btnAgregar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Selección de Método de Pago", "No se ha seleccionado ningún método de pago", "Por favor, selecciona un método de pago de la tabla para editar.");
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (metodoPagoSeleccionado != null) {
            boolean eliminado = usuarioController.eliminarMetodoPago(usuarioActual.idUsuario(), metodoPagoSeleccionado.alias());
            if (eliminado) {
                mostrarMensaje(Alert.AlertType.INFORMATION, "Eliminar Método de Pago", "Método de pago eliminado", "El método de pago ha sido eliminado exitosamente.");
                refrescarTablaMetodosPago();
                limpiarCampos();
                activarCampos(false);
                btnGuardar.setDisable(true);
                btnAgregar.setDisable(false);
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Eliminar Método de Pago", "Error al eliminar método de pago", "No se pudo eliminar el método de pago.");
            }
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Selección de Método de Pago", "No se ha seleccionado ningún método de pago", "Por favor, selecciona un método de pago de la tabla para eliminar.");
        }
    }

    @FXML
    void onGuardar(ActionEvent event) {
        String numero = txtReferencia.getText();
        String tipo = cmbTipoPago.getValue();

        if (numero.isEmpty() || tipo == null) {
            mostrarMensaje(Alert.AlertType.ERROR, "Datos incompletos", "Por favor, completa todos los campos.", "El número/referencia y el tipo son obligatorios.");
            return;
        }

        boolean resultado;
        if (metodoPagoSeleccionado == null) { // Es un nuevo método de pago
            String aliasGenerado = tipo + " - " + numero;
            MetodoPagoDto nuevoMetodoPago = new MetodoPagoDto(aliasGenerado, numero, tipo);
            resultado = usuarioController.agregarMetodoPago(usuarioActual.idUsuario(), nuevoMetodoPago);
            if (resultado) {
                mostrarMensaje(Alert.AlertType.INFORMATION, "Agregar Método de Pago", "Método de pago agregado", "El nuevo método de pago ha sido agregado exitosamente.");
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Agregar Método de Pago", "Error al agregar método de pago", "Ya existe un método de pago con este alias o hubo un error.");
            }
        } else { // Es una edición
            MetodoPagoDto metodoPagoActualizado = new MetodoPagoDto(metodoPagoSeleccionado.alias(), numero, tipo);
            resultado = usuarioController.actualizarMetodoPago(usuarioActual.idUsuario(), metodoPagoActualizado);
            if (resultado) {
                mostrarMensaje(Alert.AlertType.INFORMATION, "Actualizar Método de Pago", "Método de pago actualizado", "El método de pago ha sido actualizado exitosamente.");
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Actualizar Método de Pago", "Error al actualizar método de pago", "No se pudo actualizar el método de pago.");
            }
        }

        if (resultado) {
            refrescarTablaMetodosPago();
            limpiarCampos();
            activarCampos(false);
            btnGuardar.setDisable(true);
            btnAgregar.setDisable(false);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        usuarioActual = usuarioController.obtenerUsuarioPorNombre("Juan David");

        if (usuarioActual != null) {
            // Configurar las columnas de la tabla usando Callbacks para Java Records
            tcTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
            tcNumeroReferencia.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
            tcEstado.setCellValueFactory(cellData -> cellData.getValue().aliasProperty());

            // Inicializar ComboBox de tipos de pago
            cmbTipoPago.setItems(FXCollections.observableArrayList(Arrays.asList("Tarjeta de Crédito", "Tarjeta de Débito", "PSE", "PayPal", "Efectivo")));

            // Cargar los métodos de pago en la tabla
            refrescarTablaMetodosPago();

            // Configurar listener para la selección de la tabla
            tableMetodosPago.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                metodoPagoSeleccionado = newSelection;
                if (newSelection != null) {
                    cargarMetodoPagoEnCampos(newSelection);
                    activarCampos(false);
                    btnAgregar.setDisable(false);
                    btnEditar.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnGuardar.setDisable(true);
                } else {
                    limpiarCampos();
                    activarCampos(false);
                    btnAgregar.setDisable(false);
                    btnEditar.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnGuardar.setDisable(true);
                }
            });

            // Desactivar campos y botones inicialmente
            activarCampos(false);
            btnGuardar.setDisable(true);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
        } else {
            mostrarMensaje(Alert.AlertType.ERROR, "Error de Carga", "Usuario no encontrado", "No se pudo cargar el usuario 'Juan David'.");
        }
    }

    private void refrescarTablaMetodosPago() {
        if (usuarioActual != null) {
            ObservableList<MetodoPagoDto> metodosPago = FXCollections.observableArrayList(usuarioController.obtenerMetodosPago(usuarioActual.idUsuario()));
            tableMetodosPago.setItems(metodosPago);
        }
    }

    private void cargarMetodoPagoEnCampos(MetodoPagoDto metodoPago) {
        txtReferencia.setText(metodoPago.numero());
        cmbTipoPago.setValue(metodoPago.tipo());
        txtSaldo.clear();
    }

    private void limpiarCampos() {
        txtReferencia.clear();
        cmbTipoPago.getSelectionModel().clearSelection();
        txtSaldo.clear();
    }

    private void activarCampos(boolean activar) {
        txtReferencia.setEditable(activar);
        cmbTipoPago.setDisable(!activar);
        txtSaldo.setEditable(false);
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
