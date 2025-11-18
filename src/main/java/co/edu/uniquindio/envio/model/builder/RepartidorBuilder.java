package co.edu.uniquindio.envio.model.builder;

import co.edu.uniquindio.envio.model.Repartidor;

public class RepartidorBuilder {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String usuario;
    private String contrasena;
    private String disponibilidad;
    private String zonaCobertura;

    public RepartidorBuilder setIdRepartidor(String idRepartidor){
        this.idRepartidor = idRepartidor;
        return this;
    }

    public RepartidorBuilder setNombre(String nombre){
        this.nombre = nombre;
        return this;
    }

    public RepartidorBuilder setDocumento(String documento){
        this.documento = documento;
        return this;
    }

    public RepartidorBuilder setTelefono(String telefono){
        this.telefono = telefono;
        return this;
    }

    public RepartidorBuilder setUsuario(String usuario){
        this.usuario = usuario;
        return this;
    }

    public RepartidorBuilder setContrasena(String contrasena){
        this.contrasena = contrasena;
        return this;
    }

    public RepartidorBuilder setDisponibilidad(String disponibilidad){
        this.disponibilidad = disponibilidad;
        return this;
    }

    public RepartidorBuilder setZonaCobertura(String zonaCobertura){
        this.zonaCobertura = zonaCobertura;
        return this;
    }

    public Repartidor build(){
        return new Repartidor(idRepartidor, nombre, documento, telefono, usuario, contrasena, disponibilidad,zonaCobertura);
    }
}
