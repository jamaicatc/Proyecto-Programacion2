package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class RepartidorEnviosAsignadosViewController implements DataUpdateListener {

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
    private TableView<EnvioDto> tableEnviosAsignados;

    @FXML
    private TableColumn<EnvioDto, String> tcDestino;

    @FXML
    private TableColumn<EnvioDto, String> tcEstado;

    @FXML
    private TableColumn<EnvioDto, String> tcFechaEstimada;

    @FXML
    private TableColumn<EnvioDto, String> tcIdEnvio;

    @FXML
    private TableColumn<EnvioDto, String> tcOrigen;

    private ModelFactory modelFactory;
    private RepartidorDto repartidorActual;
    private final ObservableList<EnvioDto> listaEnvios = FXCollections.observableArrayList();

    @FXML
    void onMarcarEnRuta(ActionEvent event) {
        cambiarEstadoSeleccionado("En ruta");
    }

    @FXML
    void onMarcarEntregado(ActionEvent event) {
        cambiarEstadoSeleccionado("Entregado");
    }

    @FXML
    void onVerDetalle(ActionEvent event) {
        EnvioDto envioSeleccionado = tableEnviosAsignados.getSelectionModel().getSelectedItem();
        if (envioSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/envio/envio/DetalleEnvio.fxml"));
                Parent parent = loader.load();

                DetalleEnvioViewController controller = loader.getController();

                UsuarioDto usuarioDelEnvio = modelFactory.obtenerUsuarios().stream()
                        .filter(u -> modelFactory.getEnvioServices().obtenerEnvios(u.idUsuario()).stream().anyMatch(e -> e.id().equals(envioSeleccionado.id())))
                        .findFirst()
                        .orElse(null);

                String nombreUsuario = (usuarioDelEnvio != null) ? usuarioDelEnvio.nombreCompleto() : "Desconocido";

                controller.init(nombreUsuario, envioSeleccionado);

                Stage stage = new Stage();
                stage.setTitle("Detalle del Envío");
                stage.setScene(new Scene(parent));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        modelFactory.addDataUpdateListener(this);
        initTable();
        // Los datos se cargarán a través del evento onDataChanged
    }

    private void initTable() {
        tableEnviosAsignados.setItems(listaEnvios);
        tcIdEnvio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcOrigen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionOrigen()));
        tcDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionDestino()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estadoActual()));
        tcFechaEstimada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaEntrega().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }

    private void cargarEnvios() {
        listaEnvios.clear();
        if (repartidorActual != null) {
            List<EnvioDto> envios = modelFactory.obtenerEnviosPorRepartidor(repartidorActual);
            listaEnvios.setAll(envios);
        }
        tableEnviosAsignados.refresh();
    }

    private void cambiarEstadoSeleccionado(String nuevoEstado) {
        EnvioDto envioSeleccionado = tableEnviosAsignados.getSelectionModel().getSelectedItem();
        if (envioSeleccionado != null) {
            EnvioDto envioActualizado = new EnvioDto(
                    envioSeleccionado.id(),
                    envioSeleccionado.fechaCreacion(),
                    envioSeleccionado.fechaEntrega(),
                    envioSeleccionado.direccionOrigen(),
                    envioSeleccionado.direccionDestino(),
                    nuevoEstado,
                    envioSeleccionado.peso(),
                    envioSeleccionado.largo(),
                    envioSeleccionado.ancho(),
                    envioSeleccionado.alto(),
                    envioSeleccionado.costo(),
                    envioSeleccionado.repartidorAsignado(),
                    envioSeleccionado.pago(),
                    envioSeleccionado.ultimaIncidenciaDescripcion(),
                    envioSeleccionado.factura()
            );
            modelFactory.actualizarEnvio(envioActualizado);
        }
    }

    @Override
    public void onDataChanged() {
        // Este método ahora es el responsable de cargar los datos del repartidor actual
        Object usuarioSesion = modelFactory.getUsuarioActual();
        if (usuarioSesion instanceof Repartidor) {
            this.repartidorActual = modelFactory.getMapper().repartidorToRepartidorDto((Repartidor) usuarioSesion);
        } else {
            // Si el usuario en sesión no es un repartidor, nos aseguramos de limpiar la vista
            this.repartidorActual = null;
        }
        cargarEnvios();
    }
}
