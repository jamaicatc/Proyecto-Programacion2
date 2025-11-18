package co.edu.uniquindio.envio.factory.pago;

/**
 * Interfaz que define el contrato para los diferentes métodos de pago.
 * Este es el "Producto" en el patrón Factory Method.
 * Permite desacoplar el código que utiliza los métodos de pago de las implementaciones concretas.
 */
public interface Pago {
    /**
     * Procesa un pago por un monto específico.
     * @param monto El valor a pagar.
     * @return true si el pago fue exitoso, false en caso contrario.
     */
    boolean pagar(double monto);

    /**
     * Recarga el saldo del método de pago.
     * @param monto El valor a recargar.
     */
    void recargar(double monto);

    /**
     * Obtiene el saldo actual del método de pago.
     * @return El saldo disponible.
     */
    double getSaldo();
}
