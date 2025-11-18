package co.edu.uniquindio.envio.mapping.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public record MetodoPagoDisplayDto(String tipo, String saldo) {
    public StringProperty tipoProperty() {
        return new SimpleStringProperty(tipo);
    }

    public StringProperty saldoProperty() {
        return new SimpleStringProperty(saldo);
    }
}
