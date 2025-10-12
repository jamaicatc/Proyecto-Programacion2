package co.edu.uniquindio.envio.mapping.mappers;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IEmpresaLogisticaMapping;

import java.util.ArrayList;
import java.util.List;

public class EmpresaLogisticaMappingImpl implements IEmpresaLogisticaMapping {

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

    @Override
    public List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores) {
        if (listaRepartidores == null) {
            return null;
        }
        List<RepartidorDto> listaRepartidoresDto = new ArrayList<>(listaRepartidores.size());
        for (Repartidor repartidor : listaRepartidores) {
            listaRepartidoresDto.add(repartidorToRepartidorDto(repartidor));
        }
        return listaRepartidoresDto;
    }

    @Override
    public RepartidorDto repartidorToRepartidorDto(Repartidor repartidor) {
        if (repartidor == null) return null;
        return new RepartidorDto(
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getDocumento(),
                repartidor.getTelefono()
        );
    }

    @Override
    public Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto) {
        if (repartidorDto == null) return null;
        return Repartidor.builder()
                .setIdRepartidor(repartidorDto.idRepartidor())
                .setNombre(repartidorDto.nombre())
                .setDocumento(repartidorDto.documento())
                .setTelefono(repartidorDto.telefono())
                .build();
    }
}
