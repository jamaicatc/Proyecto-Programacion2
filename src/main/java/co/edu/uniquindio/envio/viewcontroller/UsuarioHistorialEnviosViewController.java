package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.controller.UsuarioController;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

// Apache PDFBox imports (asegúrate de que estas dependencias estén en tu build.gradle o pom.xml)
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts; // Importación necesaria para PDFBox 3.x

public class UsuarioHistorialEnviosViewController implements DataUpdateListener {

    private final UsuarioController usuarioController = new UsuarioController(ModelFactory.getInstance());
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();
    private Usuario usuario; // Este campo se establecerá al inicializar

    // Campos FXML - solo se mantienen los que se usan o tienen manejadores de eventos
    @FXML
    private Button btnGenerarReporteCsv;

    @FXML
    private Button btnGenerarReportePdf;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private DatePicker dpFechaDesde;

    @FXML
    private DatePicker dpFechaHasta;

    @FXML
    private TableView<EnvioDto> tableEnvios;

    @FXML
    private TableColumn<EnvioDto, String> tcCosto;

    @FXML
    private TableColumn<EnvioDto, String> tcDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcEstado;

    @FXML
    private TableColumn<EnvioDto, String> tcFecha;

    @FXML
    private TableColumn<EnvioDto, String> tcIdEnvio;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigen;

    @FXML
    void onBuscar(ActionEvent event) {
        filtrarEnvios();
    }

