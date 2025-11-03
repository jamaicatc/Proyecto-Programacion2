package co.edu.uniquindio.envio.services;

import java.util.List;

public interface IUsuarioServices<T> {
    List<T> obtenerUsuarios();
    boolean agregarUsuario(T usuario);
    boolean eliminarUsuario(String idUsuario);
    boolean actualizarUsuario(T usuario);
}
