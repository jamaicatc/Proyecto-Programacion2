package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.model.MetodoPago;

import java.util.List;

public interface IMetodoPagoMapping {
    MetodoPagoDto metodoPagoToMetodoPagoDto(MetodoPago metodoPago);
    MetodoPago metodoPagoDtoToMetodoPago(MetodoPagoDto metodoPagoDto);
    List<MetodoPagoDto> getMetodosPagoDto(List<MetodoPago> metodosPago);
}
