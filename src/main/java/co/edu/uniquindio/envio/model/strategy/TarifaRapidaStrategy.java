package co.edu.uniquindio.envio.model.strategy;

import co.edu.uniquindio.envio.model.Envio;

/**
 * Estrategia de tarifa rápida. Aplica un recargo sobre la tarifa estándar.
 */
public class TarifaRapidaStrategy implements ITarifaStrategy {
    private static final double RECARGO_RAPIDO = 1.5; // 50% de recargo

    @Override
    public double calcularTarifa(Envio envio) {
        TarifaEstandarStrategy tarifaEstandar = new TarifaEstandarStrategy();
        double costoBase = tarifaEstandar.calcularTarifa(envio);
        return costoBase * RECARGO_RAPIDO;
    }
}
