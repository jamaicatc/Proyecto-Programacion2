package co.edu.uniquindio.envio.mapping.mappers;

import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IUsuarioMapping;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMappingImpl implements IUsuarioMapping {

    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return null;
        }
        List<UsuarioDto> listaUsuariosDto = new ArrayList<>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaUsuariosDto.add(usuarioToUsuarioDto(usuario));
        }
        return listaUsuariosDto;
    }

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getTelefono()
        );
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return null;
        return new Usuario(
                usuarioDto.idUsuario(),
                usuarioDto.nombreCompleto(),
                usuarioDto.correo(),
                usuarioDto.telefono()
        );
    }
}
