package co.edu.uniquindio.envio.model;

public class Metrica {
    private String nombre;
    private String valor;
    private String unidad;
    private String fechaActualizacion;

    public Metrica(String nombre, String valor, String unidad, String fechaActualizacion) {
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }
}