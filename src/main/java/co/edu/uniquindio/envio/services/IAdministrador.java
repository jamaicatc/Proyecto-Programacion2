package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.model.Administrador;

import java.util.List;

public interface IAdministrador {
    boolean agregarAdministrador(Administrador administrador);
    boolean eliminarAdministrador(String idAdministrador);
    boolean actualizarAdministrador(Administrador administrador);
    Administrador obtenerAdministrador(String idAdministrador);
    List<Administrador> listarAdministradores();
    Administrador autenticarAdministrador(String correo, String contrasena);
}
