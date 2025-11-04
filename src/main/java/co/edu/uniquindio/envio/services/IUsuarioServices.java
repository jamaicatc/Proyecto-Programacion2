package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import java.util.List;

public interface IUsuarioServices {
    List<UsuarioDto> obtenerUsuarios();
    boolean agregarUsuario(UsuarioDto usuarioDto);
    boolean eliminarUsuario(String idUsuario);
    boolean actualizarUsuario(UsuarioDto usuarioDto);
    UsuarioDto obtenerUsuarioPorNombre(String nombre);
    List<DireccionDto> obtenerDireccionesUsuario(String idUsuario);
    boolean agregarDireccion(String idUsuario, DireccionDto direccionDto);
    boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto);
    boolean eliminarDireccion(String idUsuario, String aliasDireccion);
}
