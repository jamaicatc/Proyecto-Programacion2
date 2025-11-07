package co.edu.uniquindio.envio.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmpresaLogistica {
    private final String nombre;
    private final List<Usuario> listaUsuarios;
    private final List<Repartidor> listaRepartidores;
    private final List<Envio> listaEnvios;

    public EmpresaLogistica() {
        this("Mercado Cerrado");
    }

    public EmpresaLogistica(String nombre) {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
        this.listaEnvios = new ArrayList<>();
    }

    // Getters
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

    // --- Lógica de Negocio para Usuarios ---
    public boolean agregarUsuario(Usuario usuario) {
        if (usuario == null || obtenerUsuario(usuario.getIdUsuario()) != null) {
            return false;
        }
        return getListaUsuarios().add(usuario);
    }

    public Usuario obtenerUsuario(String idUsuario) {
        return getListaUsuarios().stream()
                .filter(u -> u.getIdUsuario().equalsIgnoreCase(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuarioEncontrado = obtenerUsuario(idUsuario);
        if (usuarioEncontrado != null) {
            // Eliminar también los envíos del usuario
            usuarioEncontrado.getEnvios().forEach(envio -> listaEnvios.remove(envio));
            return getListaUsuarios().remove(usuarioEncontrado);
        }
        return false;
    }

    public boolean actualizarUsuario(Usuario usuarioActualizado) {
        if (usuarioActualizado == null) return false;
        Usuario usuarioEncontrado = obtenerUsuario(usuarioActualizado.getIdUsuario());
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setNombreCompleto(usuarioActualizado.getNombreCompleto());
            usuarioEncontrado.setCorreo(usuarioActualizado.getCorreo());
            usuarioEncontrado.setTelefono(usuarioActualizado.getTelefono());
            return true;
        }
        return false;
    }

    // --- Lógica de Negocio para Repartidores ---
    public boolean agregarRepartidor(Repartidor repartidor) {
        if (repartidor == null || obtenerRepartidor(repartidor.getIdRepartidor()) != null) {
            return false;
        }
        return listaRepartidores.add(repartidor);
    }

    public Repartidor obtenerRepartidor(String idRepartidor) {
        return listaRepartidores.stream()
                .filter(r -> r.getIdRepartidor().equalsIgnoreCase(idRepartidor))
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarRepartidor(String idRepartidor) {
        Repartidor repartidor = obtenerRepartidor(idRepartidor);
        if (repartidor != null) {
            return listaRepartidores.remove(repartidor);
        }
        return false;
    }

    public boolean actualizarRepartidor(Repartidor repartidorActualizado) {
        if (repartidorActualizado == null) return false;
        Repartidor repartidorExistente = obtenerRepartidor(repartidorActualizado.getIdRepartidor());
        if (repartidorExistente != null) {
            int index = listaRepartidores.indexOf(repartidorExistente);
            listaRepartidores.set(index, repartidorActualizado);
            return true;
        }
        return false;
    }

    // --- Lógica de Negocio para Envios ---
    public boolean agregarEnvio(String idUsuario, Envio envio) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null || envio == null || obtenerEnvio(envio.getIdEnvio()) != null) {
            return false;
        }
        getListaEnvios().add(envio);
        usuario.getEnvios().add(envio);
        return true;
    }

    public boolean eliminarEnvio(String idEnvio) {
        Envio envioEncontrado = obtenerEnvio(idEnvio);
        if (envioEncontrado != null) {
            getListaEnvios().remove(envioEncontrado);
            // Eliminar la referencia del envío en todos los usuarios
            for (Usuario usuario : listaUsuarios) {
                usuario.getEnvios().remove(envioEncontrado);
            }
            return true;
        }
        return false;
    }

    public boolean actualizarEnvio(Envio envioActualizado) {
        if (envioActualizado == null) return false;
        Envio envioEncontrado = obtenerEnvio(envioActualizado.getIdEnvio());
        if (envioEncontrado != null) {
            envioEncontrado.setFecha(envioActualizado.getFecha());
            envioEncontrado.setFechaEntregaEstimada(envioActualizado.getFechaEntregaEstimada());
            envioEncontrado.setOrigen(envioActualizado.getOrigen());
            envioEncontrado.setDestino(envioActualizado.getDestino());
            envioEncontrado.setEstado(envioActualizado.getEstado());
            envioEncontrado.setPeso(envioActualizado.getPeso());
            envioEncontrado.setLargo(envioActualizado.getLargo());
            envioEncontrado.setAncho(envioActualizado.getAncho());
            envioEncontrado.setAlto(envioActualizado.getAlto());
            envioEncontrado.setCosto(envioActualizado.getCosto());
            envioEncontrado.setPago(envioActualizado.getPago());
            return true;
        }
        return false;
    }

    public Envio obtenerEnvio(String idEnvio) {
        return getListaEnvios().stream()
                .filter(e -> e.getIdEnvio().equalsIgnoreCase(idEnvio))
                .findFirst()
                .orElse(null);
    }

    public List<Envio> obtenerEnviosDeUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return (usuario != null) ? usuario.getEnvios() : new ArrayList<>();
    }

    public Factura pagarEnvio(String idEnvio, MetodoPago metodoPago) {
        Envio envio = obtenerEnvio(idEnvio);
        // Validaciones: el envío debe existir y no debe estar pagado.
        if (envio != null && !envio.getPago()) {
            Factura factura = new Factura(
                    "fact-" + UUID.randomUUID().toString().substring(0, 4),
                    LocalDateTime.now(),
                    envio.getCosto(),
                    metodoPago
            );
            envio.setFactura(factura);
            envio.setPago(true);
            return factura;
        }
        return null; // El pago no se pudo procesar
    }

    // --- Lógica de Negocio para Direcciones y Métodos de Pago ---
    public List<Direccion> obtenerDireccionesUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return (usuario != null) ? new ArrayList<>(usuario.getDireccionesFrecuentes().values()) : new ArrayList<>();
    }

    public boolean agregarDireccion(String idUsuario, Direccion direccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && direccion != null) {
            usuario.getDireccionesFrecuentes().put(direccion.getAlias(), direccion);
            return true;
        }
        return false;
    }

    public boolean actualizarDireccion(String idUsuario, Direccion direccion) {
        return agregarDireccion(idUsuario, direccion); // La lógica es la misma: reemplazar o agregar.
    }

    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            return usuario.getDireccionesFrecuentes().remove(aliasDireccion) != null;
        }
        return false;
    }

    public List<MetodoPago> obtenerMetodosPago(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return (usuario != null) ? new ArrayList<>(usuario.getMetodosPago().values()) : new ArrayList<>();
    }
}
