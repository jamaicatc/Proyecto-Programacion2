package co.edu.uniquindio.envio.model;

public class Tarifa {
    private String idTarifa;
    private double distancia;
    private double peso;
    private double volumen;
    private boolean prioridad;
    private double recargos;
    private double costoTotal;

    public Tarifa() {
    }

    public Tarifa(String idTarifa, double distancia, double peso, double volumen, boolean prioridad, double recargos, double costoTotal) {
        this.idTarifa = idTarifa;
        this.distancia = distancia;
        this.peso = peso;
        this.volumen = volumen;
        this.prioridad = prioridad;
        this.recargos = recargos;
        this.costoTotal = costoTotal;
    }
}
