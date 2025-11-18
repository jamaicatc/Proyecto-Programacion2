package co.edu.uniquindio.envio.model.state;

import co.edu.uniquindio.envio.model.Envio;

public class AsignadoState implements EnvioState {
    @Override
    public void solicitar(Envio envio) {
        // No action, already in Asignado state
    }

    @Override
    public void asignar(Envio envio) {
        // No action, already in Asignado state
    }

    @Override
    public void enRuta(Envio envio) {
        envio.setEstado(new EnRutaState());
    }

    @Override
    public void entregar(Envio envio) {
        // Cannot transition to Entregado from Asignado
    }

    @Override
    public void reportarIncidencia(Envio envio) {
        envio.setEstado(new IncidenciaState());
    }
}
