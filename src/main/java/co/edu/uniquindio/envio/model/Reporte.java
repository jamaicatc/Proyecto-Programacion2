package co.edu.uniquindio.envio.model;

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

    // Getters
    public String getIdReporte() {
        return idReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getFormato() {
        return formato;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    // Setters
    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
