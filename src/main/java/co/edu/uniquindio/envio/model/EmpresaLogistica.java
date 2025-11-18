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

    public Usuario obtenerUsuarioPorNombre(String nombre) {
        return getListaUsuarios().stream()
                .filter(u -> u.getNombreCompleto().equalsIgnoreCase(nombre))
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
            repartidorExistente.setNombre(repartidorActualizado.getNombre());
            repartidorExistente.setDocumento(repartidorActualizado.getDocumento());
            repartidorExistente.setTelefono(repartidorActualizado.getTelefono());
            repartidorExistente.setUsuario(repartidorActualizado.getUsuario());
            repartidorExistente.setContrasena(repartidorActualizado.getContrasena());
            repartidorExistente.setDisponibilidad(repartidorActualizado.getDisponibilidad());
            repartidorExistente.setZonaCobertura(repartidorActualizado.getZonaCobertura());
            return true;
        }
        return false;
    }

    // --- Lógica de Negocio para Envios ---
    public boolean agregarEnvio(String idUsuario, Envio envio) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null || envio == null || obtenerEnvio(envio.getId()) != null) {
            return false;
        }
        getListaEnvios().add(envio);
        usuario.getEnvios().add(envio);
        // Si el envío ya tiene un repartidor asignado al crearse, lo añadimos a su lista
        if (envio.getRepartidorAsignado() != null) {
            Repartidor repartidorOriginal = obtenerRepartidor(envio.getRepartidorAsignado().getIdRepartidor());
            if (repartidorOriginal != null) {
                repartidorOriginal.getEnviosAsignados().add(envio);
            }
        }
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
            // Eliminar la referencia del envío en el repartidor asignado
            if (envioEncontrado.getRepartidorAsignado() != null) {
                envioEncontrado.getRepartidorAsignado().getEnviosAsignados().remove(envioEncontrado);
            }
            return true;
        }
        return false;
    }

    public boolean actualizarEnvio(Envio envioActualizado) {
        if (envioActualizado == null) return false;
        Envio envioEncontrado = obtenerEnvio(envioActualizado.getId());
        if (envioEncontrado != null) {
            envioEncontrado.setFechaCreacion(envioActualizado.getFechaCreacion());
            envioEncontrado.setFechaEntrega(envioActualizado.getFechaEntrega());
            envioEncontrado.setDireccionOrigen(envioActualizado.getDireccionOrigen());
            envioEncontrado.setDireccionDestino(envioActualizado.getDireccionDestino());
            envioEncontrado.setEstadoActual(envioActualizado.getEstadoActual());
            envioEncontrado.setPeso(envioActualizado.getPeso());
            envioEncontrado.setLargo(envioActualizado.getLargo());
            envioEncontrado.setAncho(envioActualizado.getAncho());
            envioEncontrado.setAlto(envioActualizado.getAlto());
            envioEncontrado.setCosto(envioActualizado.getCosto());
            envioEncontrado.setPago(envioActualizado.getPago()); // Actualizar el estado de pago
            envioEncontrado.setFactura(envioActualizado.getFactura()); // Actualizar la factura
            return true;
        }
        return false;
    }

    public Envio obtenerEnvio(String idEnvio) {
        return getListaEnvios().stream()
                .filter(e -> e.getId().equalsIgnoreCase(idEnvio))
                .findFirst()
                .orElse(null);
    }

    public List<Envio> obtenerEnviosDeUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return (usuario != null) ? usuario.getEnvios() : new ArrayList<>();
    }

    public Factura pagarEnvio(String idEnvio, MetodoPago metodoPago) {
        Envio envio = obtenerEnvio(idEnvio);
        if (envio != null && !envio.getPago()) {
            envio.setPago(true);
            Factura factura = new Factura(UUID.randomUUID().toString(), LocalDateTime.now(), envio.getCosto(), metodoPago);
            envio.setFactura(factura); // Asignar la factura al envío
            return factura;
        }
        return null;
    }


    // --- Lógica de Negocio para Asignaciones e Incidencias ---

    public void asignarEnvio(Envio envio, Repartidor repartidor) {
        Envio envioOriginal = obtenerEnvio(envio.getId());
        Repartidor repartidorOriginal = obtenerRepartidor(repartidor.getIdRepartidor());

        if (envioOriginal != null && repartidorOriginal != null) {
            envioOriginal.setRepartidorAsignado(repartidorOriginal);
            envioOriginal.setEstadoActual("Asignado");
            if (!repartidorOriginal.getEnviosAsignados().contains(envioOriginal)) {
                repartidorOriginal.getEnviosAsignados().add(envioOriginal);
            }
        }
    }

    public void reasignarEnvio(Envio envio, Repartidor nuevoRepartidor) {
        Envio envioOriginal = obtenerEnvio(envio.getId());
        Repartidor nuevoRepartidorOriginal = obtenerRepartidor(nuevoRepartidor.getIdRepartidor());

        if (envioOriginal != null && nuevoRepartidorOriginal != null) {
            // Quitar el envío del repartidor anterior, si existe
            Repartidor repartidorAnterior = envioOriginal.getRepartidorAsignado();
            if (repartidorAnterior != null) {
                repartidorAnterior.getEnviosAsignados().remove(envioOriginal);
            }
            // Asignar el nuevo repartidor al envío
            envioOriginal.setRepartidorAsignado(nuevoRepartidorOriginal);
            envioOriginal.setEstadoActual("Reasignado");
            // Añadir el envío a la lista del nuevo repartidor
            if (!nuevoRepartidorOriginal.getEnviosAsignados().contains(envioOriginal)) {
                nuevoRepartidorOriginal.getEnviosAsignados().add(envioOriginal);
            }
        }
    }

    public void registrarIncidencia(Envio envio, Incidencia incidencia) {
        Envio envioEncontrado = obtenerEnvio(envio.getId());
        if (envioEncontrado != null) {
            envioEncontrado.getIncidencias().add(incidencia);
            envioEncontrado.setEstadoActual("Incidencia");
        }
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
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && direccion != null && usuario.getDireccionesFrecuentes().containsKey(direccion.getAlias())) {
            usuario.getDireccionesFrecuentes().put(direccion.getAlias(), direccion);
            return true;
        }
        return false;
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

    public boolean agregarMetodoPago(String idUsuario, MetodoPago metodoPago) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && metodoPago != null) {
            if (usuario.getMetodosPago().containsKey(metodoPago.getAlias())) {
                return false;
            }
            usuario.getMetodosPago().put(metodoPago.getAlias(), metodoPago);
            return true;
        }
        return false;
    }

    public boolean actualizarMetodoPago(String idUsuario, MetodoPago metodoPagoActualizado) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && metodoPagoActualizado != null) {
            if (usuario.getMetodosPago().containsKey(metodoPagoActualizado.getAlias())) {
                usuario.getMetodosPago().put(metodoPagoActualizado.getAlias(), metodoPagoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarMetodoPago(String idUsuario, String aliasMetodoPago) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            return usuario.getMetodosPago().remove(aliasMetodoPago) != null;
        }
        return false;
    }

    // --- Lógica de Negocio para Métricas ---
    public double calcularTiempoPromedioEntrega(List<Envio> envios) {
        return envios.stream()
                .filter(e -> "Entregado".equals(e.getEstadoActual()) && e.getFechaEntrega() != null)
                .mapToLong(e -> java.time.temporal.ChronoUnit.DAYS.between(e.getFechaCreacion(), e.getFechaEntrega()))
                .average()
                .orElse(0.0);
    }

    public double calcularIngresosTotales(List<Envio> envios) {
        return envios.stream()
                .mapToDouble(Envio::getCosto)
                .sum();
    }

    public long contarIncidencias(List<Envio> envios) {
        return envios.stream()
                .flatMap(e -> e.getIncidencias().stream())
                .count();
    }

    public List<Repartidor> getRepartidores() {
        return new ArrayList<>(listaRepartidores);
    }

}
