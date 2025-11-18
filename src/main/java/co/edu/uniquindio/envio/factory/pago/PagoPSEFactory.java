package co.edu.uniquindio.envio.factory.pago;

/**
 * Fábrica concreta para crear instancias de PagoPSE.
 * Este es un "Creador Concreto" en el patrón Factory Method.
 * Su responsabilidad es instanciar y retornar un objeto PagoPSE.
 */
public class PagoPSEFactory implements PagoFactory {
    @Override
    public Pago crearPago() {
        return new PagoPSE();
    }
}
