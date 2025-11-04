package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.model.Direccion;

import java.util.List;

public interface IDireccionMapping<T extends Direccion, D extends DireccionDto> {
    D direccionToDireccionDto(T direccion);
    T direccionDtoToDireccion(D direccionDto);
    List<D> getDireccionesDto(List<T> direcciones);
    List<T> getDirecciones(List<D> direccionesDto);
}
