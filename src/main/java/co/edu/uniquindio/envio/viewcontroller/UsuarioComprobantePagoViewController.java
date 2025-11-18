package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.Factura;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UsuarioComprobantePagoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCerrar;

    @FXML
    private Label lblCosto;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblFechaCreacion;

    @FXML
    private Label lblFechaEntrega;

    @FXML
    private Label lblFechaHora;

    @FXML
    private Label lblIdEnvio;

    @FXML
    private Label lblMetodoPago;

    @FXML
    private Label lblOrigenDestino;

    @FXML
    private Label lblPeso;

    @FXML
    private Label lblVolumen;

    @FXML
    private Text txtMensajeFinal;

    @FXML
    void onCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        // No es necesario inicializar nada aquí, los datos se pasan a través del método init
    }

    public void init(Factura factura, EnvioDto envio) {
        if (factura != null && envio != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Datos del comprobante
            lblFechaHora.setText(factura.getFecha().format(formatter));
            lblMetodoPago.setText(factura.getMetodoPago().getTipo() + " (" + factura.getMetodoPago().getAlias() + ")");

            // Datos del envío
            lblIdEnvio.setText(envio.id());
            lblFechaCreacion.setText(envio.fechaCreacion().toString());
            lblFechaEntrega.setText(envio.fechaEntrega().toString());
            lblOrigenDestino.setText(envio.direccionOrigen() + " - " + envio.direccionDestino()); // Corrected line
            lblEstado.setText(envio.estadoActual());
            lblPeso.setText(String.format("%.2f kg", envio.peso()));
            lblVolumen.setText(String.format("%.0fx%.0fx%.0f cm", envio.largo(), envio.ancho(), envio.alto()));
            lblCosto.setText(String.format("$%,.2f", envio.costo()));
        }
    }
}
