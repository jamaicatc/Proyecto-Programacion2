package co.edu.uniquindio.envio.model;

import java.time.LocalDateTime;

public class Incidencia {
    private String idIncidencia;
    private String descripcion;
    private String tipo;
    private LocalDateTime fecha;
    private Envio envio;

    public Incidencia() {
    }

    public Incidencia(String idIncidencia, String descripcion, String tipo, LocalDateTime fecha, Envio envio) {
        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.envio = envio;
    }
}
