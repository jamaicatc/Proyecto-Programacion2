package co.edu.uniquindio.envio.factory.pago;

/**
 * Interfaz de la fábrica para crear objetos de tipo Pago.
 * Este es el "Creador" en el patrón Factory Method.
 * Define el método `crearPago()` que las subclases implementarán para instanciar un producto concreto.
 */
public interface PagoFactory {
    /**
     * Crea y retorna una instancia de un método de pago.
     * @return una instancia de Pago.
     */
    Pago crearPago();
}
