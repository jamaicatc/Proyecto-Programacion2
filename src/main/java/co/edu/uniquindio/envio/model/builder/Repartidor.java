package co.edu.uniquindio.envio.model.builder;

import java.util.ArrayList;
import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoDisponibilidad disponibilidad;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor() {
        this.enviosAsignados = new ArrayList<>();
    }

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, EstadoDisponibilidad disponibilidad, String zonaCobertura) {
        this();
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.disponibilidad = disponibilidad;
        this.zonaCobertura = zonaCobertura;
    }
}
