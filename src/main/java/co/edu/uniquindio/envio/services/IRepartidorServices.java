package co.edu.uniquindio.envio.services;

import java.util.List;

public interface IRepartidorServices<T> {
    List<T> obtenerRepartidores();
    boolean agregarRepartidor(T repartidor);
    boolean eliminarRepartidor(String idRepartidor);
    boolean actualizarRepartidor(T repartidor);
}
