package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class UsuarioTarifaViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCotizar;

    @FXML
    private RadioButton rbtDocumento;

    @FXML
    private RadioButton rbtPaquete;

    @FXML
    private TextField txtAlto;

    @FXML
    private TextField txtAncho;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtLargo;

    @FXML
    private TextField txtOrigen;

    @FXML
    private TextField txtPeso;

    @FXML
    void onCotizar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        cargarDatos();
    }

    private void cargarDatos() {
        btnCotizar.setCursor(Cursor.HAND);
    }

}
