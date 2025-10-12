package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Usuario;

import java.util.List;

public interface IEmpresaLogisticaMapping {
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);
}
