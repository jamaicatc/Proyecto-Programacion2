package co.edu.uniquindio.envio.model.builder;

import java.time.LocalDate;

public class Reporte {
    private String idReporte;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String formato;
    private double valorTotal;

    public Reporte() {
    }

    public Reporte(String idReporte, String tipo, LocalDate fechaInicio, LocalDate fechaFin, String formato, double valorTotal) {
        this.idReporte = idReporte;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.formato = formato;
        this.valorTotal = valorTotal;
    }
}
