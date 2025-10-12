package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.model.Usuario;

import java.util.List;

public interface IUsuario {
    boolean agregarUsuario(Usuario usuario);
    boolean eliminarUsuario(String idUsuario);
    boolean actualizarUsuario(Usuario usuario);
    Usuario obtenerUsuario(String idUsuario);
    List<Usuario> listarUsuarios();
    Usuario autenticarUsuario(String correo, String contrasena);
}
