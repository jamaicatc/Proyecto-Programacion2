package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;

import java.util.List;

public interface IEmpresaLogisticaMapping {
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);
    List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores);
    RepartidorDto repartidorToRepartidorDto(Repartidor repartidor);
    Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto);
}
