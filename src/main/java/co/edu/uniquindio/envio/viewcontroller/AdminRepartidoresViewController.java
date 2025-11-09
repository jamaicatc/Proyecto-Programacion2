package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.controller.RepartidorController;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static co.edu.uniquindio.envio.utils.EmpresaConstantes.*;

public class AdminRepartidoresViewController {

    RepartidorController repartidorController;
    ObservableList<RepartidorDto> listaRepartidores = FXCollections.observableArrayList();
    ObservableList<String> listaDisponibilidades = FXCollections.observableArrayList("Activo", "Inactivo", "En ruta");
    RepartidorDto repartidorSeleccionado;

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
        repartidorController = new RepartidorController();
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
        listaRepartidores.addAll(repartidorController.obtenerRepartidores());
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
        RepartidorDto repartidorDto = crearRepartidorDto();
        if(datosValidos(repartidorDto)){
            if(repartidorController.agregarRepartidor(repartidorDto)){
                listaRepartidores.add(repartidorDto);
                limpiarCampos();
                mostrarMensaje(TITULO_REPARTIDOR_AGREGADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_AGREGADO, Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje(TITULO_REPARTIDOR_NO_AGREGADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_AGREGADO ,Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO,Alert.AlertType.WARNING);
        }
    }

    private void eliminarRepartidor() {
        if(repartidorSeleccionado != null){
            if(repartidorController.eliminarRepartidor(repartidorSeleccionado.idRepartidor())){
                listaRepartidores.remove(repartidorSeleccionado);
                limpiarCampos();
                mostrarMensaje(TITULO_REPARTIDOR_ELIMINADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_ELIMINADO,Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje(TITULO_REPARTIDOR_NO_ELIMINADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_ELIMINADO,Alert.AlertType.ERROR);
            }
        }
    }

    private void actualizarRepartidor() {
        if(repartidorSeleccionado != null){
            RepartidorDto repartidorDto = crearRepartidorDto();
            if(datosValidos(repartidorDto)){
                if(repartidorController.actualizarRepartidor(repartidorDto)){
                    actualizarTabla();
                    limpiarCampos();
                    mostrarMensaje(TITULO_REPARTIDOR_ACTUALIZADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_ACTUALIZADO, Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje(TITULO_REPARTIDOR_NO_ACTUALIZADO, HEADER_NOTIFICACION, BODY_REPARTIDOR_NO_ACTUALIZADO, Alert.AlertType.ERROR);
                }
            }else{
                mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO, Alert.AlertType.WARNING);
            }
        }
    }

    private void actualizarTabla() {
        listaRepartidores.clear();
        listaRepartidores.addAll(repartidorController.obtenerRepartidores());
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

    private RepartidorDto crearRepartidorDto() {
        return new RepartidorDto(
                txtIdRepartidor.getText(),
                txtNombre.getText(),
                txtDocumento.getText(),
                txtTelefono.getText(),
                txtZona.getText(),
                cmbDisponibilidad.getValue()
        );
    }

    private boolean datosValidos(RepartidorDto repartidorDto) {
        if(repartidorDto.idRepartidor().isBlank() ||
                repartidorDto.nombre().isBlank() ||
                repartidorDto.documento().isBlank() ||
                repartidorDto.telefono().isBlank() ||
                repartidorDto.zona().isBlank() ||
                repartidorDto.disponibilidad() == null
        ){
            return false;
        }else{
            return true;
        }
    }

    private void mostrarInformacionRepartidor(RepartidorDto repartidorSeleccionado) {
        if(repartidorSeleccionado != null){
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
}
