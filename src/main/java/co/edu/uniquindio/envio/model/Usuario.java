package co.edu.uniquindio.envio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private HashMap<String, Direccion> direccionesFrecuentes;
    private HashMap<String, MetodoPago> metodosPago;
    private List<Envio> envios;

    public Usuario() {
        this.direccionesFrecuentes = new HashMap<>();
        this.metodosPago = new HashMap<>();
        this.envios = new ArrayList<>();
    }

    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono) {
        this();
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
    }
}
