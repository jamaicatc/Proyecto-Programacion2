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
import javafx.scene.layout.AnchorPane;

public class UsuarioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainContentPane;

    @FXML
    void onCerrarSesion(ActionEvent event) {
        ModelFactory.getInstance().setUsuarioActual(null);
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneLogin);
    }

    @FXML
    void onGestionarMetodosPago(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuarioGestionarMetodosPago);
    }

    @FXML
    void onGestionarPerfil(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuarioGestionPerfil);
    }

    @FXML
    void onVerHistorialEnvios(ActionEvent event) {
        Usuario usuarioLogueado = (Usuario) ModelFactory.getInstance().getUsuarioActual();
        if (usuarioLogueado == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No se pudo cargar el historial de envíos. Por favor, inicie sesión nuevamente.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/usuario/UsuarioHistorialEnvios.fxml"));
            Parent historialEnviosView = loader.load();
            UsuarioHistorialEnviosViewController historialController = loader.getController();

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
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}