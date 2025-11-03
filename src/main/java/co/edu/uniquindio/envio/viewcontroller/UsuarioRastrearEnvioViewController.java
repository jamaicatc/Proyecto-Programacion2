package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioRastrearEnvioViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnReportarIncidencia;

    @FXML
    private TableView<?> tableHistorial;

    @FXML
    private TableColumn<?, ?> tcEvento;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcObservaciones;

    @FXML
    private TextField txtNumeroSeguimiento;

    @FXML
    void onBuscar(ActionEvent event) {

    }

    @FXML
    void onReportarIncidencia(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
