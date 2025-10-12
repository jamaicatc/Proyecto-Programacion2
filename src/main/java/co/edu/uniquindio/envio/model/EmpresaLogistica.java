package co.edu.uniquindio.envio.model;

import co.edu.uniquindio.envio.services.IEmpresaLogistica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpresaLogistica implements IEmpresaLogistica {
    private String nombre;

    private Map<String, Usuario> listaUsuarios;
    private Map<String, Repartidor> listaRepartidores;
    private Map<String, Envio> listaEnvios;
    private Map<String, Pago> listaPagos;
    private Map<String, Administrador> listaAdministradores;
    private Map<String, Incidencia> listaIncidencias;

    public EmpresaLogistica() {
        this.nombre = "Mercado Cerrado";
        this.listaUsuarios = new HashMap<>();
        this.listaRepartidores = new HashMap<>();
        this.listaEnvios = new HashMap<>();
        this.listaPagos = new HashMap<>();
        this.listaAdministradores = new HashMap<>();
        this.listaIncidencias = new HashMap<>();
    }

    public EmpresaLogistica(String nombre) {
        this();
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Map<String, Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public Map<String, Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    public Map<String, Envio> getListaEnvios() {
        return listaEnvios;
    }

    public Map<String, Pago> getListaPagos() {
        return listaPagos;
    }

    public Map<String, Administrador> getListaAdministradores() {
        return listaAdministradores;
    }

    public Map<String, Incidencia> getListaIncidencias() {
        return listaIncidencias;
    }

    //CRUD Administrador
    @Override
    public boolean agregarAdministrador(Administrador administrador) {
        return false;
    }

    @Override
    public boolean eliminarAdministrador(String idAdministrador) {
        return false;
    }

    @Override
    public boolean actualizarAdministrador(Administrador administrador) {
        return false;
    }

    @Override
    public Administrador obtenerAdministrador(String idAdministrador) {
        return null;
    }

    @Override
    public List<Administrador> listarAdministradores() {
        return List.of();
    }

    @Override
    public Administrador autenticarAdministrador(String correo, String contrasena) {
        return null;
    }

    //CRUD Repartidor

    @Override
    public boolean agregarRepartidor(Repartidor repartidor) {
        return false;
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        return false;
    }

    @Override
    public boolean actualizarRepartidor(Repartidor repartidor) {
        return false;
    }

    @Override
    public Repartidor obtenerRepartidor(String idRepartidor) {
        return null;
    }

    @Override
    public List<Repartidor> listarRepartidores() {
        return List.of();
    }

    //CRUD Usuario

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        return false;
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        return false;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        return false;
    }

    @Override
    public Usuario obtenerUsuario(String idUsuario) {
        return null;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }

    @Override
    public Usuario autenticarUsuario(String correo, String contrasena) {
        return null;
    }
}
