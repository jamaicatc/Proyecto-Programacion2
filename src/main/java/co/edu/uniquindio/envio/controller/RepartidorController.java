package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.model.Repartidor;

public class RepartidorController {
    public boolean validarCredencialesRepartidor(String usuario, String contrasena) {
        ModelFactory modelFactory = ModelFactory.getInstance();
        for (RepartidorDto rDto : modelFactory.obtenerRepartidores()) {
            if (rDto.usuario().equals(usuario) && rDto.contrasena().equals(contrasena)) {
                Repartidor repartidorEncontrado = modelFactory.obtenerRepartidorOriginal(rDto.idRepartidor());
                modelFactory.setUsuarioActual(repartidorEncontrado);
                return true;
            }
        }
        return false;
    }
}
