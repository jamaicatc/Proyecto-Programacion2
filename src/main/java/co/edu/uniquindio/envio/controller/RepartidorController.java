package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;

import java.util.List;

public class RepartidorController {
    ModelFactory modelFactory;
    public RepartidorController(){
        modelFactory = ModelFactory.getInstance();
    }

    public List<RepartidorDto> obtenerRepartidores() {
        return modelFactory.obtenerRepartidores();
    }

    public boolean agregarRepartidor(RepartidorDto repartidorDto) {
        return modelFactory.agregarRepartidor(repartidorDto);
    }

    public boolean eliminarRepartidor(String idRepartidor) {
        return modelFactory.eliminarRepartidor(idRepartidor);
    }
}
