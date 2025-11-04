package co.edu.uniquindio.envio.mapping.mappers;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.model.Direccion;

public class DireccionMapper {

    public static DireccionDto toDto(Direccion direccion) {
        return new DireccionDto(
            direccion.getIdDireccion(),
            direccion.getAlias(),
            direccion.getCalle(),
            direccion.getCiudad(),
            direccion.getCoordenadas()
        );
    }

    public static Direccion toEntity(DireccionDto dto) {
        return new Direccion(
            dto.idDireccion(),
            dto.alias(),
            dto.calleCarrera(),
            dto.ciudad(),
            dto.coordenadas()
        );
    }
}
