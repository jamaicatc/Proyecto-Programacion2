package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.utils.DataUtil;

public class AdministradorController {

    public boolean validarCredencialesAdministrador(String usuario, String contrasena) {
        return usuario.equals(DataUtil.ADMIN_USUARIO) && contrasena.equals(DataUtil.ADMIN_CONTRASENA);
    }
}
