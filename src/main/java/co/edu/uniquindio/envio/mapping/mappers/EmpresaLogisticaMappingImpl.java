package co.edu.uniquindio.envio.mapping.mappers;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Direccion;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IDireccionMapping;
import co.edu.uniquindio.envio.services.IEmpresaLogisticaMapping;
import co.edu.uniquindio.envio.services.IRepartidorMapping;
import co.edu.uniquindio.envio.services.IUsuarioMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpresaLogisticaMappingImpl implements IEmpresaLogisticaMapping, IUsuarioMapping<Usuario, UsuarioDto>, IRepartidorMapping<Repartidor, RepartidorDto>, IDireccionMapping<Direccion, DireccionDto> {

    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return null;
        }
        return listaUsuarios.stream()
                .map(this::usuarioToUsuarioDto)
                .collect(Collectors.toList());
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
        return listaRepartidores.stream()
                .map(this::repartidorToRepartidorDto)
                .collect(Collectors.toList());
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

    @Override
    public DireccionDto direccionToDireccionDto(Direccion direccion) {
        if(direccion == null) return null;
        return new DireccionDto(
            direccion.getIdDireccion(),
            direccion.getAlias(),
            direccion.getCalle(),
            direccion.getCiudad(),
            direccion.getCoordenadas()
        );
    }

    @Override
    public Direccion direccionDtoToDireccion(DireccionDto direccionDto) {
        if(direccionDto == null) return null;
        return new Direccion(
            direccionDto.idDireccion(),
            direccionDto.alias(),
            direccionDto.calleCarrera(),
            direccionDto.ciudad(),
            direccionDto.coordenadas()
        );
    }

    @Override
    public List<DireccionDto> getDireccionesDto(List<Direccion> direcciones) {
        if(direcciones == null) return new ArrayList<>();
        return direcciones.stream()
                .map(this::direccionToDireccionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Direccion> getDirecciones(List<DireccionDto> direccionesDto) {
        if(direccionesDto == null) return new ArrayList<>();
        return direccionesDto.stream()
                .map(this::direccionDtoToDireccion)
                .collect(Collectors.toList());
    }
}
