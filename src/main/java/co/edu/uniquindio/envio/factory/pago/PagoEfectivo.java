package co.edu.uniquindio.envio.factory.pago;

/**
 * Implementación de la interfaz Pago para pagos en efectivo.
 * Este es un "Producto Concreto" en el patrón Factory Method.
 * En este caso, el saldo es "infinito" o no aplica, pero se mantiene por la interfaz.
 */
public class PagoEfectivo implements Pago {
    private double saldo;

    public PagoEfectivo() {
        // Para efectivo, el concepto de saldo puede ser diferente.
        // Se puede asumir que siempre hay saldo o manejarlo de otra forma.
        this.saldo = Double.MAX_VALUE;
    }

    @Override
    public boolean pagar(double monto) {
        // El pago en efectivo siempre es exitoso si se tiene el dinero físico.
        return true;
    }

    @Override
    public void recargar(double monto) {
        // No aplica para efectivo en este contexto.
    }

    @Override
    public double getSaldo() {
        return saldo;
    }
}
