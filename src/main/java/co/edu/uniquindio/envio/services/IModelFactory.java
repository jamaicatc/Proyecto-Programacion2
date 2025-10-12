package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;

import java.util.List;

public interface IModelFactory {
    List<UsuarioDto> obtenerUsuarios();
    boolean agregarUsuario(UsuarioDto usuarioDto);
    boolean eliminarUsuario(String idUsuario);
    boolean actualizarUsuario(UsuarioDto usuarioDto);

    List<RepartidorDto> obtenerRepartidores();
    boolean agregarRepartidor(RepartidorDto repartidorDto);
    boolean eliminarRepartidor(String idRepartidor);
    boolean actualizarRepartidor(RepartidorDto repartidorDto);

}
