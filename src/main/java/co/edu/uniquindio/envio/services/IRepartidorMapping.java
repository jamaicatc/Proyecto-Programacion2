package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Repartidor;

import java.util.List;

public interface IRepartidorMapping<T extends Repartidor, D extends RepartidorDto> {
    List<D> getRepartidoresDto(List<T> listaRepartidores);
    D repartidorToRepartidorDto(T repartidor);
    T repartidorDtoToRepartidor(D repartidorDto);
}
