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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioViewController {

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

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtIdUsuario.setText(usuarioSeleccionado.idUsuario());
            txtNombreCompleto.setText(usuarioSeleccionado.nombreCompleto());
            txtCorreo.setText(usuarioSeleccionado.correo());
            txtTelefono.setText(usuarioSeleccionado.telefono());
        }
    }

    @FXML
    void onActualizarUsuario(ActionEvent event) {

    }

    @FXML
    void onAgregarUsuario(ActionEvent event) {

    }

    @FXML
    void onEliminarUsuario(ActionEvent event) {

    }

    @FXML
    void onNuevoUsuario(ActionEvent event) {

    }

}

