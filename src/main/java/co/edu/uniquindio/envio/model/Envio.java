package co.edu.uniquindio.envio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double largo;
    private double alto;
    private double ancho;
    private int cantidad;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntregaEstimada;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<ServicioAdicional> serviciosAdicionales;
    private Pago pago;

    public Envio() {
        this.serviciosAdicionales = new ArrayList<>();
    }

    public Envio(String idEnvio, Direccion origen, Direccion destino, double peso, double largo, double alto, double ancho, int cantidad, double costo, EstadoEnvio estado, LocalDateTime fechaCreacion, LocalDateTime fechaEntregaEstimada, Usuario usuario, Repartidor repartidor, Pago pago) {
        this();
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.largo = largo;
        this.alto = alto;
        this.ancho = ancho;
        this.cantidad = cantidad;
        this.costo = costo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.usuario = usuario;
        this.repartidor = repartidor;
        this.pago = pago;
    }
}
