package co.edu.uniquindio.envio.controller;

import co.edu.uniquindio.envio.factory.ModelFactory;
import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.MetodoPagoDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.model.Usuario;
import co.edu.uniquindio.envio.services.IUsuarioServices;

import java.util.List;

public class UsuarioController {
    ModelFactory modelFactory;
    IUsuarioServices usuarioServices;

    public UsuarioController() {
        modelFactory = ModelFactory.getInstance();
        usuarioServices = modelFactory.getUsuarioServices();
    }

    public List<UsuarioDto> obtenerUsuarios() {
        return modelFactory.obtenerUsuarios();
    }

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactory.agregarUsuario(usuarioDto);
    }

    public boolean eliminarUsuario(String idUsuario) {
        return modelFactory.eliminarUsuario(idUsuario);
    }

    public UsuarioDto obtenerUsuarioPorNombre(String nombre) {
        return usuarioServices.obtenerUsuarioPorNombre(nombre);
    }

    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        return usuarioServices.actualizarUsuario(usuarioDto);
    }

    public List<DireccionDto> obtenerDireccionesUsuario(String idUsuario) {
        return usuarioServices.obtenerDireccionesUsuario(idUsuario);
    }

    public boolean agregarDireccion(String idUsuario, DireccionDto direccionDto) {
        return usuarioServices.agregarDireccion(idUsuario, direccionDto);
    }

    public boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto) {
        return usuarioServices.actualizarDireccion(idUsuario, direccionDto);
    }

    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        return usuarioServices.eliminarDireccion(idUsuario, aliasDireccion);
    }

    public List<MetodoPagoDto> obtenerMetodosPago(String idUsuario) {
        return usuarioServices.obtenerMetodosPago(idUsuario);
    }

    public boolean agregarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        return usuarioServices.agregarMetodoPago(idUsuario, metodoPagoDto);
    }

    public boolean actualizarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        return usuarioServices.actualizarMetodoPago(idUsuario, metodoPagoDto);
    }

    public boolean eliminarMetodoPago(String idUsuario, String aliasMetodoPago) {
        return usuarioServices.eliminarMetodoPago(idUsuario, aliasMetodoPago);
    }

    public boolean validarCredencialesUsuario(String usuario, String contrasena) {
        ModelFactory modelFactory = ModelFactory.getInstance();
        for (UsuarioDto uDto : modelFactory.obtenerUsuarios()) {
            if (uDto.usuario().equals(usuario) && uDto.contrasena().equals(contrasena)) {
                Usuario usuarioEncontrado = modelFactory.obtenerUsuarioOriginal(uDto.idUsuario());
                modelFactory.setUsuarioActual(usuarioEncontrado);
                return true;
            }
        }
        return false;
    }
}
