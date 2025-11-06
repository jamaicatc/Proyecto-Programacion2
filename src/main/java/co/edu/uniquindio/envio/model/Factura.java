package co.edu.uniquindio.envio.model;

import java.time.LocalDateTime;

/**
 * Representa el comprobante de pago de un envío.
 */
public class Factura {
    private String idFactura;
    private LocalDateTime fecha;
    private double monto;
    private MetodoPago metodoPago;

    // Constructores
    public Factura() {}

    public Factura(String idFactura, LocalDateTime fecha, double monto, MetodoPago metodoPago) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.monto = monto;
        this.metodoPago = metodoPago;
    }

    // Getters y Setters
    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
