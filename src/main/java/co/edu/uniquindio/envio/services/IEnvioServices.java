package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.IncidenciaDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Factura;

import java.util.List;

public interface IEnvioServices {
    List<EnvioDto> obtenerEnvios();
    List<EnvioDto> obtenerEnvios(String idUsuario);
    EnvioDto obtenerEnvioDto(String idEnvio);
    boolean agregarEnvio(String idUsuario, EnvioDto envioDto);
    boolean actualizarEnvio(EnvioDto envioDto);
    boolean eliminarEnvio(String idEnvio);
    List<String> obtenerHistorial(String idEnvio);
    Factura pagarEnvio(String idEnvio, MetodoPagoDto metodoPagoDto);
    EnvioDto obtenerEnvio(String numeroSeguimiento);
    void asignarEnvio(EnvioDto envio, RepartidorDto repartidor);
    void reasignarEnvio(EnvioDto envio, RepartidorDto nuevoRepartidor);
    void registrarIncidencia(EnvioDto envio, IncidenciaDto incidencia);
    List<EnvioDto> obtenerEnviosNoAsignados();
    List<EnvioDto> obtenerEnviosPorRepartidor(RepartidorDto repartidor);
}
