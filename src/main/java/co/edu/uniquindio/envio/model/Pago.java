package co.edu.uniquindio.envio.model;


import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private double monto;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;

    public Pago() {
    }

    public Pago(String idPago, double monto, LocalDateTime fechaPago, MetodoPago metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
    }
}
