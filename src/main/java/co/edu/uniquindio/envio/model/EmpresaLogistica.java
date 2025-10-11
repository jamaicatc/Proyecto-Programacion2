package co.edu.uniquindio.envio.model;

import java.util.HashMap;
import java.util.Map;

public class EmpresaLogistica {
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

}
