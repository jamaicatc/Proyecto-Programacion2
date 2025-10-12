package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RepartidorViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<?> tableUsuario;

    @FXML
    private TableColumn<?, ?> tcCorreo;

    @FXML
    private TableColumn<?, ?> tcIdUsuario;

    @FXML
    private TableColumn<?, ?> tcNombreCompleto;

    @FXML
    private TableColumn<?, ?> tcTelefono;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtIdRepartidor;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void onActualizarRepartidor(ActionEvent event) {

    }

    @FXML
    void onAgregarRepartidor(ActionEvent event) {

    }

    @FXML
    void onEliminarUsuario(ActionEvent event) {

    }

    @FXML
    void onNuevoRepartidor(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}