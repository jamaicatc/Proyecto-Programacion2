package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.mapping.dto.*;
import co.edu.uniquindio.envio.mapping.mappers.EmpresaLogisticaMappingImpl;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.model.strategy.*;
import co.edu.uniquindio.envio.services.*;
import co.edu.uniquindio.envio.utils.DataUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelFactory implements IModelFactory, IUsuarioServices, IEnvioServices {
    private static ModelFactory modelFactory;
    private final EmpresaLogistica empresaLogistica;
    private final IEmpresaLogisticaMapping empresaLogisticaMapping;
    private final IDireccionMapping<Direccion, DireccionDto> direccionMapping;

    public static ModelFactory getInstance(){
        if (modelFactory == null){
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        empresaLogisticaMapping = new EmpresaLogisticaMappingImpl();
        direccionMapping = (IDireccionMapping<Direccion, DireccionDto>) empresaLogisticaMapping;
        empresaLogistica = DataUtil.inicializarDatos();
    }

    public IUsuarioServices getUsuarioServices() {
        return this;
    }

    public IEnvioServices getEnvioServices() {
        return this;
    }

    // Implementación de IUsuarioServices
    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return empresaLogisticaMapping.getUsuariosDto(empresaLogistica.getListaUsuarios());
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return empresaLogistica.agregarUsuario(usuarioDto);
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        return empresaLogistica.eliminarUsuario(idUsuario);
    }

    @Override
    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        return empresaLogistica.actualizarUsuario(usuarioDto);
    }

    @Override
    public List<RepartidorDto> obtenerRepartidores() {
        return empresaLogisticaMapping.getRepartidoresDto(empresaLogistica.getListaRepartidores());
    }

    @Override
    public boolean agregarRepartidor(RepartidorDto repartidorDto) {
        return empresaLogistica.agregarRepartidor(empresaLogisticaMapping.repartidorDtoToRepartidor(repartidorDto));
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        return empresaLogistica.eliminarRepartidor(idRepartidor);
    }

    @Override
    public boolean actualizarRepartidor(RepartidorDto repartidorDto) {
        return empresaLogistica.actualizarRepartidor(empresaLogisticaMapping.repartidorDtoToRepartidor(repartidorDto));
    }

    @Override
    public UsuarioDto obtenerUsuarioPorNombre(String nombre) {
        Usuario usuario = empresaLogistica.getListaUsuarios().stream()
                .filter(u -> u.getNombreCompleto().equals(nombre))
                .findFirst()
                .orElse(null);
        return usuario != null ? empresaLogisticaMapping.usuarioToUsuarioDto(usuario) : null;
    }

    @Override
    public List<DireccionDto> obtenerDireccionesUsuario(String idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            List<Direccion> direcciones = new ArrayList<>(usuario.getDireccionesFrecuentes().values());
            return direccionMapping.getDireccionesDto(direcciones);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean agregarDireccion(String idUsuario, DireccionDto direccionDto) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            Direccion direccion = direccionMapping.direccionDtoToDireccion(direccionDto);
            usuario.getDireccionesFrecuentes().put(direccion.getAlias(), direccion);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            Map<String, Direccion> direcciones = usuario.getDireccionesFrecuentes();
            if (direcciones.containsKey(direccionDto.alias())) {
                Direccion direccion = direccionMapping.direccionDtoToDireccion(direccionDto);
                direcciones.put(direccionDto.alias(), direccion);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            return usuario.getDireccionesFrecuentes().remove(aliasDireccion) != null;
        }
        return false;
    }

    @Override
    public List<MetodoPagoDto> obtenerMetodosPago(String idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            return empresaLogisticaMapping.getMetodosPagoDto(new ArrayList<>(usuario.getMetodosPago().values()));
        }
        return new ArrayList<>();
    }

    private Usuario obtenerUsuarioPorId(String idUsuario) {
        return empresaLogistica.getListaUsuarios().stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    // Implementación de IEnvioServices
    @Override
    public List<EnvioDto> obtenerEnvios() {
        return empresaLogisticaMapping.getEnviosDto(empresaLogistica.getListaEnvios());
    }

    @Override
    public List<EnvioDto> obtenerEnvios(String idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        if (usuario != null) {
            return empresaLogisticaMapping.getEnviosDto(usuario.getEnvios());
        }
        return new ArrayList<>();
    }

    @Override
    public EnvioDto obtenerEnvioDto(String idEnvio) {
        Envio envio = empresaLogistica.obtenerEnvio(idEnvio);
        if (envio != null) {
            return empresaLogisticaMapping.envioToEnvioDto(envio);
        }
        return null;
    }

    @Override
    public boolean agregarEnvio(String idUsuario, EnvioDto envioDto) {
        return empresaLogistica.agregarEnvio(idUsuario, envioDto);
    }

    @Override
    public boolean actualizarEnvio(EnvioDto envioDto) {
        return empresaLogistica.actualizarEnvio(envioDto);
    }

    @Override
    public boolean eliminarEnvio(String idEnvio) {
        return empresaLogistica.eliminarEnvio(idEnvio);
    }

    @Override
    public List<String> obtenerHistorial(String idEnvio) {
        Envio envio = empresaLogistica.obtenerEnvio(idEnvio);
        if (envio != null) {
            return envio.getHistorial();
        }
        return new ArrayList<>();
    }

    public double calcularTarifa(Envio envio, ITarifaStrategy estrategia) {
        TarifaBase tarifaBase = new TarifaBase();
        tarifaBase.setEstrategia(estrategia);
        return tarifaBase.calcular(envio);
    }

    public Factura pagarEnvio(String idEnvio, MetodoPagoDto metodoPagoDto) {
        Envio envio = empresaLogistica.obtenerEnvio(idEnvio);
        if (envio != null && envio.getFactura() == null) {
            MetodoPago metodoPago = empresaLogisticaMapping.metodoPagoDtoToMetodoPago(metodoPagoDto);
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
        return null;
    }

    public EnvioDto obtenerEnvio(String numeroSeguimiento) {
        Envio envio = empresaLogistica.obtenerEnvio(numeroSeguimiento);
        if (envio != null) {
            return empresaLogisticaMapping.envioToEnvioDto(envio);
        }
        return null;
    }
}
