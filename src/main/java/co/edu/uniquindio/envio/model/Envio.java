package co.edu.uniquindio.envio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un envío con sus atributos y dimensiones.
 */
public class Envio {
    private String idEnvio;
    private LocalDate fecha;
    private LocalDate fechaEntregaEstimada;
    private String origen;
    private String destino;
    private String estado;
    private double peso;
    private double largo;
    private double alto;
    private double ancho;
    private double costo;
    private Factura factura;
    private List<String> historial;
    private boolean pago; // Nuevo atributo

    // Constructores
    public Envio() {
        this.historial = new ArrayList<>();
        this.pago = false; // Por defecto, un envío no está pagado
    }

    public Envio(String idEnvio, LocalDate fecha, LocalDate fechaEntregaEstimada, String origen, String destino, String estado, double peso, double largo, double alto, double ancho, double costo, Factura factura) {
        this();
        this.idEnvio = idEnvio;
        this.fecha = fecha;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.origen = origen;
        this.destino = destino;
        this.estado = estado;
        this.peso = peso;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.costo = costo;
        this.factura = factura;
    }

    public Envio(String idEnvio, LocalDate fecha, LocalDate fechaEntregaEstimada, String origen, String destino, String estado, double peso, double largo, double alto, double ancho, double costo, Factura factura, boolean pago) {
        this();
        this.idEnvio = idEnvio;
        this.fecha = fecha;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.origen = origen;
        this.destino = destino;
        this.estado = estado;
        this.peso = peso;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.costo = costo;
        this.factura = factura;
        this.pago = pago;
    }

    // Getters y Setters
    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public void setHistorial(List<String> historial) {
        this.historial = historial;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    /**
     * Calcula el volumen del paquete.
     * @return El volumen en cm³.
     */
    public double calcularVolumen() {
        return largo * alto * ancho;
    }
}