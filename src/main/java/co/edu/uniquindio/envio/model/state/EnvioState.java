package co.edu.uniquindio.envio.model.state;

import co.edu.uniquindio.envio.model.Envio;

public interface EnvioState {
    void solicitar(Envio envio);
    void asignar(Envio envio);
    void enRuta(Envio envio);
    void entregar(Envio envio);
    void reportarIncidencia(Envio envio);
}
