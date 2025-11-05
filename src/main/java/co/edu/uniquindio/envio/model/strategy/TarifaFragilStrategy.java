package co.edu.uniquindio.envio.model.strategy;

import co.edu.uniquindio.envio.model.Envio;

/**
 * Estrategia de tarifa para paquetes frágiles. Añade un costo fijo por manejo especial.
 */
public class TarifaFragilStrategy implements ITarifaStrategy {
    private static final double COSTO_MANEJO_FRAGIL = 15000; // $15,000 fijos

    @Override
    public double calcularTarifa(Envio envio) {
        TarifaEstandarStrategy tarifaEstandar = new TarifaEstandarStrategy();
        double costoBase = tarifaEstandar.calcularTarifa(envio);
        return costoBase + COSTO_MANEJO_FRAGIL;
    }
}
