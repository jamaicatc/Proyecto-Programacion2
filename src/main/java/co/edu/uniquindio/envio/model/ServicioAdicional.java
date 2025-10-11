package co.edu.uniquindio.envio.model;

public class ServicioAdicional {
    private String idServicio;
    private String nombre;
    private double costo;

    public ServicioAdicional() {
    }

    public ServicioAdicional(String idServicio, String nombre, double costo) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.costo = costo;
    }
}
