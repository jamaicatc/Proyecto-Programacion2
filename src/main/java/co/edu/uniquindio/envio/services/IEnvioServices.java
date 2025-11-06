package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;

import java.util.List;

public interface IEnvioServices {
    List<EnvioDto> obtenerEnvios();
    List<EnvioDto> obtenerEnvios(String idUsuario);
    EnvioDto obtenerEnvioDto(String idEnvio);
    boolean agregarEnvio(String idUsuario, EnvioDto envioDto);
    boolean actualizarEnvio(EnvioDto envioDto);
    boolean eliminarEnvio(String idEnvio);
    List<String> obtenerHistorial(String idEnvio);
}
