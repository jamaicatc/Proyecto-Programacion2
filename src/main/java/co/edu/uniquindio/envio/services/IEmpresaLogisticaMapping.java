package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Direccion;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;

import java.util.List;

public interface IEmpresaLogisticaMapping extends IUsuarioMapping<Usuario, UsuarioDto>, IRepartidorMapping<Repartidor, RepartidorDto>, IDireccionMapping<Direccion, DireccionDto>, IEnvioMapping {
    List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores);
    Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto);
}
