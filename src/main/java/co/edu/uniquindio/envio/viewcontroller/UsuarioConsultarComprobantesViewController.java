package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class UsuarioConsultarComprobantesViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnVerDetalle;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextArea taDetalle;

    @FXML
    private TableView<?> tableComprobantes;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcIdPago;

    @FXML
    private TableColumn<?, ?> tcMetodo;

    @FXML
    private TableColumn<?, ?> tcMonto;

    @FXML
    private TableColumn<?, ?> tcResultado;

    @FXML
    void onFiltrar(ActionEvent event) {

    }

    @FXML
    void onVerDetalle(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
