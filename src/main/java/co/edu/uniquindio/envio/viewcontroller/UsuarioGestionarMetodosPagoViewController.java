package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.EnvioApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioGestionarMetodosPagoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<?> cmbTipoPago;

    @FXML
    private TableView<?> tableMetodosPago;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcNumeroReferencia;

    @FXML
    private TableColumn<?, ?> tcSaldo;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtSaldo;

    @FXML
    void onAgregar(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
    }

    @FXML
    void onEditar(ActionEvent event) {

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void onGuardar(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
