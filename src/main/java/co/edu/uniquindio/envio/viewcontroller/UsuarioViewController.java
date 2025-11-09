package co.edu.uniquindio.envio.viewcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane; // Asumiendo AnchorPane como un contenedor común

public class UsuarioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainContentPane; // Asumiendo que este panel existe en Usuario.fxml para cargar sub-vistas

    @FXML
    void onCerrarSesion(ActionEvent event) {
        // Limpiar el usuario logueado de ModelFactory al cerrar sesión
        ModelFactory.getInstance().setUsuarioActual(null);
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneLogin);
    }

    @FXML
    void onGestionarMetodosPago(ActionEvent event) {
        // Esto podría necesitar cargarse en mainContentPane o cambiar de escena
        // Por ahora, se mantiene el comportamiento original si es un cambio de escena completo
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuarioGestionarMetodosPago);
    }

    @FXML
    void onGestionarPerfil(ActionEvent event) {
        // Esto podría necesitar cargarse en mainContentPane o cambiar de escena
        // Por ahora, se mantiene el comportamiento original si es un cambio de escena completo
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuarioGestionPerfil);
    }

    @FXML
    void onVerHistorialEnvios(ActionEvent event) {
        Usuario usuarioLogueado = (Usuario) ModelFactory.getInstance().getUsuarioActual();
        if (usuarioLogueado == null) {
            // Esto idealmente no debería ocurrir si el login se maneja correctamente, pero como respaldo
            mostrarMensaje("Error", "Usuario no logueado", "No se pudo cargar el historial de envíos. Por favor, inicie sesión nuevamente.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioHistorialEnvios.fxml"));
            Parent historialEnviosView = loader.load();
            UsuarioHistorialEnviosViewController historialController = loader.getController();

            // Limpiar contenido previo y cargar nuevo contenido
            mainContentPane.getChildren().setAll(historialEnviosView);
            AnchorPane.setTopAnchor(historialEnviosView, 0.0);
            AnchorPane.setBottomAnchor(historialEnviosView, 0.0);
            AnchorPane.setLeftAnchor(historialEnviosView, 0.0);
            AnchorPane.setRightAnchor(historialEnviosView, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error", "Error al cargar vista", "No se pudo cargar la vista de historial de envíos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void initialize() {
        // Lógica de inicialización para UsuarioViewController en sí
        // No es necesario cargar el historial de envíos aquí, se cargará al hacer clic en el botón
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}