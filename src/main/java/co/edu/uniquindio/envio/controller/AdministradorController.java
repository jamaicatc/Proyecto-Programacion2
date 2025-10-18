package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.utils.DataUtil;

public class AdministradorController {
    public static boolean validarCredencialesAdministrador(String usuario, String contrasena, String rol){
        return usuario.equals(DataUtil.ADMIN_USUARIO) && contrasena.equals(DataUtil.ADMIN_CONTRASENA) && rol.equals(DataUtil.ADMIN_ROL);
    }
}
