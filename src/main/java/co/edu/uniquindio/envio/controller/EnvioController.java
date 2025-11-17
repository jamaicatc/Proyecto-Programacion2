package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.IncidenciaDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Factura;
import co.edu.uniquindio.envio.model.facade.EnvioFacadeImpl;
import co.edu.uniquindio.envio.model.facade.IEnvioFacade;
import co.edu.uniquindio.envio.services.IRepartidorServices;

import java.util.List;

public class EnvioController {

    private final ModelFactory modelFactory;
    private final IEnvioFacade envioFacade;

    public EnvioController() {
        this.modelFactory = ModelFactory.getInstance();
        this.envioFacade = new EnvioFacadeImpl(
                modelFactory.getEnvioServices(),
                (IRepartidorServices<RepartidorDto>) modelFactory,
                modelFactory.getUsuarioServices()
        );
    }

    public List<EnvioDto> obtenerEnvios() {
        return modelFactory.obtenerEnvios();
    }

    public List<EnvioDto> obtenerEnvios(String idUsuario) {
        return modelFactory.obtenerEnvios(idUsuario);
    }

    public boolean agregarEnvio(String idUsuario, EnvioDto envioDto) {
        return modelFactory.agregarEnvio(idUsuario, envioDto);
    }

    public boolean actualizarEnvio(EnvioDto envioDto) {
        return modelFactory.actualizarEnvio(envioDto);
    }

    public boolean eliminarEnvio(String idEnvio) {
        return modelFactory.eliminarEnvio(idEnvio);
    }

    public EnvioDto obtenerEnvio(String numeroSeguimiento) {
        return modelFactory.obtenerEnvio(numeroSeguimiento);
    }

    public boolean crearYAsignarNuevoEnvio(String idUsuario, EnvioDto envioDto, RepartidorDto repartidorDto) {
        return envioFacade.crearYAsignarEnvio(idUsuario, envioDto, repartidorDto);
    }

    public Factura procesarPagoDeEnvio(String idEnvio, MetodoPagoDto metodoPagoDto) {
        return envioFacade.procesarPagoEnvio(idEnvio, metodoPagoDto);
    }

    public boolean registrarIncidenciaEnEnvio(String idEnvio, IncidenciaDto incidenciaDto) {
        return envioFacade.registrarIncidenciaEnvio(idEnvio, incidenciaDto);
    }
}
