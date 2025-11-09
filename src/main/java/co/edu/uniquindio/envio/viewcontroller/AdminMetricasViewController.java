package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.Metrica;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminMetricasViewController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ObservableList<Metrica> metricasData = FXCollections.observableArrayList();
    private List<EnvioDto> todosLosEnvios;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarMetricas;

    @FXML
    private Button btnAplicarFiltros;

    @FXML
    private Button btnExportarReporte;

    @FXML
    private Button btnReiniciar;

    @FXML
    private ComboBox<String> cmbZona;

    @FXML
    private DatePicker dpPeriodoFin;

    @FXML
    private DatePicker dpPeriodoInicio;

    @FXML
    private Label lblIncidenciasRegistradas;

    @FXML
    private Label lblIngresosTotales;

    @FXML
    private Label lblServiciosAdicional;

    @FXML
    private Label lblTiempoPromedio;

    @FXML
    private TableView<Metrica> tableMetricas;

    @FXML
    private TableColumn<Metrica, String> tcIndicador;

    @FXML
    private TableColumn<Metrica, String> tcUltimaActualizacion;

    @FXML
    private TableColumn<Metrica, String> tcUnidad;

    @FXML
    private TableColumn<Metrica, String> tcValor;

    @FXML
    void onActualizarMetricas(ActionEvent event) {
        actualizarMetricas(getEnviosFiltrados());
    }

    @FXML
    void onAplicarFiltros(ActionEvent event) {
        List<EnvioDto> enviosFiltrados = getEnviosFiltrados();
        actualizarMetricas(enviosFiltrados);
    }

    @FXML
    void onExportarReporte(ActionEvent event) {
        // Lógica para exportar reporte (PDF o CSV) puede ser añadida aquí.
    }

    @FXML
    void onReiniciar(ActionEvent event) {
        dpPeriodoInicio.setValue(null);
        dpPeriodoFin.setValue(null);
        cmbZona.getSelectionModel().clearSelection();
        actualizarMetricas(todosLosEnvios);
    }

    @FXML
    void initialize() {
        this.todosLosEnvios = modelFactory.obtenerEnvios();
        initTable();
        cargarDatosIniciales();
    }

    private void initTable() {
        tcIndicador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcValor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValor()));
        tcUnidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidad()));
        tcUltimaActualizacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaActualizacion()));
        tableMetricas.setItems(metricasData);
    }

    private void cargarDatosIniciales() {
        // Cargar zonas en el ComboBox
        List<String> zonas = modelFactory.obtenerZonasDeRepartidores();
        cmbZona.setItems(FXCollections.observableArrayList(zonas));

        // Cargar todas las métricas inicialmente
        actualizarMetricas(todosLosEnvios);
    }

    private void actualizarMetricas(List<EnvioDto> envios) {
        double tiempoPromedio = modelFactory.calcularTiempoPromedioEntrega(envios);
        double ingresosTotales = modelFactory.calcularIngresosTotales(envios);
        long incidencias = modelFactory.contarIncidencias(envios);

        lblTiempoPromedio.setText(String.format("%.2f días", tiempoPromedio));
        lblIngresosTotales.setText(String.format("$%,.2f", ingresosTotales));
        lblIncidenciasRegistradas.setText(String.valueOf(incidencias));
        lblServiciosAdicional.setText("N/A"); // No hay datos en el modelo para esto

        metricasData.clear();
        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        metricasData.add(new Metrica("Tiempo promedio de entrega", String.format("%.2f", tiempoPromedio), "días", fechaActual));
        metricasData.add(new Metrica("Ingresos totales", String.format("%,.2f", ingresosTotales), "COP", fechaActual));
        metricasData.add(new Metrica("Incidencias registradas", String.valueOf(incidencias), "unidades", fechaActual));
        metricasData.add(new Metrica("Servicios adicionales más usados", "N/A", "", fechaActual));

        tableMetricas.refresh();
    }

    private List<EnvioDto> getEnviosFiltrados() {
        LocalDate fechaInicio = dpPeriodoInicio.getValue();
        LocalDate fechaFin = dpPeriodoFin.getValue();

        return todosLosEnvios.stream()
                .filter(envio -> {
                    LocalDate fechaCreacion = envio.fechaCreacion();
                    boolean despuesDeInicio = (fechaInicio == null) || !fechaCreacion.isBefore(fechaInicio);
                    boolean antesDeFin = (fechaFin == null) || !fechaCreacion.isAfter(fechaFin);
                    return despuesDeInicio && antesDeFin;
                })
                .collect(Collectors.toList());
    }

}
