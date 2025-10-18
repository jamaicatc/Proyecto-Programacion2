package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.mapping.dto.RepartidorDto;

import java.util.List;

public interface IRepartidorServices<T> {
    List<T> obtenerRepartidores();
    boolean agregarRepartidor(T repartidor);
    boolean eliminarRepartidor(String idRepartidor);
    boolean actualizarRepartidor(T repartidor);
}
