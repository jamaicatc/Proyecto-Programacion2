package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.EnvioApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RepartidorViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onCerrarSesion(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneLogin);
    }

    @FXML
    void initialize() {

    }

}
