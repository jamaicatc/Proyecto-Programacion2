package co.edu.uniquindio.envio.mapping.dto;

import co.edu.uniquindio.envio.model.Factura;

import java.time.LocalDate;

public record EnvioDto(
        String id,
        LocalDate fechaCreacion,
        LocalDate fechaEntrega,
        String direccionOrigen,
        String direccionDestino,
        String estadoActual,
        double peso,
        double largo,
        double ancho,
        double alto,
        double costo,
        RepartidorDto repartidorAsignado,
        boolean pago,
        String ultimaIncidenciaDescripcion, // Nuevo campo para la descripción de la última incidencia
        FacturaDto factura // Nuevo campo para la factura
) {
}
