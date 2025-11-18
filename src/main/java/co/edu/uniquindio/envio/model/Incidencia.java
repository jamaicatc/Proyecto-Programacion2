package co.edu.uniquindio.envio.model;

import java.time.LocalDate;

public class Incidencia {
    private String asunto;
    private String descripcion;
    private LocalDate fecha;

    public Incidencia(String asunto, String descripcion, LocalDate fecha) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
