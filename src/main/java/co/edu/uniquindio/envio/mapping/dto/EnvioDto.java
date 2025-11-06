package co.edu.uniquindio.envio.mapping.dto;

import co.edu.uniquindio.envio.model.Factura;

import java.time.LocalDate;

public record EnvioDto(
        String idEnvio,
        LocalDate fecha,
        LocalDate fechaEntregaEstimada,
        String origen,
        String destino,
        String estado,
        double peso,
        double largo,
        double ancho,
        double alto,
        double costo,
        Factura factura
) {
}
