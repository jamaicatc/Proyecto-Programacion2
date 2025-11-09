package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UsuarioConsultarComprobantesViewController {

    private static final String ID_USUARIO_LOGUEADO = "2245"; // Simulación de usuario logueado

    ModelFactory modelFactory;
    ObservableList<EnvioDto> listaComprobantes = FXCollections.observableArrayList();
    FilteredList<EnvioDto> listaComprobantesFiltrada;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFiltrar;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TableView<EnvioDto> tableComprobantes;

    @FXML
    private TableColumn<EnvioDto, String> tcFecha;

    @FXML
    private TableColumn<EnvioDto, String> tcIdPago;

    @FXML
    private TableColumn<EnvioDto, String> tcMetodo;

    @FXML
    private TableColumn<EnvioDto, String> tcMonto;

    @FXML
    private TableColumn<EnvioDto, String> tcResultado;

    @FXML
    void onFiltrar(ActionEvent event) {
        // El predicado se actualiza para reflejar las nuevas fechas
        listaComprobantesFiltrada.setPredicate(this::esComprobanteValido);
    }

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();
        // Escuchar la señal de actualización de datos.
        ModelFactory.datosActualizadosProperty.addListener((obs, oldValue, newValue) -> {
            // Cuando la señal se activa, recargar los comprobantes.
            cargarComprobantes();
        });
    }

    private void initView() {
        initDataBinding();
        cargarComprobantes();
        // Envolver la lista de comprobantes en una FilteredList
        listaComprobantesFiltrada = new FilteredList<>(listaComprobantes, this::esComprobanteValido);
        tableComprobantes.setItems(listaComprobantesFiltrada);
    }

    private void initDataBinding() {
        tcIdPago.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factura().idFactura()));
        tcMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%,.2f", cellData.getValue().factura().monto())));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factura().fecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        tcMetodo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factura().metodoPago().tipo()));
        tcResultado.setCellValueFactory(cellData -> new SimpleStringProperty("Exitoso"));
    }

    private void cargarComprobantes() {
        listaComprobantes.clear();
        listaComprobantes.addAll(
                modelFactory.getEnvioServices().obtenerEnvios(ID_USUARIO_LOGUEADO).stream()
                        .filter(envio -> envio.pago() && envio.factura() != null)
                        .collect(Collectors.toList())
        );
    }

    private boolean esComprobanteValido(EnvioDto envio) {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        // El envío debe ser un comprobante válido (pagado y con factura)
        if (!envio.pago() || envio.factura() == null) {
            return false;
        }

        LocalDate fechaFactura = envio.factura().fecha().toLocalDate();
        boolean despuesDeInicio = (fechaInicio == null) || !fechaFactura.isBefore(fechaInicio);
        boolean antesDeFin = (fechaFin == null) || !fechaFactura.isAfter(fechaFin);

        return despuesDeInicio && antesDeFin;
    }
}