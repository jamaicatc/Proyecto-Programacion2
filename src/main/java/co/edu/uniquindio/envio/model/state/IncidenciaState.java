package co.edu.uniquindio.envio.model.state;

import co.edu.uniquindio.envio.model.Envio;

public class IncidenciaState implements EnvioState {
    @Override
    public void solicitar(Envio envio) {
        // No action, cannot solicit again with an incidence
    }

    @Override
    public void asignar(Envio envio) {
        // No action, cannot assign with an incidence
    }

    @Override
    public void enRuta(Envio envio) {
        // No action, cannot set to enRuta with an incidence
    }

    @Override
    public void entregar(Envio envio) {
        // No action, cannot deliver with an incidence
    }

    @Override
    public void reportarIncidencia(Envio envio) {
        // No action, already has an incidence
    }
}
