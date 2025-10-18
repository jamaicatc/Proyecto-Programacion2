package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Repartidor;

import java.util.List;

public interface IRepartidorMapping {
    List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores);
    RepartidorDto repartidorToRepartidorDto(Repartidor repartidor);
    Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto);
}
