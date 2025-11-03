package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.utils.DataUtil;

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

    public boolean actualizarRepartidor(RepartidorDto repartidorDto) {
        return modelFactory.actualizarRepartidor(repartidorDto);
    }

    public static boolean validarCredencialesRepartidor(String usuario, String contrasena, String rol){
        return usuario.equals(DataUtil.REPARTIDOR_USUARIO) && contrasena.equals(DataUtil.REPARTIDOR_CONTRASENA) && rol.equals(DataUtil.REPARTIDOR_ROL);
    }
}
