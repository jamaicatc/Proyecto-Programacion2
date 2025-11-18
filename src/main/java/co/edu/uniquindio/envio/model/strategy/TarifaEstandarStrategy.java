package co.edu.uniquindio.envio.model.strategy;

import co.edu.uniquindio.envio.model.Envio;

/**
 * Estrategia de tarifa estándar. El costo se basa en el peso y el volumen.
 */
public class TarifaEstandarStrategy implements ITarifaStrategy {
    private static final double TARIFA_POR_KG = 500; // $500 por kg
    private static final double TARIFA_POR_VOLUMEN = 2; // $2 por cm³

    @Override
    public double calcularTarifa(Envio envio) {
        double costoPorPeso = envio.getPeso() * TARIFA_POR_KG;
        double costoPorVolumen = envio.calcularVolumen() * TARIFA_POR_VOLUMEN;
        return costoPorPeso + costoPorVolumen;
    }
}
