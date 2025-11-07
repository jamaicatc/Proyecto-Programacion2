package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
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
    List<MetodoPagoDto> obtenerMetodosPago(String idUsuario);
    boolean agregarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto);
    boolean actualizarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto);
    boolean eliminarMetodoPago(String idUsuario, String aliasMetodoPago);
}
