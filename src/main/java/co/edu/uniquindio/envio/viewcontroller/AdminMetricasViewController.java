package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AdminMetricasViewController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private List<EnvioDto> todosLosEnvios;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarMetricas; // This button will now trigger a full data refresh

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

    // New FXML elements for charts and additional labels
    @FXML
    private Label lblZonaMayorActividad;
    @FXML
    private LineChart<String, Number> chartIngresos;
    @FXML
    private PieChart chartDistribucion;
    @FXML
    private Button btnActualizarGraficos; // This button is redundant with btnActualizarMetricas, can be removed or repurposed
    @FXML
    private Button btnExportarImagen;

    @FXML
    void onActualizarMetricas(ActionEvent event) {
        actualizarDatos(getEnviosFiltrados());
    }

    @FXML
    void onAplicarFiltros(ActionEvent event) {
        List<EnvioDto> enviosFiltrados = getEnviosFiltrados();
        actualizarDatos(enviosFiltrados);
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
        actualizarDatos(todosLosEnvios);
    }

    // New methods for chart interaction
    @FXML
    void onActualizarGraficos(ActionEvent event) {
        actualizarDatos(getEnviosFiltrados());
    }

    @FXML
    void onExportarImagen(ActionEvent event) {
        // Lógica para exportar los gráficos como imagen.
    }

    @FXML
    void initialize() {
        this.todosLosEnvios = modelFactory.obtenerEnvios();
        cargarDatosIniciales();

        // Initialize chart titles
        chartIngresos.setTitle("Ingresos por Mes");
        chartDistribucion.setTitle("Distribución de Envíos por Estado");

        // Initial data load for all metrics and charts
        actualizarDatos(todosLosEnvios);
    }

    private void cargarDatosIniciales() {
        // Cargar zonas en el ComboBox
        List<String> zonas = modelFactory.obtenerZonasDeRepartidores();
        cmbZona.setItems(FXCollections.observableArrayList(zonas));
    }

    private void actualizarDatos(List<EnvioDto> envios) {
        // Update summary labels
        double tiempoPromedio = modelFactory.calcularTiempoPromedioEntrega(envios);
        double ingresosTotales = modelFactory.calcularIngresosTotales(envios);
        long incidencias = modelFactory.contarIncidencias(envios);

        lblTiempoPromedio.setText(String.format("%.2f días", tiempoPromedio));
        lblIngresosTotales.setText(String.format("$%,.2f", ingresosTotales));
        lblIncidenciasRegistradas.setText(String.valueOf(incidencias));
        lblServiciosAdicional.setText("N/A"); // No hay datos en el modelo para esto
        lblZonaMayorActividad.setText("N/A"); // Modelo no soporta esta métrica

        // Update Line Chart (Ingresos por Mes)
        actualizarGraficoIngresos(envios);

        // Update Pie Chart (Distribución por Estado)
        actualizarGraficoDistribucion(envios);
    }

    private void actualizarGraficoIngresos(List<EnvioDto> envios) {
        chartIngresos.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos");

        Map<String, Double> ingresosPorMes = envios.stream()
                .collect(Collectors.groupingBy(
                        envio -> envio.fechaCreacion().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")),
                        Collectors.summingDouble(EnvioDto::costo)
                ));

        ingresosPorMes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));

        chartIngresos.getData().add(series);
    }

    private void actualizarGraficoDistribucion(List<EnvioDto> envios) {
        Map<String, Long> conteoPorEstado = envios.stream()
                .collect(Collectors.groupingBy(EnvioDto::estadoActual, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = conteoPorEstado.entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey() + " (" + entry.getValue() + ")", entry.getValue()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        chartDistribucion.setData(pieChartData);
    }

    private List<EnvioDto> getEnviosFiltrados() {
        LocalDate fechaInicio = dpPeriodoInicio.getValue();
        LocalDate fechaFin = dpPeriodoFin.getValue();
        String zonaSeleccionada = cmbZona.getSelectionModel().getSelectedItem();

        // Recargar los envíos desde el ModelFactory para asegurar que los datos estén actualizados.
        this.todosLosEnvios = modelFactory.obtenerEnvios();

        return todosLosEnvios.stream()
                .filter(envio -> {
                    LocalDate fechaCreacion = envio.fechaCreacion();
                    boolean despuesDeInicio = (fechaInicio == null) || !fechaCreacion.isBefore(fechaInicio);
                    boolean antesDeFin = (fechaFin == null) || !fechaCreacion.isAfter(fechaFin);
                    // La zona se determina a través del repartidor asignado al envío.
                    // Si un envío no tiene repartidor o la zona no coincide, no se incluye en el filtro.
                    boolean enZona = (zonaSeleccionada == null || zonaSeleccionada.isEmpty()) ||
                                     (envio.repartidorAsignado() != null &&
                                      envio.repartidorAsignado().zona().equals(zonaSeleccionada));

                    return despuesDeInicio && antesDeFin && enZona;
                })
                .collect(Collectors.toList());
    }
}
