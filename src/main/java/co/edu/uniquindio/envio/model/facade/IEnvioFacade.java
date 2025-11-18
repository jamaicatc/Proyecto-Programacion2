package co.edu.uniquindio.envio.model.facade;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.IncidenciaDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Factura;

import java.util.List;

public interface IEnvioFacade {
    boolean crearYAsignarEnvio(String idUsuario, EnvioDto envioDto, RepartidorDto repartidorDto);
    Factura procesarPagoEnvio(String idEnvio, MetodoPagoDto metodoPagoDto);
    boolean registrarIncidenciaEnvio(String idEnvio, IncidenciaDto incidenciaDto);
}
