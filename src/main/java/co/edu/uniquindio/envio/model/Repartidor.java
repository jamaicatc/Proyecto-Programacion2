package co.edu.uniquindio.envio.model;

import co.edu.uniquindio.envio.model.builder.DireccionBuilder;
import co.edu.uniquindio.envio.model.builder.RepartidorBuilder;

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

    public static RepartidorBuilder builder(){
        return new RepartidorBuilder();
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public EstadoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public List<Envio> getEnviosAsignados() {
        return enviosAsignados;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDisponibilidad(EstadoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public void setEnviosAsignados(List<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }
}
