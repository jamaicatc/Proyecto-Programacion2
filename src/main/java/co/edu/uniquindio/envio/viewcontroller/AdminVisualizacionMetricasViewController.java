package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class AdminVisualizacionMetricasViewController {
    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private List<EnvioDto> todosLosEnvios;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarGraficos;

    @FXML
    private Button btnExportarImagen;

    @FXML
    private Button btnGenerarGrafico;

    @FXML
    private Button btnReiniciar;

    @FXML
    private ComboBox<String> cmbTipoGrafico;

    @FXML
    private DatePicker dpPeriodoFin;

    @FXML
    private DatePicker dpPeriodoInicio;

    @FXML
    private Label lblPromedioEntrega;

    @FXML
    private Label lblTotalIncidencias;

    @FXML
    private Label lblZonaMayorActividad;
    @FXML
    private LineChart<String, Number> chartIngresos;

    @FXML
    private PieChart chartDistribucion;

    @FXML
    void onActualizarGraficos(ActionEvent event) {
        generarGraficos();
    }

    @FXML
    void onExportarImagen(ActionEvent event) {
        // Lógica para exportar los gráficos como imagen.
    }

    @FXML
    void onGenerarGrafico(ActionEvent event) {
        generarGraficos();
    }

    @FXML
    void onReiniciar(ActionEvent event) {
        dpPeriodoInicio.setValue(null);
        dpPeriodoFin.setValue(null);
        cmbTipoGrafico.getSelectionModel().clearSelection();
        actualizarDatos(todosLosEnvios);
    }

    @FXML
    void initialize() {
        this.todosLosEnvios = modelFactory.obtenerEnvios();
        cmbTipoGrafico.setItems(FXCollections.observableArrayList("Ingresos por Periodo", "Distribución de Estados"));
        cmbTipoGrafico.getSelectionModel().selectFirst();

        chartIngresos.setTitle("Ingresos por Mes");
        chartDistribucion.setTitle("Distribución de Envíos por Estado");

        actualizarDatos(todosLosEnvios);
    }

    private void generarGraficos() {
        LocalDate fechaInicio = dpPeriodoInicio.getValue();
        LocalDate fechaFin = dpPeriodoFin.getValue();

        List<EnvioDto> enviosFiltrados = todosLosEnvios.stream()
                .filter(envio -> {
                    LocalDate fechaCreacion = envio.fechaCreacion();
                    boolean despuesDeInicio = (fechaInicio == null) || !fechaCreacion.isBefore(fechaInicio);
                    boolean antesDeFin = (fechaFin == null) || !fechaCreacion.isAfter(fechaFin);
                    return despuesDeInicio && antesDeFin;
                })
                .collect(Collectors.toList());

        actualizarDatos(enviosFiltrados);
    }

    private void actualizarDatos(List<EnvioDto> envios) {
        // Actualizar etiquetas de resumen
        double tiempoPromedio = modelFactory.calcularTiempoPromedioEntrega(envios);
        long totalIncidencias = modelFactory.contarIncidencias(envios);

        lblPromedioEntrega.setText(String.format("%.2f días", tiempoPromedio));
        lblTotalIncidencias.setText(String.valueOf(totalIncidencias));
        lblZonaMayorActividad.setText("N/A"); // Modelo no soporta esta métrica

        // Actualizar Gráfico de Líneas (Ingresos por Mes)
        actualizarGraficoIngresos(envios);

        // Actualizar Gráfico de Torta (Distribución por Estado)
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

}
