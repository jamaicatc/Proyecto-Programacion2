package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import co.edu.uniquindio.envio.EnvioApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UsuarioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onCerrarSesion(ActionEvent event) {
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
    void initialize() {

    }

}
