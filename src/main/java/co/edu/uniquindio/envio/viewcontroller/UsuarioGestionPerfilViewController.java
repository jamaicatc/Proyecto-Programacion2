package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.EnvioApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioGestionPerfilViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAgregarDireccion;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEditarDireccion;

    @FXML
    private Button btnEliminarDireccion;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private TableView<?> tableDireccionesFrecuentes;

    @FXML
    private TableColumn<?, ?> tcAlias;

    @FXML
    private TableColumn<?, ?> tcCalleCarrera;

    @FXML
    private TableColumn<?, ?> tcCiudad;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void onAgregarDireccion(ActionEvent event) {

    }

    @FXML
    void onCancelar(ActionEvent event) {
        EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
    }

    @FXML
    void onEditarDireccion(ActionEvent event) {

    }

    @FXML
    void onEliminarDireccion(ActionEvent event) {

    }

    @FXML
    void onGuardarCambios(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
