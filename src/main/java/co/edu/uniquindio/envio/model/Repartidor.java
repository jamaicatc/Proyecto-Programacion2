package co.edu.uniquindio.envio.model;

import co.edu.uniquindio.envio.model.builder.DireccionBuilder;
import co.edu.uniquindio.envio.model.builder.RepartidorBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String usuario;
    private String contrasena;
    private String disponibilidad;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor() {
        this.enviosAsignados = new ArrayList<>();
    }

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, String usuario, String contrasena, String disponibilidad, String zonaCobertura) {
        this();
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.disponibilidad = disponibilidad;
        this.zonaCobertura = zonaCobertura;
    }

    public static RepartidorBuilder builder(){
        return new RepartidorBuilder();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getDisponibilidad() {
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

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public void setEnviosAsignados(List<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repartidor that = (Repartidor) o;
        return Objects.equals(idRepartidor, that.idRepartidor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRepartidor);
    }
}
