package co.edu.uniquindio.envio.factory.pago;

/**
 * Fábrica concreta para crear instancias de PagoEfectivo.
 * Este es un "Creador Concreto" en el patrón Factory Method.
 * Su responsabilidad es instanciar y retornar un objeto PagoEfectivo.
 */
public class PagoEfectivoFactory implements PagoFactory {
    @Override
    public Pago crearPago() {
        return new PagoEfectivo();
    }
}
