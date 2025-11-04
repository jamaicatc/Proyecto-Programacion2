package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UsuarioTarifaViewController {
    ObservableList<String> listaPrioridades = FXCollections.observableArrayList("Normal", "Alta", "Urgente");


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCotizar;

    @FXML
    private CheckBox chkFragil;

    @FXML
    private CheckBox chkUrgente;

    @FXML
    private ComboBox<String> cmbPrioridad;

    @FXML
    private RadioButton rbtDocumento;

    @FXML
    private RadioButton rbtPaquete;

    @FXML
    private TextArea taResultado;

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
        cmbPrioridad.setItems(listaPrioridades);
    }

}
