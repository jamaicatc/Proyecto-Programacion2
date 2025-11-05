package co.edu.uniquindio.envio.model.strategy;

import co.edu.uniquindio.envio.model.Envio;

/**
 * Estrategia de tarifa con seguro. Añade un porcentaje del valor declarado del paquete.
 */
public class TarifaConSeguroStrategy implements ITarifaStrategy {
    private static final double PORCENTAJE_SEGURO = 0.05; // 5% del valor declarado

    @Override
    public double calcularTarifa(Envio envio) {
        // En una implementación real, el valor declarado vendría del envío.
        // Para este ejemplo, se asume un valor declarado fijo.
        double valorDeclarado = 100000; // $100,000
        TarifaEstandarStrategy tarifaEstandar = new TarifaEstandarStrategy();
        double costoBase = tarifaEstandar.calcularTarifa(envio);
        return costoBase + (valorDeclarado * PORCENTAJE_SEGURO);
    }
}
