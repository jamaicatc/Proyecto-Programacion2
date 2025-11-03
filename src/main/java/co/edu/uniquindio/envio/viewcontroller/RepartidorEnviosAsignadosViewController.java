package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RepartidorEnviosAsignadosViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnMarcarEnRuta;

    @FXML
    private Button btnMarcarEntregado;

    @FXML
    private Button btnVerDetalle;

    @FXML
    private TableView<?> tableEnviosAsignados;

    @FXML
    private TableColumn<?, ?> tcDestino;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcFechaEstimada;

    @FXML
    private TableColumn<?, ?> tcIdEnvio;

    @FXML
    private TableColumn<?, ?> tcOrigen;

    @FXML
    void onMarcarEnRuta(ActionEvent event) {

    }

    @FXML
    void onMarcarEntregado(ActionEvent event) {

    }

    @FXML
    void onVerDetalle(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
