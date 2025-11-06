package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    }

    public void init(String nombreUsuario, EnvioDto envioDto) {
        if (envioDto != null) {
            txtIdEnvio.setText(envioDto.idEnvio());
            txtUsuario.setText(nombreUsuario);
            txtOrigen.setText(envioDto.origen());
            txtDestino.setText(envioDto.destino());
            txtPeso.setText(String.format("%.2f kg", envioDto.peso()));
            txtDimensiones.setText(String.format("%.0fx%.0fx%.0f cm", envioDto.largo(), envioDto.ancho(), envioDto.alto()));
            txtCostoTotal.setText(String.format("$%,.2f", envioDto.costo()));
            txtEstado.setText(envioDto.estado());
            txtFechaCreacion.setText(envioDto.fecha().toString());
            txtFechaEstimada.setText(envioDto.fechaEntregaEstimada().toString());

            // Campo de repartidor (a implementar en el futuro)
            txtRepartidor.setText("No asignado");
        }
    }
}