    @FXML
    void onGenerarReporteCsv(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para generar el reporte CSV.", Alert.AlertType.ERROR);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(btnGenerarReporteCsv.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Escribir encabezado
                writer.append("ID Envio,Fecha,Origen,Destino,Estado,Costo\n");

                // Escribir datos
                for (EnvioDto envio : listaEnvios) {
                    writer.append(String.format("%s,%s,%s,%s,%s,%.2f\n",
                            envio.id(),
                            envio.fechaCreacion().toString(),
                            envio.direccionOrigen(),
                            envio.direccionDestino(),
                            envio.estadoActual(),
                            envio.costo()));
                }
                mostrarMensaje("Reporte CSV", "Generación Exitosa", "Reporte CSV generado exitosamente en: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al generar reporte CSV", "Error al generar el reporte CSV: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onGenerarReportePdf(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para generar el reporte PDF.", Alert.AlertType.ERROR);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(btnGenerarReportePdf.getScene().getWindow());

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Historial de Envíos del Usuario: " + (usuario != null ? usuario.getNombreCompleto() : "N/A"));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 730);
                contentStream.showText("Fecha de Generación: " + LocalDate.now());
                contentStream.endText();

                float yStart = 700;
                float margin = 50;
                float yPosition = yStart;
                int rowsPerPage = 25;
                int rowCount = 0;

                String[] headers = {"ID Envio", "Fecha", "Origen", "Destino", "Estado", "Costo"};
                float[] columnWidths = {80, 80, 100, 100, 80, 60};

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                float x = margin;
                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                    x += columnWidths[i];
                }
                yPosition -= 15;

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
                for (EnvioDto envio : listaEnvios) {
                    if (rowCount >= rowsPerPage) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = 750;
                        rowCount = 0;

                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                        x = margin;
                        for (int i = 0; i < headers.length; i++) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(x, yPosition);
                            contentStream.showText(headers[i]);
                            contentStream.endText();
                            x += columnWidths[i];
                        }
                        yPosition -= 15;
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
                    }

                    x = margin;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(envio.id());
                    contentStream.endText();
                    x += columnWidths[0];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(envio.fechaCreacion().toString());
                    contentStream.endText();
                    x += columnWidths[1];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(envio.direccionOrigen());
                    contentStream.endText();
                    x += columnWidths[2];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(envio.direccionDestino());
                    contentStream.endText();
                    x += columnWidths[3];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(envio.estadoActual());
                    contentStream.endText();
                    x += columnWidths[4];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition);
                    contentStream.showText(String.format("%.2f", envio.costo()));
                    contentStream.endText();
                    x += columnWidths[5];

                    yPosition -= 12;
                    rowCount++;
                }

                contentStream.close();
                document.save(file);
                mostrarMensaje("Reporte PDF", "Generación Exitosa", "Reporte PDF generado exitosamente en: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al generar reporte PDF", "Error al generar el reporte PDF: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        dpFechaDesde.setValue(null);
        dpFechaHasta.setValue(null);
        cmbEstado.setValue(null);
        cargarEnviosUsuario(); // Recargar todos los envíos del usuario sin filtros
    }

    @FXML
    void onVerDetalles(ActionEvent event) {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para ver detalles del envío.", Alert.AlertType.ERROR);
            return;
        }
        EnvioDto selectedEnvio = tableEnvios.getSelectionModel().getSelectedItem();
        if (selectedEnvio != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/envio/DetalleEnvio.fxml"));
                Parent root = loader.load();
                DetalleEnvioViewController controller = loader.getController();
                controller.init(usuario.getNombreCompleto(), selectedEnvio);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                mostrarMensaje("Error", "Error al cargar detalles", "Error al cargar la ventana de detalles: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje("Advertencia", "Envío no seleccionado", "No ha seleccionado ningún envío para ver detalles.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void initialize() {
        initTable();
        cmbEstado.setItems(FXCollections.observableArrayList("Solicitado", "Asignado", "En Ruta", "Entregado", "Incidencia"));
        ModelFactory.getInstance().addDataUpdateListener(this);
        onDataChanged(); // Carga inicial de datos
    }

    private void initTable() {
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaCreacion().toString()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionDestino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
        tcCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().costo())));
        tableEnvios.setItems(listaEnvios);
    }

    private void cargarEnviosUsuario() {
        if (usuario != null) {
            List<EnvioDto> enviosDelUsuario = usuarioController.obtenerEnvios(usuario.getIdUsuario());
            if (enviosDelUsuario != null && !enviosDelUsuario.isEmpty()) {
                actualizarTabla(enviosDelUsuario);
            } else {
                actualizarTabla(Collections.emptyList()); // Clear table if no shipments or null list
                mostrarMensaje("Información", "Sin Envíos", "El usuario actual no tiene envíos registrados en su historial.", Alert.AlertType.INFORMATION);
            }
        } else {
            listaEnvios.clear(); // Clear the table if no user
            mostrarMensaje("Advertencia", "Usuario no disponible", "No se puede cargar el historial de envíos porque no hay un usuario logueado.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla(List<EnvioDto> envios) {
        listaEnvios.clear();
        listaEnvios.addAll(envios);
        // Forzar un refresco de la tabla si es necesario, aunque setItems debería ser suficiente
        tableEnvios.refresh();
    }

    private void filtrarEnvios() {
        if (usuario == null) {
            mostrarMensaje("Error", "Usuario no logueado", "No hay un usuario logueado para filtrar envíos.", Alert.AlertType.ERROR);
            return;
        }

        // Obtener todos los envíos del usuario y luego aplicar los filtros
        List<EnvioDto> enviosBase = usuarioController.obtenerEnvios(usuario.getIdUsuario());
        List<EnvioDto> enviosFiltrados = enviosBase;

        if (dpFechaDesde.getValue() != null) {
            LocalDate fechaDesde = dpFechaDesde.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> !envio.fechaCreacion().isBefore(fechaDesde))
                    .collect(Collectors.toList());
        }

        if (dpFechaHasta.getValue() != null) {
            LocalDate fechaHasta = dpFechaHasta.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> !envio.fechaCreacion().isAfter(fechaHasta))
                    .collect(Collectors.toList());
        }

        if (cmbEstado.getValue() != null && !cmbEstado.getValue().isEmpty()) {
            String estadoSeleccionado = cmbEstado.getValue();
            enviosFiltrados = enviosFiltrados.stream()
                    .filter(envio -> envio.estadoActual().equalsIgnoreCase(estadoSeleccionado))
                    .collect(Collectors.toList());
        }

        actualizarTabla(enviosFiltrados);
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @Override
    public void onDataChanged() {
        Object usuarioLogueado = ModelFactory.getInstance().getUsuarioActual();
        if (usuarioLogueado instanceof Usuario) {
            this.usuario = (Usuario) usuarioLogueado;
            cargarEnviosUsuario();
        } else {
            this.usuario = null;
            listaEnvios.clear(); // Asegurarse de que la tabla esté vacía si no hay usuario
        }
    }
}
