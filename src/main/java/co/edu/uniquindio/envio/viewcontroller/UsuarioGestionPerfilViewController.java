package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import static co.edu.uniquindio.envio.utils.EmpresaConstantes.*;

public class UsuarioGestionPerfilViewController {

    ModelFactory modelFactory;
    ObservableList<DireccionDto> listaDirecciones = FXCollections.observableArrayList();
    DireccionDto direccionSeleccionada;
    UsuarioDto usuarioActual;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregarDireccion;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditarDireccion;

    @FXML
    private Button btnEliminarDireccion;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private TableView<DireccionDto> tableDireccionesFrecuentes;

    @FXML
    private TableColumn<DireccionDto, String> tcAlias;

    @FXML
    private TableColumn<DireccionDto, String> tcCalleCarrera;

    @FXML
    private TableColumn<DireccionDto, String> tcCiudad;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();
    }

    private void initView() {
        initDataBinding();
        cargarDatosUsuario();
        obtenerDirecciones();
        tableDireccionesFrecuentes.getItems().clear();
        tableDireccionesFrecuentes.setItems(listaDirecciones);
        listenerSelection();
    }

    private void initDataBinding() {
        tcAlias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().alias()));
        tcCalleCarrera.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().calleCarrera()));
        tcCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ciudad()));
    }

    private void cargarDatosUsuario() {
        Object usuarioSesion = modelFactory.getUsuarioActual();
        if (usuarioSesion instanceof Usuario) {
            usuarioActual = modelFactory.getMapper().usuarioToUsuarioDto((Usuario) usuarioSesion);
            if (usuarioActual != null) {
                txtNombre.setText(usuarioActual.nombreCompleto());
                txtCorreo.setText(usuarioActual.correo());
                txtTelefono.setText(usuarioActual.telefono());
            }
        }
    }

    private void obtenerDirecciones() {
        if (usuarioActual != null) {
            listaDirecciones.addAll(modelFactory.obtenerDireccionesUsuario(usuarioActual.idUsuario()));
        }
    }

    private void listenerSelection() {
        tableDireccionesFrecuentes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            direccionSeleccionada = newSelection;
        });
    }

    @FXML
    void onAgregarDireccion(ActionEvent event) {
        TextInputDialog dialogAlias = new TextInputDialog();
        dialogAlias.setTitle("Nueva Dirección");
        dialogAlias.setHeaderText("Ingrese el alias de la dirección:");
        dialogAlias.setContentText("Alias:");

        dialogAlias.showAndWait().ifPresent(alias -> {
            TextInputDialog dialogDireccion = new TextInputDialog();
            dialogDireccion.setTitle("Nueva Dirección");
            dialogDireccion.setHeaderText("Ingrese la dirección:");
            dialogDireccion.setContentText("Calle/Carrera:");

            dialogDireccion.showAndWait().ifPresent(direccion -> {
                TextInputDialog dialogCiudad = new TextInputDialog();
                dialogCiudad.setTitle("Nueva Dirección");
                dialogCiudad.setHeaderText("Ingrese la ciudad:");
                dialogCiudad.setContentText("Ciudad:");

                dialogCiudad.showAndWait().ifPresent(ciudad -> {
                    String idDireccion = "DIR-" + UUID.randomUUID().toString().substring(0, 8);
                    DireccionDto nuevaDireccion = new DireccionDto(
                        idDireccion,
                        alias,
                        direccion,
                        ciudad,
                        "0,0" // Coordenadas por defecto
                    );
                    if (modelFactory.agregarDireccion(usuarioActual.idUsuario(), nuevaDireccion)) {
                        listaDirecciones.add(nuevaDireccion);
                        mostrarMensaje("Dirección agregada", "Notificación", "La dirección se ha agregado correctamente", AlertType.INFORMATION);
                    } else {
                        mostrarMensaje("Error", "Error", "No se pudo agregar la dirección", AlertType.ERROR);
                    }
                });
            });
        });
    }

    @FXML
    void onEditarDireccion(ActionEvent event) {
        if (direccionSeleccionada != null) {
            TextInputDialog dialogDireccion = new TextInputDialog(direccionSeleccionada.calleCarrera());
            dialogDireccion.setTitle("Editar Dirección");
            dialogDireccion.setHeaderText("Ingrese la nueva dirección:");
            dialogDireccion.setContentText("Calle/Carrera:");

            dialogDireccion.showAndWait().ifPresent(nuevaDireccion -> {
                TextInputDialog dialogCiudad = new TextInputDialog(direccionSeleccionada.ciudad());
                dialogCiudad.setTitle("Editar Dirección");
                dialogCiudad.setHeaderText("Ingrese la nueva ciudad:");
                dialogCiudad.setContentText("Ciudad:");

                dialogCiudad.showAndWait().ifPresent(nuevaCiudad -> {
                    DireccionDto direccionActualizada = new DireccionDto(
                        direccionSeleccionada.idDireccion(),
                        direccionSeleccionada.alias(),
                        nuevaDireccion,
                        nuevaCiudad,
                        direccionSeleccionada.coordenadas()
                    );
                    if (modelFactory.actualizarDireccion(usuarioActual.idUsuario(), direccionActualizada)) {
                        actualizarTabla();
                        mostrarMensaje("Dirección actualizada", "Notificación", "La dirección se ha actualizado correctamente", AlertType.INFORMATION);
                    } else {
                        mostrarMensaje("Error", "Error", "No se pudo actualizar la dirección", AlertType.ERROR);
                    }
                });
            });
        } else {
            mostrarMensaje("Selección requerida", "Advertencia", "Por favor seleccione una dirección para editar", AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarDireccion(ActionEvent event) {
        if (direccionSeleccionada != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Está seguro de eliminar esta dirección?");
            alert.setContentText("Esta acción no se puede deshacer");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (modelFactory.eliminarDireccion(usuarioActual.idUsuario(), direccionSeleccionada.alias())) {
                        listaDirecciones.remove(direccionSeleccionada);
                        mostrarMensaje("Dirección eliminada", "Notificación", "La dirección se ha eliminado correctamente", AlertType.INFORMATION);
                    } else {
                        mostrarMensaje("Error", "Error", "No se pudo eliminar la dirección", AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarMensaje("Selección requerida", "Advertencia", "Por favor seleccione una dirección para eliminar", AlertType.WARNING);
        }
    }

    @FXML
    void onGuardarCambios(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarMensaje("Error", "Error", "No se ha podido identificar al usuario actual.", AlertType.ERROR);
            return;
        }

        UsuarioDto usuarioActualizado = new UsuarioDto(
            usuarioActual.idUsuario(),
            txtNombre.getText(),
            txtCorreo.getText(),
            txtTelefono.getText(),
            usuarioActual.usuario(),
            usuarioActual.contrasena()
        );

        if (datosValidos(usuarioActualizado)) {
            if (modelFactory.actualizarUsuario(usuarioActualizado)) {
                mostrarMensaje("Cambios guardados", "Notificación", "Los cambios se han guardado correctamente", AlertType.INFORMATION);
                // Actualizar el usuario en sesión en el ModelFactory
                modelFactory.setUsuarioActual(modelFactory.getMapper().usuarioDtoToUsuario(usuarioActualizado));
                this.usuarioActual = usuarioActualizado;
            } else {
                mostrarMensaje("Error", "Error", "No se pudieron guardar los cambios", AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Datos incompletos", "Advertencia", "Por favor complete todos los campos", AlertType.WARNING);
        }
    }

    @FXML
    void onCancelar(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
    }

    private void actualizarTabla() {
        listaDirecciones.clear();
        obtenerDirecciones();
    }

    private boolean datosValidos(UsuarioDto usuario) {
        // No validamos usuario y contraseña porque no se editan en esta vista
        return usuario != null &&
               usuario.idUsuario() != null && !usuario.idUsuario().isBlank() &&
               usuario.nombreCompleto() != null && !usuario.nombreCompleto().isBlank() &&
               usuario.correo() != null && !usuario.correo().isBlank() &&
               usuario.telefono() != null && !usuario.telefono().isBlank();
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
