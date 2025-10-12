package co.edu.uniquindio.envio.model;


import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica  {
    private String nombre;
    private List<Usuario> listaUsuarios;
    private List<Repartidor> listaRepartidores;
    private List<Envio> listaEnvios;
    private List<Pago> listaPagos;
    private List<Administrador> listaAdministradores;
    private List<Incidencia> listaIncidencias;

    public EmpresaLogistica() {
        this.nombre = "Mercado Cerrado";
        this.listaUsuarios = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
        this.listaEnvios = new ArrayList<>();
        this.listaPagos = new ArrayList<>();
        this.listaAdministradores = new ArrayList<>();
        this.listaIncidencias = new ArrayList<>();
    }

    public EmpresaLogistica(String nombre) {
        this();
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    public List<Envio> getListaEnvios() {
        return listaEnvios;
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public List<Administrador> getListaAdministradores() {
        return listaAdministradores;
    }

    public List<Incidencia> getListaIncidencias() {
        return listaIncidencias;
    }
}
