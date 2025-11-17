package co.edu.uniquindio.envio.factory.pago;

/**
 * Fábrica concreta para crear instancias de PagoTarjeta.
 * Este es un "Creador Concreto" en el patrón Factory Method.
 * Su responsabilidad es instanciar y retornar un objeto PagoTarjeta.
 */
public class PagoTarjetaFactory implements PagoFactory {
    @Override
    public Pago crearPago() {
        return new PagoTarjeta();
    }
}
