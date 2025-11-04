package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DetalleEnvioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCerrar;

    @FXML
    private TextField txtCostoTotal;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtDimensiones;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtFechaCreacion;

    @FXML
    private TextField txtFechaEstimada;

    @FXML
    private TextField txtIdEnvio;

    @FXML
    private TextField txtOrigen;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtRepartidor;

    @FXML
    private TextField txtUsuario;

    @FXML
    void onCerrar(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
