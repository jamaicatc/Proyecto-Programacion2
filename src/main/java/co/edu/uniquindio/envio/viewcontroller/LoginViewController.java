package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.controller.AdministradorController;
import co.edu.uniquindio.envio.controller.UsuarioController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;

public class LoginViewController {

    ObservableList<String> listaOpciones = FXCollections.observableArrayList("Usuario", "Administrador");


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnIngresar;

    @FXML
    private ComboBox<String> comboRolUsuario;

    @FXML
    private PasswordField txtIngresarContrasena;

    @FXML
    private TextField txtIngresarUsuario;

    @FXML
    void OnCancelar(ActionEvent event) {

    }

    @FXML
    void OnIngresar(ActionEvent event) {
        String usuario = txtIngresarUsuario.getText();
        String contrasena = txtIngresarContrasena.getText();
        String rol = comboRolUsuario.getValue();

        if (AdministradorController.validarCredencialesAdministrador(usuario, contrasena, rol)) {
            EnvioApplication.mainStage.setScene(EnvioApplication.sceneAdministrador);
            EnvioApplication.mainStage.setTitle("Panel Administrador");
            txtIngresarContrasena.clear();
            comboRolUsuario.getSelectionModel().clearSelection();
        } else if (UsuarioController.validarCredencialesUsuario(usuario, contrasena, rol)){
            EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
            EnvioApplication.mainStage.setTitle("Panel Usuario");
        } else {
            mostrarMensaje("Error de autenticación", null, "Usuario, contraseña o rol incorrectos", Alert.AlertType.ERROR);
        }

    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    @FXML
    void initialize() {
        cargarDatos();
    }

    private void cargarDatos() {
        comboRolUsuario.setItems(listaOpciones);
        btnIngresar.setCursor(Cursor.HAND);
    }


}
