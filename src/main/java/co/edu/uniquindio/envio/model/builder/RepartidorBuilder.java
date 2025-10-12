package co.edu.uniquindio.envio.model.builder;

import co.edu.uniquindio.envio.model.Envio;
import co.edu.uniquindio.envio.model.EstadoDisponibilidad;
import co.edu.uniquindio.envio.model.Repartidor;

import java.util.List;

public class RepartidorBuilder {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoDisponibilidad disponibilidad;
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

    public RepartidorBuilder setDisponibilidad(EstadoDisponibilidad disponibilidad){
        this.disponibilidad = disponibilidad;
        return this;
    }

    public RepartidorBuilder setZonaCobertura(String zonaCobertura){
        this.zonaCobertura = zonaCobertura;
        return this;
    }

    public Repartidor build(){
        return new Repartidor(idRepartidor, nombre, documento, telefono,disponibilidad,zonaCobertura);
    }
}
