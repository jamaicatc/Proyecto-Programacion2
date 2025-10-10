package co.edu.uniquindio.envio.model.builder;

import java.util.HashMap;
import java.util.Map;

public class Administrador {
    private String idAdministrador;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String usuario;
    private String contrasena;
    private Map<String, Usuario> usuariosGestionados;
    private Map<String, Repartidor> repartidoresGestionados;
    private Map<String, Envio> enviosSupervisados;

    public Administrador() {
        this.usuariosGestionados = new HashMap<>();
        this.repartidoresGestionados = new HashMap<>();
        this.enviosSupervisados = new HashMap<>();
    }

    public Administrador(String idAdministrador, String nombreCompleto, String correo, String telefono, String usuario, String contrasena) {
        this();
        this.idAdministrador = idAdministrador;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
}
