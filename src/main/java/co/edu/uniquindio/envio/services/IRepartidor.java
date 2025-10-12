package co.edu.uniquindio.envio.services;

import co.edu.uniquindio.envio.model.Repartidor;

import java.util.List;

public interface IRepartidor {
    boolean agregarRepartidor(Repartidor repartidor);
    boolean eliminarRepartidor(String idRepartidor);
    boolean actualizarRepartidor(Repartidor repartidor);
    Repartidor obtenerRepartidor(String idRepartidor);
    List<Repartidor> listarRepartidores();
}
