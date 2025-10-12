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

    public boolean agregarUsuario(Usuario nuevoUsuario){
        Usuario usuarioEncontrado = obtenerUsuario(nuevoUsuario.getIdUsuario());
        if(usuarioEncontrado == null){
            getListaUsuarios().add(nuevoUsuario);
            return true;
        }else{
            return  false;
        }
    }

    private Usuario obtenerUsuario(String idUsuario) {
        Usuario usuario = null;
        for (Usuario usuario1: getListaUsuarios()) {
            if(usuario1.getIdUsuario().equalsIgnoreCase(idUsuario)){
                usuario = usuario1;
                break;
            }
        }
        return usuario;
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuarioEncontrado = obtenerUsuario(idUsuario);
        if(usuarioEncontrado !=null){
            getListaUsuarios().remove(usuarioEncontrado);
            return true;
        }else{
            return false;
        }
    }

    public boolean agregarRepartidor(Repartidor nuevoRepartidor){
        Repartidor repartidorEncontrado = obtenerRepartidor(nuevoRepartidor.getIdRepartidor());
        if(repartidorEncontrado == null){
            getListaRepartidores().add(nuevoRepartidor);
            return true;
        }else{
            return  false;
        }
    }

    private Repartidor obtenerRepartidor(String idRepartidor) {
        Repartidor repartidor = null;
        for (Repartidor repartidor1: getListaRepartidores()) {
            if(repartidor1.getIdRepartidor().equalsIgnoreCase(idRepartidor)){
                repartidor = repartidor1;
                break;
            }
        }
        return repartidor;
    }

    public boolean eliminarRepartidor(String idRepartidor) {
        Repartidor repartidorEncontrado = obtenerRepartidor(idRepartidor);
        if(repartidorEncontrado !=null){
            getListaRepartidores().remove(repartidorEncontrado);
            return true;
        }else{
            return false;
        }
    }
}
