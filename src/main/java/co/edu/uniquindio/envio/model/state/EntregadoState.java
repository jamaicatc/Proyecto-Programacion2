package co.edu.uniquindio.envio.model.state;

import co.edu.uniquindio.envio.model.Envio;

public class EntregadoState implements EnvioState {
    @Override
    public void solicitar(Envio envio) {
        // No action, already delivered
    }

    @Override
    public void asignar(Envio envio) {
        // No action, already delivered
    }

    @Override
    public void enRuta(Envio envio) {
        // No action, already delivered
    }

    @Override
    public void entregar(Envio envio) {
        // No action, already delivered
    }

    @Override
    public void reportarIncidencia(Envio envio) {
        // Cannot report incidence for an already delivered shipment
    }
}
