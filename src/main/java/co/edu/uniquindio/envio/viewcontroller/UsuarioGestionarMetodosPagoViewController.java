package co.edu.uniquindio.envio.viewcontroller;

import co.edu.uniquindio.envio.EnvioApplication;
import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.factory.pago.*;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDisplayDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioGestionarMetodosPagoViewController {

    private ModelFactory modelFactory;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<MetodoPagoDisplayDto> tableMetodosPago;

    @FXML
    private TableColumn<MetodoPagoDisplayDto, String> tcTipo;

    @FXML
    private TableColumn<MetodoPagoDisplayDto, String> tcSaldo;

    @FXML
    private Button btnCancelar;

    @FXML
    void initialize() {
        modelFactory = ModelFactory.getInstance();
        initView();

        // Add listener to refresh table when data changes in ModelFactory
        ModelFactory.datosActualizadosProperty.addListener((obs, oldVal, newVal) -> {
            refrescarTablaMetodosPago();
        });
    }

    private void initView() {
        tcTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        tcSaldo.setCellValueFactory(cellData -> cellData.getValue().saldoProperty());

        refrescarTablaMetodosPago();
    }

    private void refrescarTablaMetodosPago() {
        List<MetodoPagoDisplayDto> metodos = new ArrayList<>();
        
        // Retrieve shared instances of payment methods from ModelFactory
        Pago tarjeta = modelFactory.getPagoTarjeta();
        metodos.add(new MetodoPagoDisplayDto("Tarjeta", String.format("$%,.2f", tarjeta.getSaldo())));

        Pago pse = modelFactory.getPagoPSE();
        metodos.add(new MetodoPagoDisplayDto("PSE", String.format("$%,.2f", pse.getSaldo())));

        Pago efectivo = modelFactory.getPagoEfectivo();
        metodos.add(new MetodoPagoDisplayDto("Efectivo", "N/A"));

        ObservableList<MetodoPagoDisplayDto> metodosPago = FXCollections.observableArrayList(metodos);
        tableMetodosPago.setItems(metodosPago);
    }

    @FXML
    void onCancelar(ActionEvent event) {
        if (EnvioApplication.mainStage != null && EnvioApplication.sceneUsuario != null) {
            EnvioApplication.mainStage.setScene(EnvioApplication.sceneUsuario);
        } else {
            System.err.println("Error: mainStage or sceneUsuario is null in EnvioApplication.");
        }
    }
}
