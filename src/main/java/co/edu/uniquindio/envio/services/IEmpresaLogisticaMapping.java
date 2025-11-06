package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.*;
import co.edu.uniquindio.envio.model.*;

import java.util.List;

public interface IEmpresaLogisticaMapping extends IUsuarioMapping<Usuario, UsuarioDto>, IRepartidorMapping<Repartidor, RepartidorDto>, IDireccionMapping<Direccion, DireccionDto>, IEnvioMapping, IMetodoPagoMapping {
    List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores);
    Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto);
}
