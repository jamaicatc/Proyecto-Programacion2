package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.model.Administrador;
import co.edu.uniquindio.envio.model.EmpresaLogistica;
import co.edu.uniquindio.envio.model.Repartidor;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IModelFactory;

import java.util.List;

public class ModelFactory implements IModelFactory {
    private static ModelFactory modelFactory;
    private EmpresaLogistica empresaLogistica;

    private ModelFactory(){
        empresaLogistica = new EmpresaLogistica();
    }

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    //CRUD Administrador
    @Override
    public boolean agregarAdministrador(Administrador administrador) {
        return empresaLogistica.agregarAdministrador(administrador);
    }

    @Override
    public boolean eliminarAdministrador(String idAdministrador) {
        return empresaLogistica.eliminarAdministrador(idAdministrador);
    }

    @Override
    public boolean actualizarAdministrador(Administrador administrador) {
        return empresaLogistica.actualizarAdministrador(administrador);
    }

    @Override
    public Administrador obtenerAdministrador(String idAdministrador) {
        return empresaLogistica.obtenerAdministrador(idAdministrador);
    }

    @Override
    public List<Administrador> listarAdministradores() {
        return empresaLogistica.listarAdministradores();
    }

    @Override
    public Administrador autenticarAdministrador(String correo, String contrasena) {
        return empresaLogistica.autenticarAdministrador(correo, contrasena);
    }

    //CRUD Repartidor

    @Override
    public boolean agregarRepartidor(Repartidor repartidor) {
        return empresaLogistica.agregarRepartidor(repartidor);
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        return empresaLogistica.eliminarRepartidor(idRepartidor);
    }

    @Override
    public boolean actualizarRepartidor(Repartidor repartidor) {
        return empresaLogistica.actualizarRepartidor(repartidor);
    }

    @Override
    public Repartidor obtenerRepartidor(String idRepartidor) {
        return empresaLogistica.obtenerRepartidor(idRepartidor);
    }

    @Override
    public List<Repartidor> listarRepartidores() {
        return empresaLogistica.listarRepartidores();
    }

    //CRUD Usuario

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        return empresaLogistica.agregarUsuario(usuario);
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        return empresaLogistica.eliminarUsuario(idUsuario);
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        return empresaLogistica.actualizarUsuario(usuario);
    }

    @Override
    public Usuario obtenerUsuario(String idUsuario) {
        return empresaLogistica.obtenerUsuario(idUsuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return empresaLogistica.listarUsuarios();
    }

    @Override
    public Usuario autenticarUsuario(String correo, String contrasena) {
        return empresaLogistica.autenticarUsuario(correo, contrasena);
    }
}
