package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Usuario;

import java.util.List;

public interface IUsuarioMapping<T extends Usuario, D extends UsuarioDto> {
    List<D> getUsuariosDto(List<T> listaUsuarios);
    D usuarioToUsuarioDto(T usuario);
    T usuarioDtoToUsuario(D usuarioDto);
}
