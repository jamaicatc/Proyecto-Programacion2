package co.edu.uniquindio.envio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private String dimensiones;
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

    public Envio(String idEnvio, Direccion origen, Direccion destino, double peso, String dimensiones, double costo, EstadoEnvio estado, LocalDateTime fechaCreacion, LocalDateTime fechaEntregaEstimada, Usuario usuario, Repartidor repartidor, Pago pago) {
        this();
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.costo = costo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.usuario = usuario;
        this.repartidor = repartidor;
        this.pago = pago;
    }
}
