package co.edu.uniquindio.envio.model;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.services.IEmpresaLogisticaServices;

import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica implements IEmpresaLogisticaServices {
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

    // Implementación de IUsuarioServices
    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        List<UsuarioDto> usuariosDto = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            usuariosDto.add(convertirAUsuarioDto(usuario));
        }
        return usuariosDto;
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return false;
        Usuario usuario = convertirAUsuario(usuarioDto);
        Usuario usuarioEncontrado = obtenerUsuario(usuario.getIdUsuario());
        if (usuarioEncontrado == null) {
            getListaUsuarios().add(usuario);
            return true;
        }
        return false;
    }

    private Usuario obtenerUsuario(String idUsuario) {
        for (Usuario usuario : getListaUsuarios()) {
            if (usuario.getIdUsuario().equalsIgnoreCase(idUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuarioEncontrado = obtenerUsuario(idUsuario);
        if (usuarioEncontrado != null) {
            getListaUsuarios().remove(usuarioEncontrado);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return false;
        Usuario usuarioEncontrado = obtenerUsuario(usuarioDto.idUsuario());
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setNombreCompleto(usuarioDto.nombreCompleto());
            usuarioEncontrado.setCorreo(usuarioDto.correo());
            usuarioEncontrado.setTelefono(usuarioDto.telefono());
            return true;
        }
        return false;
    }

    @Override
    public UsuarioDto obtenerUsuarioPorNombre(String nombre) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreCompleto().equalsIgnoreCase(nombre)) {
                return convertirAUsuarioDto(usuario);
            }
        }
        return null;
    }

    @Override
    public List<DireccionDto> obtenerDireccionesUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            List<DireccionDto> direccionesDto = new ArrayList<>();
            for (Direccion direccion : usuario.getDireccionesFrecuentes().values()) {
                direccionesDto.add(convertirADireccionDto(direccion));
            }
            return direccionesDto;
        }
        return new ArrayList<>();
    }

    @Override
    public boolean agregarDireccion(String idUsuario, DireccionDto direccionDto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && direccionDto != null) {
            Direccion direccion = convertirADireccion(direccionDto);
            usuario.getDireccionesFrecuentes().put(direccionDto.alias(), direccion);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && direccionDto != null) {
            Direccion direccion = convertirADireccion(direccionDto);
            usuario.getDireccionesFrecuentes().put(direccionDto.alias(), direccion);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            return usuario.getDireccionesFrecuentes().remove(aliasDireccion) != null;
        }
        return false;
    }

    // Implementación de IRepartidorServices
    @Override
    public List<Repartidor> obtenerRepartidores() {
        return new ArrayList<>(listaRepartidores);
    }

    @Override
    public boolean agregarRepartidor(Repartidor repartidor) {
        if (repartidor == null) return false;
        if (obtenerRepartidor(repartidor.getIdRepartidor()) == null) {
            listaRepartidores.add(repartidor);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        Repartidor repartidor = obtenerRepartidor(idRepartidor);
        if (repartidor != null) {
            listaRepartidores.remove(repartidor);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarRepartidor(Repartidor repartidor) {
        if (repartidor == null) return false;
        Repartidor repartidorExistente = obtenerRepartidor(repartidor.getIdRepartidor());
        if (repartidorExistente != null) {
            int index = listaRepartidores.indexOf(repartidorExistente);
            listaRepartidores.set(index, repartidor);
            return true;
        }
        return false;
    }

    private Repartidor obtenerRepartidor(String idRepartidor) {
        for (Repartidor repartidor : listaRepartidores) {
            if (repartidor.getIdRepartidor().equalsIgnoreCase(idRepartidor)) {
                return repartidor;
            }
        }
        return null;
    }

    // Métodos para Envios
    public boolean agregarEnvio(String idUsuario, EnvioDto envioDto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null || envioDto == null) return false;
        Envio envio = convertirAEnvio(envioDto);
        Envio envioEncontrado = obtenerEnvio(envio.getIdEnvio());
        if (envioEncontrado == null) {
            getListaEnvios().add(envio);
            usuario.getEnvios().add(envio);
            return true;
        }
        return false;
    }

    public boolean eliminarEnvio(String idEnvio) {
        Envio envioEncontrado = obtenerEnvio(idEnvio);
        if (envioEncontrado != null) {
            getListaEnvios().remove(envioEncontrado);
            for (Usuario usuario : listaUsuarios) {
                usuario.getEnvios().remove(envioEncontrado);
            }
            return true;
        }
        return false;
    }

    public boolean actualizarEnvio(EnvioDto envioDto) {
        if (envioDto == null) return false;
        Envio envioEncontrado = obtenerEnvio(envioDto.idEnvio());
        if (envioEncontrado != null) {
            envioEncontrado.setFecha(envioDto.fecha());
            envioEncontrado.setFechaEntregaEstimada(envioDto.fechaEntregaEstimada());
            envioEncontrado.setOrigen(envioDto.origen());
            envioEncontrado.setDestino(envioDto.destino());
            envioEncontrado.setEstado(envioDto.estado());
            envioEncontrado.setPeso(envioDto.peso());
            envioEncontrado.setLargo(envioDto.largo());
            envioEncontrado.setAncho(envioDto.ancho());
            envioEncontrado.setAlto(envioDto.alto());
            envioEncontrado.setCosto(envioDto.costo());
            return true;
        }
        return false;
    }

    public Envio obtenerEnvio(String idEnvio) {
        for (Envio envio : getListaEnvios()) {
            if (envio.getIdEnvio().equalsIgnoreCase(idEnvio)) {
                return envio;
            }
        }
        return null;
    }

    // Métodos auxiliares de conversión
    private UsuarioDto convertirAUsuarioDto(Usuario usuario) {
        return new UsuarioDto(
            usuario.getIdUsuario(),
            usuario.getNombreCompleto(),
            usuario.getCorreo(),
            usuario.getTelefono()
        );
    }

    private Usuario convertirAUsuario(UsuarioDto dto) {
        return new Usuario(
            dto.idUsuario(),
            dto.nombreCompleto(),
            dto.correo(),
            dto.telefono()
        );
    }

    private DireccionDto convertirADireccionDto(Direccion direccion) {
        return new DireccionDto(
            direccion.getIdDireccion(),
            direccion.getAlias(),
            direccion.getCalle(),
            direccion.getCiudad(),
            direccion.getCoordenadas()
        );
    }

    private Direccion convertirADireccion(DireccionDto dto) {
        return new Direccion(
            dto.idDireccion(),
            dto.alias(),
            dto.calleCarrera(),
            dto.ciudad(),
            dto.coordenadas()
        );
    }

    private EnvioDto convertirAEnvioDto(Envio envio) {
        return new EnvioDto(
            envio.getIdEnvio(),
            envio.getFecha(),
            envio.getFechaEntregaEstimada(),
            envio.getOrigen(),
            envio.getDestino(),
            envio.getEstado(),
            envio.getPeso(),
            envio.getLargo(),
            envio.getAncho(),
            envio.getAlto(),
            envio.getCosto()
        );
    }

    private Envio convertirAEnvio(EnvioDto dto) {
        return new Envio(
            dto.idEnvio(),
            dto.fecha(),
            dto.fechaEntregaEstimada(),
            dto.origen(),
            dto.destino(),
            dto.estado(),
            dto.peso(),
            dto.largo(),
            dto.ancho(),
            dto.alto(),
            dto.costo()
        );
    }
}
