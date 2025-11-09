package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static co.edu.uniquindio.envio.utils.EmpresaConstantes.*;

public class AdminRepartidoresViewController implements DataUpdateListener {

    private final ModelFactory modelFactory = ModelFactory.getInstance();
    private final ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();
    private final ObservableList<String> listaDisponibilidades = FXCollections.observableArrayList("Activo", "Inactivo", "En ruta");
    private RepartidorDto repartidorSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnActualizarTabla;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnExportar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ComboBox<String> cmbDisponibilidad;

    @FXML
    private TableView<RepartidorDto> tableRepartidor;

    @FXML
    private TableColumn<RepartidorDto, String> tcDisponibilidad;

    @FXML
    private TableColumn<RepartidorDto, String> tcDocumento;

    @FXML
    private TableColumn<RepartidorDto, String> tcIdRepartidor;

    @FXML
    private TableColumn<RepartidorDto, String> tcNombre;

    @FXML
    private TableColumn<RepartidorDto, String> tcTelefono;

    @FXML
    private TableColumn<RepartidorDto, String> tcZona;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtIdRepartidor;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtZona;

    @FXML
    void initialize() {
        modelFactory.addDataUpdateListener(this);
        initView();
        cargarDatos();
    }

    private void cargarDatos() {
        cmbDisponibilidad.setItems(listaDisponibilidades);
    }

    @FXML
    void onActualizarRepartidor(ActionEvent event) {
        actualizarRepartidor();
    }

    @FXML
    void onActualizarTabla(ActionEvent event) {
        actualizarTabla();
    }

    @FXML
    void onAgregarRepartidor(ActionEvent event) {
        agregarRepartidor();
    }

    @FXML
    void onEliminarRepartidor(ActionEvent event) {
        eliminarRepartidor();
    }

    @FXML
    void onExportar(ActionEvent event) {

    }

    @FXML
    void onNuevoRepartidor(ActionEvent event) {
        nuevoRepartidor();
    }

    private void initView() {
        initDataBinding();
        obtenerRepartidores();
        tableRepartidor.getItems().clear();
        tableRepartidor.setItems(listaRepartidores);
        listenerSelection();
    }

    private void obtenerRepartidores() {
        listaRepartidores.setAll(modelFactory.obtenerRepartidores());
    }

    private void initDataBinding() {
        tcIdRepartidor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idRepartidor()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().documento()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        tcZona.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().zona()));
        tcDisponibilidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().disponibilidad()));
    }

    private void listenerSelection() {
        tableRepartidor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            repartidorSeleccionado = newSelection;
            mostrarInformacionRepartidor(repartidorSeleccionado);
        });
    }

    private void agregarRepartidor() {
        // 1. Recoger datos del formulario
        String id = txtIdRepartidor.getText();
        String nombre = txtNombre.getText();
        String documento = txtDocumento.getText();
        String telefono = txtTelefono.getText();
        String zona = txtZona.getText();
        String disponibilidad = cmbDisponibilidad.getValue();

        // 2. Generar credenciales por defecto
        String usuario = nombre.split(" ")[0].toLowerCase();
        String contrasena = "password123";

        // 3. Crear el DTO
        RepartidorDto repartidorDto = new RepartidorDto(id, nombre, documento, telefono, usuario, contrasena, zona, disponibilidad);

        // 4. Validar y agregar
        if (datosValidos(repartidorDto)) {
            if (modelFactory.agregarRepartidor(repartidorDto)) {
                limpiarCampos();
                mostrarMensaje(TITULO_REPARTIDOR_AGREGADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_AGREGADO, Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_REPARTIDOR_NO_AGREGADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_AGREGADO, Alert.AlertType.ERROR);
            }
        } else {
            mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO, Alert.AlertType.WARNING);
        }
    }

    private void eliminarRepartidor() {
        if (repartidorSeleccionado != null) {
            if (modelFactory.eliminarRepartidor(repartidorSeleccionado.idRepartidor())) {
                limpiarCampos();
                mostrarMensaje(TITULO_REPARTIDOR_ELIMINADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_ELIMINADO, Alert.AlertType.INFORMATION);
            } else {
                mostrarMensaje(TITULO_REPARTIDOR_NO_ELIMINADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_ELIMINADO, Alert.AlertType.ERROR);
            }
        }
    }

    private void actualizarRepartidor() {
        if (repartidorSeleccionado != null) {
            // 1. Recoger datos del formulario
            String id = txtIdRepartidor.getText();
            String nombre = txtNombre.getText();
            String documento = txtDocumento.getText();
            String telefono = txtTelefono.getText();
            String zona = txtZona.getText();
            String disponibilidad = cmbDisponibilidad.getValue();

            // 2. Preservar credenciales existentes
            String usuario = repartidorSeleccionado.usuario();
            String contrasena = repartidorSeleccionado.contrasena();

            // 3. Crear el DTO
            RepartidorDto repartidorDto = new RepartidorDto(id, nombre, documento, telefono, usuario, contrasena, zona, disponibilidad);

            // 4. Validar y actualizar
            if (datosValidos(repartidorDto)) {
                if (modelFactory.actualizarRepartidor(repartidorDto)) {
                    limpiarCampos();
                    mostrarMensaje(TITULO_REPARTIDOR_ACTUALIZADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_ACTUALIZADO, Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje(TITULO_REPARTIDOR_NO_ACTUALIZADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_ACTUALIZADO, Alert.AlertType.ERROR);
                }
            } else {
                mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO, Alert.AlertType.WARNING);
            }
        }
    }

    private void actualizarTabla() {
        obtenerRepartidores();
        tableRepartidor.refresh();
    }

    private void nuevoRepartidor() {
        limpiarCampos();
        txtIdRepartidor.setText("Ingrese un id para el repartidor");
    }

    private void limpiarCampos() {
        txtIdRepartidor.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtZona.setText("");
        cmbDisponibilidad.setValue(null);
    }

    private boolean datosValidos(RepartidorDto repartidorDto) {
        return repartidorDto.idRepartidor() != null && !repartidorDto.idRepartidor().isBlank() &&
                repartidorDto.nombre() != null && !repartidorDto.nombre().isBlank() &&
                repartidorDto.documento() != null && !repartidorDto.documento().isBlank() &&
                repartidorDto.telefono() != null && !repartidorDto.telefono().isBlank() &&
                repartidorDto.zona() != null && !repartidorDto.zona().isBlank() &&
                repartidorDto.disponibilidad() != null;
    }

    private void mostrarInformacionRepartidor(RepartidorDto repartidorSeleccionado) {
        if (repartidorSeleccionado != null) {
            txtIdRepartidor.setText(repartidorSeleccionado.idRepartidor());
            txtNombre.setText(repartidorSeleccionado.nombre());
            txtDocumento.setText(repartidorSeleccionado.documento());
            txtTelefono.setText(repartidorSeleccionado.telefono());
            txtZona.setText(repartidorSeleccionado.zona());
            cmbDisponibilidad.setValue(repartidorSeleccionado.disponibilidad());
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    @Override
    public void onDataChanged() {
        actualizarTabla();
    }
}
