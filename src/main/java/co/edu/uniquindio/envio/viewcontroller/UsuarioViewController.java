package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioViewController {

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
    private TextField txtCorreo;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtTelefono;

    @FXML
    void onActualizarUsuario(ActionEvent event) {

    }

    @FXML
    void onAgregarUsuario(ActionEvent event) {

    }

    @FXML
    void onEliminarUsuario(ActionEvent event) {

    }

    @FXML
    void onNuevoUsuario(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}

