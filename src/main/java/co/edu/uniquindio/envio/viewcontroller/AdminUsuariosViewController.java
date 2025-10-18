package co.edu.uniquindio.envio.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.envio.controller.UsuarioController;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static co.edu.uniquindio.envio.utils.EmpresaConstantes.*;

public class AdminUsuariosViewController {

    UsuarioController usuarioController;
    ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<UsuarioDto> tableUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreo;

    @FXML
    private TableColumn<UsuarioDto, String> tcIdUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcNombreCompleto;

    @FXML
    private TableColumn<UsuarioDto, String> tcTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtTelefono;

    @FXML
    void initialize() {
        usuarioController = new UsuarioController();
        initView();
    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void onAgregarUsuario(ActionEvent event) {
        agregarUsuario();
    }


    @FXML
    void onEliminarUsuario(ActionEvent event) {
        eliminarUsuario();
    }

    @FXML
    void onNuevoUsuario(ActionEvent event) {
        nuevoUsuario();
    }


    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tableUsuario.getItems().clear();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();
    }

    private void obtenerUsuarios() {
        listaUsuarios.addAll(usuarioController.obtenerUsuarios());
    }

    private void initDataBinding() {
        tcIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idUsuario()));
        tcNombreCompleto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreCompleto()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void agregarUsuario() {
        UsuarioDto usuarioDto = crearUsuarioDto();
        if(datosValidos(usuarioDto)){
            if(usuarioController.agregarUsuario(usuarioDto)){
                listaUsuarios.addAll(usuarioDto);
                limpiarCampos();
                mostrarMensaje(TITULO_USUARIO_AGREGADO, HEADER, BODY_USUARIO_AGREGADO, Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje(TITULO_USUARIO_NO_AGREGADO, HEADER, BODY_USUARIO_NO_AGREGADO ,Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje(TITULO_INCOMPLETO, HEADER, BODY_INCOMPLETO,Alert.AlertType.WARNING);
        }
    }

    private void eliminarUsuario() {
        if(usuarioSeleccionado != null){
            if(usuarioController.eliminarUsuario(usuarioSeleccionado.idUsuario())){
                listaUsuarios.remove(usuarioSeleccionado);
                limpiarCampos();
                mostrarMensaje(TITULO_USUARIO_ELIMINADO, HEADER, BODY_USUARIO_ELIMINADO,Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje(TITULO_USUARIO_NO_ELIMINADO, HEADER, BODY_USUARIO_NO_ELIMINADO,Alert.AlertType.ERROR);
            }
        }
    }

    private void actualizarUsuario() {
        if(usuarioSeleccionado != null){
            UsuarioDto usuarioDto = crearUsuarioDto();
            if(datosValidos(usuarioDto)){
                if(usuarioController.actualizarUsuario(usuarioDto)){
                    actualizarTabla();
                    limpiarCampos();
                    mostrarMensaje(TITULO_USUARIO_ACTUALIZADO, HEADER, BODY_USUARIO_ACTUALIZADO, Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje(TITULO_USUARIO_NO_ACTUALIZADO, HEADER, BODY_USUARIO_NO_ACTUALIZADO, Alert.AlertType.ERROR);
                }
            }else{
                mostrarMensaje(TITULO_INCOMPLETO, HEADER, BODY_INCOMPLETO, Alert.AlertType.WARNING);
            }
        }
    }

    private void actualizarTabla() {
        listaUsuarios.clear();
        listaUsuarios.addAll(usuarioController.obtenerUsuarios());
    }


    private void nuevoUsuario() {
        limpiarCampos();
        txtIdUsuario.setText("Ingrese un id para el usuario");
    }

    private void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombreCompleto.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

    private UsuarioDto crearUsuarioDto() {
        return new UsuarioDto(
                txtIdUsuario.getText(),
                txtNombreCompleto.getText(),
                txtTelefono.getText(),
                txtCorreo.getText());
    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        if(usuarioDto.idUsuario().isBlank() ||
                usuarioDto.nombreCompleto().isBlank() ||
                usuarioDto.telefono().isBlank() ||
                usuarioDto.correo().isBlank()
        ){
            return false;
        }else{
            return true;
        }
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtIdUsuario.setText(usuarioSeleccionado.idUsuario());
            txtNombreCompleto.setText(usuarioSeleccionado.nombreCompleto());
            txtCorreo.setText(usuarioSeleccionado.correo());
            txtTelefono.setText(usuarioSeleccionado.telefono());
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

