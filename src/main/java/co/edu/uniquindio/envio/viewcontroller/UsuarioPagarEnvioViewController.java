package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UsuarioPagarEnvioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnPagar;

    @FXML
    private ComboBox<String> cmbEnviosPendientes;

    @FXML
    private ComboBox<String> cmbMetodoPago;

    @FXML
    private TextArea taResultado;

    @FXML
    void onPagar(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
