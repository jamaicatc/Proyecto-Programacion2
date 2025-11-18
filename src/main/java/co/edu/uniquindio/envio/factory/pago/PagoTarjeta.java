package co.edu.uniquindio.envio.factory.pago;

/**
 * Implementación de la interfaz Pago para pagos con tarjeta.
 * Este es un "Producto Concreto" en el patrón Factory Method.
 */
public class PagoTarjeta implements Pago {
    private double saldo;

    public PagoTarjeta() {
        this.saldo = 1000.0; // Saldo inicial de ejemplo
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
