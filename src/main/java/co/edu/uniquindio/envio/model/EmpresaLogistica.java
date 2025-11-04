package co.edu.uniquindio.envio.model;

import co.edu.uniquindio.envio.mapping.dto.DireccionDto;
import co.edu.uniquindio.envio.mapping.dto.UsuarioDto;
import co.edu.uniquindio.envio.services.IEmpresaLogisticaServices;

import java.util.ArrayList;
import java.util.List;

public class EmpresaLogistica implements IEmpresaLogisticaServices {
    private final String nombre;
    private final List<Usuario> listaUsuarios;
    private final List<Repartidor> listaRepartidores;

    public EmpresaLogistica() {
        this("Mercado Cerrado");
    }

    public EmpresaLogistica(String nombre) {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
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
}
