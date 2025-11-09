package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
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

public class AdminUsuariosViewController implements DataUpdateListener {

    ModelFactory modelFactory;
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
        modelFactory = ModelFactory.getInstance();
        modelFactory.addDataUpdateListener(this);
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
        listaUsuarios.setAll(modelFactory.obtenerUsuarios());
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
        // 1. Recoger datos del formulario
        String id = txtIdUsuario.getText();
        String nombre = txtNombreCompleto.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();

        // 2. Generar credenciales por defecto
        String usuario = nombre.split(" ")[0].toLowerCase(); // Tomar el primer nombre como usuario
        String contrasena = "password123"; // Contraseña por defecto

        // 3. Crear el DTO
        UsuarioDto usuarioDto = new UsuarioDto(id, nombre, correo, telefono, usuario, contrasena);

        // 4. Validar y agregar
        if(datosValidos(usuarioDto)){
            if(modelFactory.agregarUsuario(usuarioDto)){
                mostrarMensaje(TITULO_USUARIO_AGREGADO, HEADER_NOTIFICACION, BODY_USUARIO_AGREGADO, Alert.AlertType.INFORMATION);
                limpiarCampos();
            }else{
                mostrarMensaje(TITULO_USUARIO_NO_AGREGADO, HEADER_NOTIFICACION, BODY_USUARIO_NO_AGREGADO ,Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO,Alert.AlertType.WARNING);
        }
    }

    private void eliminarUsuario() {
        if(usuarioSeleccionado != null){
            if(modelFactory.eliminarUsuario(usuarioSeleccionado.idUsuario())){
                mostrarMensaje(TITULO_USUARIO_ELIMINADO, HEADER_NOTIFICACION, BODY_USUARIO_ELIMINADO,Alert.AlertType.INFORMATION);
                limpiarCampos();
            }else{
                mostrarMensaje(TITULO_USUARIO_NO_ELIMINADO, HEADER_NOTIFICACION, BODY_USUARIO_NO_ELIMINADO,Alert.AlertType.ERROR);
            }
        }
    }

    private void actualizarUsuario() {
        if(usuarioSeleccionado != null){
            // 1. Recoger datos del formulario
            String id = txtIdUsuario.getText();
            String nombre = txtNombreCompleto.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();

            // 2. Preservar credenciales existentes
            String usuario = usuarioSeleccionado.usuario();
            String contrasena = usuarioSeleccionado.contrasena();

            // 3. Crear el DTO
            UsuarioDto usuarioDto = new UsuarioDto(id, nombre, correo, telefono, usuario, contrasena);

            // 4. Validar y actualizar
            if(datosValidos(usuarioDto)){
                if(modelFactory.actualizarUsuario(usuarioDto)){
                    mostrarMensaje(TITULO_USUARIO_ACTUALIZADO, HEADER_NOTIFICACION, BODY_USUARIO_ACTUALIZADO, Alert.AlertType.INFORMATION);
                    limpiarCampos();
                }else{
                    mostrarMensaje(TITULO_USUARIO_NO_ACTUALIZADO, HEADER_NOTIFICACION, BODY_USUARIO_NO_ACTUALIZADO, Alert.AlertType.ERROR);
                }
            }else{
                mostrarMensaje(TITULO_INCOMPLETO, HEADER_NOTIFICACION, BODY_INCOMPLETO, Alert.AlertType.WARNING);
            }
        }
    }

    private void actualizarTabla() {
        obtenerUsuarios();
        tableUsuario.refresh();
    }


    private void nuevoUsuario() {
        limpiarCampos();
        txtIdUsuario.setText("Ingrese un id para el usuario");
    }

    private void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombreCompleto.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }

    private boolean datosValidos(UsuarioDto usuarioDto) {
        // La validación de credenciales no es necesaria aquí porque se autogeneran o se preservan.
        return usuarioDto.idUsuario() != null && !usuarioDto.idUsuario().isBlank() &&
                usuarioDto.nombreCompleto() != null && !usuarioDto.nombreCompleto().isBlank() &&
                usuarioDto.telefono() != null && !usuarioDto.telefono().isBlank() &&
                usuarioDto.correo() != null && !usuarioDto.correo().isBlank();
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

    @Override
    public void onDataChanged() {
        actualizarTabla();
    }
}
