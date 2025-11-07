package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.model.decorator.Tarifa;
import co.edu.uniquindio.envio.model.decorator.TarifaBase;
import co.edu.uniquindio.envio.model.decorator.TarifaFragil;
import co.edu.uniquindio.envio.model.decorator.TarifaPrioritaria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioTarifaViewController {

    ModelFactory modelFactory;
    ObservableList<String> listaPrioridades = FXCollections.observableArrayList("Normal", "Alta", "Urgente");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbPrioridad;

    @FXML
    private CheckBox chkFragil;

    @FXML
    private CheckBox chkUrgente;

    @FXML
    private TextField txtOrigen;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtLargo;

    @FXML
    private TextField txtAncho;

    @FXML
    private TextField txtAlto;

    @FXML
    private Label lblCostoEnvio;

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();
    }

    private void initView() {
        listenerSelection();
        cargarDatos();
    }

    private void listenerSelection() {
        // Listener para calcular la tarifa cuando se cambia un valor
        txtOrigen.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtDestino.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtPeso.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtLargo.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAncho.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        txtAlto.textProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        cmbPrioridad.valueProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        chkFragil.selectedProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
        chkUrgente.selectedProperty().addListener((obs, oldVal, newVal) -> calcularCosto());
    }

    private void cargarDatos() {
        cmbPrioridad.setItems(listaPrioridades);
        cmbPrioridad.setValue("Normal");
    }

    private double calcularCosto() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double largo = Double.parseDouble(txtLargo.getText());
            double ancho = Double.parseDouble(txtAncho.getText());
            double alto = Double.parseDouble(txtAlto.getText());
            double volumen = largo * ancho * alto;
            String origen = txtOrigen.getText();
            String destino = txtDestino.getText();

            Tarifa tarifa = new TarifaBase(peso, volumen, 1, false, origen, destino); // Asumiendo cantidad=1 y no es documento

            if (chkFragil.isSelected()) {
                tarifa = new TarifaFragil(tarifa);
            }

            String prioridad = cmbPrioridad.getValue();
            if (chkUrgente.isSelected() || (prioridad != null && !prioridad.equals("Normal"))) {
                tarifa = new TarifaPrioritaria(tarifa, chkUrgente.isSelected() ? "urgente" : prioridad);
            }

            double costoFinal = tarifa.calcularCosto();

            lblCostoEnvio.setText(String.format("$%,.2f", costoFinal));
            return costoFinal;

        } catch (NumberFormatException e) {
            lblCostoEnvio.setText("Datos inválidos");
        } catch (Exception e) {
            lblCostoEnvio.setText("Error de cálculo");
            e.printStackTrace();
        }
        return 0.0;
    }
}
