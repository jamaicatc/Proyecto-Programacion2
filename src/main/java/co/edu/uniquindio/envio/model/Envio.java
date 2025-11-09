package co.edu.uniquindio.envio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String id;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private String direccionOrigen;
    private String direccionDestino;
    private String estadoActual;
    private double peso;
    private double largo;
    private double alto;
    private double ancho;
    private double costo;
    private Repartidor repartidorAsignado;
    private boolean pago; // Nuevo atributo
    private List<String> historial;
    private List<Incidencia> incidencias;
    private Factura factura; // Nuevo atributo para la factura

    public Envio(String id, LocalDate fechaCreacion, LocalDate fechaEntrega, String direccionOrigen, String direccionDestino, String estadoActual, double peso, double largo, double alto, double ancho, double costo, Repartidor repartidorAsignado, boolean pago) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.estadoActual = estadoActual;
        this.peso = peso;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.costo = costo;
        this.repartidorAsignado = repartidorAsignado;
        this.pago = pago; // Inicializar el nuevo atributo
        this.historial = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.factura = null; // Inicializar la factura como nula
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public void setRepartidorAsignado(Repartidor repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public void setHistorial(List<String> historial) {
        this.historial = historial;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public double calcularVolumen() {
        return largo * alto * ancho;
    }
}
