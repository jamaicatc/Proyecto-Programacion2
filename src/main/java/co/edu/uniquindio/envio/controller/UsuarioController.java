package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.utils.DataUtil;

import java.util.List;

public class UsuarioController {
    ModelFactory modelFactory;
    public UsuarioController(){
        modelFactory = ModelFactory.getInstance();
    }

    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactory.agregarUsuario(usuarioDto);
    }

    public boolean eliminarUsuario(String idUsuario) {
        return modelFactory.eliminarUsuario(idUsuario);
    }

    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        return modelFactory.actualizarUsuario(usuarioDto);
    }

    public static boolean validarCredencialesUsuario(String usuario, String contrasena, String rol){
        return usuario.equals(DataUtil.USUARIO_USUARIO) && contrasena.equals(DataUtil.USUARIO_CONTRASENA) && rol.equals(DataUtil.USUARIO_ROL);
    }

}
