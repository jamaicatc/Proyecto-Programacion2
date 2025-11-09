package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.controller.AdministradorController;
import co.edu.uniquindio.envio.controller.RepartidorController;
import co.edu.uniquindio.envio.controller.UsuarioController;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

import static co.edu.uniquindio.envio.utils.EmpresaConstantes.BODY_AUTENTICACION_INCORRECTA;
import static co.edu.uniquindio.envio.utils.EmpresaConstantes.HEADER_ERROR;
import static co.edu.uniquindio.envio.utils.EmpresaConstantes.TITULO_ERROR_AUTENTICACION;

public class LoginViewController {

    private final ObservableList<String> listaOpciones = FXCollections.observableArrayList("Usuario", "Administrador", "Repartidor");
    private final UsuarioController usuarioController = new UsuarioController();
    private final RepartidorController repartidorController = new RepartidorController();
    private final AdministradorController administradorController = new AdministradorController();
    private final ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnIngresar;

    @FXML
    private ComboBox<String> cmbRol;

    @FXML
    private PasswordField txtIngresarContrasena;

    @FXML
    private TextField txtIngresarUsuario;

    @FXML
    void OnCancelar(ActionEvent event) {
        // Lógica para cerrar la aplicación o limpiar campos
    }

    @FXML
    void initialize() {
        cmbRol.setItems(listaOpciones);
        btnIngresar.setCursor(Cursor.HAND);
    }

    @FXML
    void OnIngresar(ActionEvent event) {
        String usuario = txtIngresarUsuario.getText();
        String contrasena = txtIngresarContrasena.getText();
        String rol = cmbRol.getValue();

        if (usuario.isEmpty() || contrasena.isEmpty() || rol == null) {
            mostrarMensaje("Error de Validación", "Campos incompletos", "Por favor, ingrese usuario, contraseña y seleccione un rol.", AlertType.WARNING);
            return;
        }

        boolean loginExitoso = false;

        switch (rol) {
            case "Usuario":
                if (usuarioController.validarCredencialesUsuario(usuario, contrasena)) {
                    Usuario usuarioLogueado = modelFactory.obtenerUsuarioPorCredenciales(usuario, contrasena);
                    modelFactory.setUsuarioActual(usuarioLogueado);
                    EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
                    EnvioApplication.mainStage.setTitle("Panel Usuario");
                    loginExitoso = true;
                }
                break;
            case "Repartidor":
                if (repartidorController.validarCredencialesRepartidor(usuario, contrasena)) {
                    EnvioApplication.mainStage.setScene(EnvioApplication.sceneRepartidor);
                    EnvioApplication.mainStage.setTitle("Panel Repartidor");
                    loginExitoso = true;
                }
                break;
            case "Administrador":
                if (administradorController.validarCredencialesAdministrador(usuario, contrasena)) {
                    EnvioApplication.mainStage.setScene(EnvioApplication.sceneAdministrador);
                    EnvioApplication.mainStage.setTitle("Panel Administrador");
                    loginExitoso = true;
                }
                break;
        }

        if (loginExitoso) {
            limpiarCampos();
            // Notificar a todos los listeners (incluido RepartidorEnviosAsignadosViewController)
            // que los datos han cambiado (porque hay un nuevo usuario en sesión).
            modelFactory.notifyDataChanged();
        } else {
            mostrarMensaje(TITULO_ERROR_AUTENTICACION, HEADER_ERROR, BODY_AUTENTICACION_INCORRECTA, AlertType.ERROR);
            txtIngresarContrasena.clear();
        }
    }

    private void limpiarCampos() {
        txtIngresarUsuario.clear();
        txtIngresarContrasena.clear();
        cmbRol.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
