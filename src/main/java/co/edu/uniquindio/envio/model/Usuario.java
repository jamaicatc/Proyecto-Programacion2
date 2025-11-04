package co.edu.uniquindio.envio.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private Map<String, Direccion> direccionesFrecuentes;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Map<String, Direccion> getDireccionesFrecuentes() {
        return direccionesFrecuentes;
    }

    public void setDireccionesFrecuentes(Map<String, Direccion> direccionesFrecuentes) {
        this.direccionesFrecuentes = direccionesFrecuentes;
    }

    public HashMap<String, MetodoPago> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(HashMap<String, MetodoPago> metodosPago) {
        this.metodosPago = metodosPago;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

    public boolean agregarDireccion(String alias, Direccion direccion) {
        if (!direccionesFrecuentes.containsKey(alias)) {
            direccionesFrecuentes.put(alias, direccion);
            return true;
        }
        return false;
    }

    public boolean actualizarDireccion(String alias, Direccion direccion) {
        if (direccionesFrecuentes.containsKey(alias)) {
            direccionesFrecuentes.put(alias, direccion);
            return true;
        }
        return false;
    }

    public boolean eliminarDireccion(String alias) {
        return direccionesFrecuentes.remove(alias) != null;
    }
}
