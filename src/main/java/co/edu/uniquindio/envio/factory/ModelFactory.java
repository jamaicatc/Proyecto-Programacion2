package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.mapping.dto.*;
import co.edu.uniquindio.envio.mapping.mappers.EmpresaLogisticaMappingImpl;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.model.strategy.ITarifaStrategy;
import co.edu.uniquindio.envio.model.strategy.TarifaBase;
import co.edu.uniquindio.envio.services.IEnvioServices;
import co.edu.uniquindio.envio.services.IModelFactory;
import co.edu.uniquindio.envio.services.IUsuarioServices;
import co.edu.uniquindio.envio.model.observer.DataUpdateListener;
import co.edu.uniquindio.envio.utils.DataUtil;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModelFactory implements IModelFactory, IUsuarioServices, IEnvioServices {
    private static ModelFactory modelFactory;
    private final EmpresaLogistica empresaLogistica;
    private final EmpresaLogisticaMappingImpl mapper;
    private final List<DataUpdateListener> listeners = new ArrayList<>();
    private Object usuarioActual;


    public static SimpleBooleanProperty datosActualizadosProperty = new SimpleBooleanProperty(false);

    private ModelFactory() {
        this.mapper = new EmpresaLogisticaMappingImpl();
        this.empresaLogistica = DataUtil.inicializarDatos();
    }

    public void addDataUpdateListener(DataUpdateListener listener) {
        listeners.add(listener);
    }

    public void notifyDataChanged() {
        for (DataUpdateListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    public static ModelFactory getInstance() {
        if (modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    public Object getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Object usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public IUsuarioServices getUsuarioServices() {
        return this;
    }

    public IEnvioServices getEnvioServices() {
        return this;
    }

    public EmpresaLogisticaMappingImpl getMapper() {
        return mapper;
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return mapper.getUsuariosDto(empresaLogistica.getListaUsuarios());
    }

    public Usuario obtenerUsuarioOriginal(String idUsuario) {
        return empresaLogistica.obtenerUsuario(idUsuario);
    }
    
    public Repartidor obtenerRepartidorOriginal(String idRepartidor) {
        return empresaLogistica.obtenerRepartidor(idRepartidor);
    }

    @Override
    public UsuarioDto obtenerUsuarioPorNombre(String nombre) {
        return mapper.usuarioToUsuarioDto(empresaLogistica.obtenerUsuarioPorNombre(nombre));
    }

    public Usuario obtenerUsuarioPorCredenciales(String usuario, String contrasena) {
        return empresaLogistica.getListaUsuarios().stream()
                .filter(u -> u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);
    }


    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        boolean result = empresaLogistica.agregarUsuario(usuario);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        boolean result = empresaLogistica.eliminarUsuario(idUsuario);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        boolean result = empresaLogistica.actualizarUsuario(usuario);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public List<RepartidorDto> obtenerRepartidores() {
        return mapper.getRepartidoresDto(empresaLogistica.getListaRepartidores());
    }

    @Override
    public boolean agregarRepartidor(RepartidorDto repartidorDto) {
        Repartidor repartidor = mapper.repartidorDtoToRepartidor(repartidorDto);
        boolean result = empresaLogistica.agregarRepartidor(repartidor);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        boolean result = empresaLogistica.eliminarRepartidor(idRepartidor);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean actualizarRepartidor(RepartidorDto repartidorDto) {
        Repartidor repartidor = mapper.repartidorDtoToRepartidor(repartidorDto);
        boolean result = empresaLogistica.actualizarRepartidor(repartidor);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public List<EnvioDto> obtenerEnvios() {
        return mapper.getEnviosDto(empresaLogistica.getListaEnvios());
    }

    @Override
    public List<EnvioDto> obtenerEnvios(String idUsuario) {
        return mapper.getEnviosDto(empresaLogistica.obtenerEnviosDeUsuario(idUsuario));
    }

    @Override
    public EnvioDto obtenerEnvioDto(String idEnvio) {
        return mapper.envioToEnvioDto(empresaLogistica.obtenerEnvio(idEnvio));
    }

    @Override
    public boolean agregarEnvio(String idUsuario, EnvioDto envioDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        boolean result = empresaLogistica.agregarEnvio(idUsuario, envio);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean actualizarEnvio(EnvioDto envioDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        boolean result = empresaLogistica.actualizarEnvio(envio);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean eliminarEnvio(String idEnvio) {
        boolean result = empresaLogistica.eliminarEnvio(idEnvio);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public Factura pagarEnvio(String idEnvio, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        Factura factura = empresaLogistica.pagarEnvio(idEnvio, metodoPago);
        if (factura != null) {
            notifyDataChanged();
        }
        return factura;
    }

    @Override
    public EnvioDto obtenerEnvio(String numeroSeguimiento) {
        return mapper.envioToEnvioDto(empresaLogistica.obtenerEnvio(numeroSeguimiento));
    }

    @Override
    public List<String> obtenerHistorial(String idEnvio) {
        return empresaLogistica.obtenerEnvio(idEnvio).getHistorial();
    }

    @Override
    public void asignarEnvio(EnvioDto envioDto, RepartidorDto repartidorDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        Repartidor repartidor = mapper.repartidorDtoToRepartidor(repartidorDto);
        empresaLogistica.asignarEnvio(envio, repartidor);
        notifyDataChanged();
    }

    @Override
    public void reasignarEnvio(EnvioDto envioDto, RepartidorDto nuevoRepartidorDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        Repartidor nuevoRepartidor = mapper.repartidorDtoToRepartidor(nuevoRepartidorDto);
        empresaLogistica.reasignarEnvio(envio, nuevoRepartidor);
        notifyDataChanged();
    }

    @Override
    public void registrarIncidencia(EnvioDto envioDto, IncidenciaDto incidenciaDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        Incidencia incidencia = mapper.incidenciaDtoToIncidencia(incidenciaDto);
        empresaLogistica.registrarIncidencia(envio, incidencia);
        notifyDataChanged();
    }

    @Override
    public List<EnvioDto> obtenerEnviosNoAsignados() {
        return mapper.getEnviosDto(empresaLogistica.getListaEnvios().stream()
                .filter(envio -> envio.getRepartidorAsignado() == null)
                .collect(Collectors.toList()));
    }

    @Override
    public List<EnvioDto> obtenerEnviosPorRepartidor(RepartidorDto repartidorDto) {
        if (repartidorDto == null) {
            return Collections.emptyList();
        }
        Repartidor repartidor = obtenerRepartidorOriginal(repartidorDto.idRepartidor());
        if (repartidor != null) {
            return mapper.getEnviosDto(repartidor.getEnviosAsignados());
        }
        return Collections.emptyList();
    }

    public double calcularTarifa(Envio envio, ITarifaStrategy estrategia) {
        TarifaBase tarifaBase = new TarifaBase();
        tarifaBase.setEstrategia(estrategia);
        return tarifaBase.calcular(envio);
    }

    @Override
    public List<DireccionDto> obtenerDireccionesUsuario(String idUsuario) {
        return mapper.getDireccionesDto(empresaLogistica.obtenerDireccionesUsuario(idUsuario));
    }

    @Override
    public boolean agregarDireccion(String idUsuario, DireccionDto direccionDto) {
        Direccion direccion = mapper.direccionDtoToDireccion(direccionDto);
        boolean result = empresaLogistica.agregarDireccion(idUsuario, direccion);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto) {
        return agregarDireccion(idUsuario, direccionDto); // La lógica es la misma: reemplazar o agregar.
    }

    @Override
    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        boolean result = empresaLogistica.eliminarDireccion(idUsuario, aliasDireccion);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public List<MetodoPagoDto> obtenerMetodosPago(String idUsuario) {
        return mapper.getMetodosPagoDto(empresaLogistica.obtenerMetodosPago(idUsuario));
    }

    @Override
    public boolean agregarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        boolean result = empresaLogistica.agregarMetodoPago(idUsuario, metodoPago);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean actualizarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        boolean result = empresaLogistica.actualizarMetodoPago(idUsuario, metodoPago);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    @Override
    public boolean eliminarMetodoPago(String idUsuario, String aliasMetodoPago) {
        boolean result = empresaLogistica.eliminarMetodoPago(idUsuario, aliasMetodoPago);
        if (result) {
            notifyDataChanged();
        }
        return result;
    }

    // Métodos para métricas
    public double calcularTiempoPromedioEntrega(List<EnvioDto> enviosDto) {
        List<Envio> envios = enviosDto.stream()
                .map(this::obtenerEnvioOriginalDesdeDto)
                .collect(Collectors.toList());
        return empresaLogistica.calcularTiempoPromedioEntrega(envios);
    }

    public double calcularIngresosTotales(List<EnvioDto> enviosDto) {
        List<Envio> envios = enviosDto.stream()
                .map(this::obtenerEnvioOriginalDesdeDto)
                .collect(Collectors.toList());
        return empresaLogistica.calcularIngresosTotales(envios);
    }

    public long contarIncidencias(List<EnvioDto> enviosDto) {
        List<Envio> envios = enviosDto.stream()
                .map(this::obtenerEnvioOriginalDesdeDto)
                .collect(Collectors.toList());
        return empresaLogistica.contarIncidencias(envios);
    }

    public List<String> obtenerZonasDeRepartidores() {
        return empresaLogistica.getRepartidores().stream().map(Repartidor::getZonaCobertura).distinct().collect(Collectors.toList());
    }

    private Envio obtenerEnvioOriginalDesdeDto(EnvioDto envioDto) {
        return empresaLogistica.obtenerEnvio(envioDto.id());
    }
}
