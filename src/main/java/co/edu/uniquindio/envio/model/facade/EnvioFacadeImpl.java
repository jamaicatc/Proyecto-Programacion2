package co.edu.uniquindio.envio.model.facade;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.IncidenciaDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Factura;
import co.edu.uniquindio.envio.services.IEnvioServices;
import co.edu.uniquindio.envio.services.IRepartidorServices;
import co.edu.uniquindio.envio.services.IUsuarioServices;

import java.util.List;

public class EnvioFacadeImpl implements IEnvioFacade {

    private final IEnvioServices envioServices;
    private final IRepartidorServices<RepartidorDto> repartidorServices; // Asumo que el tipo genérico es RepartidorDto
    private final IUsuarioServices usuarioServices;

    public EnvioFacadeImpl(IEnvioServices envioServices, IRepartidorServices<RepartidorDto> repartidorServices, IUsuarioServices usuarioServices) {
        this.envioServices = envioServices;
        this.repartidorServices = repartidorServices;
        this.usuarioServices = usuarioServices;
    }

    @Override
    public boolean crearYAsignarEnvio(String idUsuario, EnvioDto envioDto, RepartidorDto repartidorDto) {
        boolean envioAgregado = envioServices.agregarEnvio(idUsuario, envioDto);
        if (envioAgregado) {
            envioServices.asignarEnvio(envioDto, repartidorDto);
            return true;
        }
        return false;
    }

    @Override
    public Factura procesarPagoEnvio(String idEnvio, MetodoPagoDto metodoPagoDto) {
        return envioServices.pagarEnvio(idEnvio, metodoPagoDto);
    }

    @Override
    public boolean registrarIncidenciaEnvio(String idEnvio, IncidenciaDto incidenciaDto) {
        EnvioDto envioDto = envioServices.obtenerEnvioDto(idEnvio);
        if (envioDto != null) {
            envioServices.registrarIncidencia(envioDto, incidenciaDto);
            return true;
        }
        return false;
    }
}
