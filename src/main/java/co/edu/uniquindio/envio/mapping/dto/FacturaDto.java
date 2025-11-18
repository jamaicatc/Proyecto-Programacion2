package co.edu.uniquindio.envio.mapping.dto;

import java.time.LocalDateTime;

public record FacturaDto(
        String idFactura,
        LocalDateTime fecha,
        double monto,
        MetodoPagoDto metodoPago
) {
}
