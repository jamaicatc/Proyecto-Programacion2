package co.edu.uniquindio.envio.model.builder;


public class Administrador {
    private String idAdministrador;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String usuario;
    private String contrasena;

    public Administrador() {
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
