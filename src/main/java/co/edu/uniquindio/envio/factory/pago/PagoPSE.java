package co.edu.uniquindio.envio.factory.pago;

/**
 * Implementación de la interfaz Pago para pagos con PSE.
 * Este es un "Producto Concreto" en el patrón Factory Method.
 */
public class PagoPSE implements Pago {
    private double saldo;

    public PagoPSE() {
        this.saldo = 500.0; // Saldo inicial de ejemplo
    }

    @Override
    public boolean pagar(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    @Override
    public void recargar(double monto) {
        this.saldo += monto;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }
}
