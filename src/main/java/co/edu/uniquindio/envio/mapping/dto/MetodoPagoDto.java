package co.edu.uniquindio.envio.mapping.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public record MetodoPagoDto(
        String alias,
        String numero,
        String tipo
) {
    @Override
    public String toString() {
        return alias + " (" + tipo + ")";
    }

    // Métodos Property para compatibilidad con JavaFX TableView
    public StringProperty aliasProperty() {
        return new SimpleStringProperty(alias);
    }

    public StringProperty numeroProperty() {
        return new SimpleStringProperty(numero);
    }

    public StringProperty tipoProperty() {
        return new SimpleStringProperty(tipo);
    }
}
