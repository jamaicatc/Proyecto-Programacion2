package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.model.Envio;

import java.util.List;

public interface IEnvioMapping {
    EnvioDto envioToEnvioDto(Envio envio);
    Envio envioDtoToEnvio(EnvioDto envioDto);
    List<EnvioDto> getEnviosDto(List<Envio> listaEnvios);
}
