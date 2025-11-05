package co.edu.uniquindio.envio.model.strategy;

import co.edu.uniquindio.envio.model.Envio;

/**
 * Clase de contexto para el patrón Strategy. Gestiona la estrategia de cálculo de tarifas.
 */
public class TarifaBase {
    private ITarifaStrategy estrategia;

    /**
     * Establece la estrategia de cálculo a utilizar.
     * @param estrategia La estrategia de cálculo.
     */
    public void setEstrategia(ITarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Calcula la tarifa utilizando la estrategia actual.
     * @param envio El envío para el cual se calculará la tarifa.
     * @return El costo del envío.
     */
    public double calcular(Envio envio) {
        if (estrategia == null) {
            throw new IllegalStateException("No se ha establecido una estrategia de tarifa.");
        }
        return estrategia.calcularTarifa(envio);
    }
}
