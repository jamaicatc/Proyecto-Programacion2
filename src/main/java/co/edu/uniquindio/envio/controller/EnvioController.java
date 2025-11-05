package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;

import java.util.List;

public class EnvioController {

    private final ModelFactory modelFactory;

    public EnvioController() {
        this.modelFactory = ModelFactory.getInstance();
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
}
