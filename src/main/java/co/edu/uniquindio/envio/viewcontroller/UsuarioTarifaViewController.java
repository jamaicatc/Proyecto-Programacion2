package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.model.decorator.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import java.text.DecimalFormat;

public class UsuarioTarifaViewController {
    ObservableList<String> listaPrioridades = FXCollections.observableArrayList("Normal", "Alta", "Urgente");

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button btnCotizar;
    @FXML private CheckBox chkFragil;
    @FXML private CheckBox chkUrgente;
    @FXML private ComboBox<String> cmbPrioridad;
    @FXML private RadioButton rbtDocumento;
    @FXML private RadioButton rbtPaquete;
    @FXML private TextArea taResultado;
    @FXML private TextField txtAlto;
    @FXML private TextField txtAncho;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtDestino;
    @FXML private TextField txtLargo;
    @FXML private TextField txtOrigen;
    @FXML private TextField txtPeso;

    @FXML
    void onCotizar(ActionEvent event) {
        try {
            // Validar campos obligatorios
            if (!validarCampos()) {
                mostrarError("Por favor complete todos los campos obligatorios.");
                return;
            }

            // Obtener valores básicos
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = calcularVolumen();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            boolean esDocumento = rbtDocumento.isSelected();
            String origen = txtOrigen.getText();
            String destino = txtDestino.getText();

            // Crear tarifa base
            Tarifa tarifa = new TarifaBase(peso, volumen, cantidad, esDocumento, origen, destino);

            // Aplicar decoradores según las selecciones
            if (chkFragil.isSelected()) {
                tarifa = new TarifaFragil(tarifa);
            }

            String prioridad = cmbPrioridad.getValue();
            if (prioridad != null && !prioridad.equals("Normal")) {
                tarifa = new TarifaPrioritaria(tarifa, prioridad);
            }

            // Calcular costo final
            double costoFinal = tarifa.calcularCosto();

            // Mostrar resultado
            DecimalFormat df = new DecimalFormat("#,###.##");
            StringBuilder resultado = new StringBuilder();
            resultado.append("=== Detalles de la Cotización ===\n");
            resultado.append("Tipo: ").append(esDocumento ? "Documento" : "Paquete").append("\n");
            resultado.append("Origen: ").append(origen).append("\n");
            resultado.append("Destino: ").append(destino).append("\n");
            resultado.append("Peso: ").append(peso).append(" kg\n");
            if (!esDocumento) {
                resultado.append("Volumen: ").append(String.format("%.2f", volumen)).append(" cm³\n");
            }
            resultado.append("Cantidad: ").append(cantidad).append("\n");
            resultado.append("Prioridad: ").append(prioridad).append("\n");
            resultado.append("Servicios adicionales:\n");
            if (chkFragil.isSelected()) resultado.append("- Manejo de material frágil\n");
            resultado.append("\nCosto total: $").append(df.format(costoFinal));

            taResultado.setText(resultado.toString());

        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese valores numéricos válidos.");
        } catch (Exception e) {
            mostrarError("Error al calcular la tarifa: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtOrigen.getText().trim().isEmpty() || txtDestino.getText().trim().isEmpty()) {
            return false;
        }
        if (!rbtDocumento.isSelected() && !rbtPaquete.isSelected()) {
            return false;
        }
        if (txtPeso.getText().trim().isEmpty() || txtCantidad.getText().trim().isEmpty()) {
            return false;
        }
        if (rbtPaquete.isSelected()) {
            if (txtAlto.getText().trim().isEmpty() ||
                txtAncho.getText().trim().isEmpty() ||
                txtLargo.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private double calcularVolumen() {
        if (rbtDocumento.isSelected()) return 0;
        double alto = Double.parseDouble(txtAlto.getText());
        double ancho = Double.parseDouble(txtAncho.getText());
        double largo = Double.parseDouble(txtLargo.getText());
        return alto * ancho * largo;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        cargarDatos();
        configurarControles();
    }

    private void cargarDatos() {
        cmbPrioridad.setItems(listaPrioridades);
        cmbPrioridad.setValue("Normal");
    }

    private void configurarControles() {
        // Configurar grupo de radio buttons
        ToggleGroup grupoTipo = new ToggleGroup();
        rbtDocumento.setToggleGroup(grupoTipo);
        rbtPaquete.setToggleGroup(grupoTipo);

        // Habilitar/deshabilitar campos de dimensiones según tipo
        rbtDocumento.setOnAction(e -> habilitarCamposDimensiones(false));
        rbtPaquete.setOnAction(e -> habilitarCamposDimensiones(true));

        // Configurar validación de entrada numérica
        configurarValidacionNumerica(txtPeso);
        configurarValidacionNumerica(txtAlto);
        configurarValidacionNumerica(txtAncho);
        configurarValidacionNumerica(txtLargo);
        configurarValidacionNumerica(txtCantidad);
    }

    private void habilitarCamposDimensiones(boolean habilitar) {
        txtAlto.setDisable(!habilitar);
        txtAncho.setDisable(!habilitar);
        txtLargo.setDisable(!habilitar);
        if (!habilitar) {
            txtAlto.clear();
            txtAncho.clear();
            txtLargo.clear();
        }
    }

    private void configurarValidacionNumerica(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                textField.setText(oldValue);
            }
        });
    }
}
