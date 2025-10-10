package co.edu.uniquindio.envio.model.builder;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private double monto;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;
    private EstadoPago estadoPago;

    public Pago() {
    }

    public Pago(String idPago, double monto, LocalDateTime fechaPago, MetodoPago metodoPago, EstadoPago estadoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
    }
}
