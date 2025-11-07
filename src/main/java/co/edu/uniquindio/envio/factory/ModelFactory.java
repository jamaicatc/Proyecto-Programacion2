package co.edu.uniquindio.envio.factory;

import co.edu.uniquindio.envio.mapping.dto.*;
import co.edu.uniquindio.envio.mapping.mappers.EmpresaLogisticaMappingImpl;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.model.strategy.ITarifaStrategy;
import co.edu.uniquindio.envio.model.strategy.TarifaBase;
import co.edu.uniquindio.envio.services.IEnvioServices;
import co.edu.uniquindio.envio.services.IModelFactory;
import co.edu.uniquindio.envio.services.IUsuarioServices;
import co.edu.uniquindio.envio.utils.DataUtil;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class ModelFactory implements IModelFactory, IUsuarioServices, IEnvioServices {
    private static ModelFactory modelFactory;
    private final EmpresaLogistica empresaLogistica;
    private final EmpresaLogisticaMappingImpl mapper;

    public static SimpleBooleanProperty datosActualizadosProperty = new SimpleBooleanProperty(false);

    private ModelFactory() {
        this.mapper = new EmpresaLogisticaMappingImpl();
        this.empresaLogistica = DataUtil.inicializarDatos();
    }

    public static ModelFactory getInstance() {
        if (modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    public IUsuarioServices getUsuarioServices() {
        return this;
    }

    public IEnvioServices getEnvioServices() {
        return this;
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return mapper.getUsuariosDto(empresaLogistica.getListaUsuarios());
    }

    @Override
    public UsuarioDto obtenerUsuarioPorNombre(String nombre) {
        return mapper.usuarioToUsuarioDto(empresaLogistica.obtenerUsuarioPorNombre(nombre));
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        return empresaLogistica.agregarUsuario(usuario);
    }

    @Override
    public boolean eliminarUsuario(String idUsuario) {
        return empresaLogistica.eliminarUsuario(idUsuario);
    }

    @Override
    public boolean actualizarUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        return empresaLogistica.actualizarUsuario(usuario);
    }

    @Override
    public List<RepartidorDto> obtenerRepartidores() {
        return mapper.getRepartidoresDto(empresaLogistica.getListaRepartidores());
    }

    @Override
    public boolean agregarRepartidor(RepartidorDto repartidorDto) {
        Repartidor repartidor = mapper.repartidorDtoToRepartidor(repartidorDto);
        return empresaLogistica.agregarRepartidor(repartidor);
    }

    @Override
    public boolean eliminarRepartidor(String idRepartidor) {
        return empresaLogistica.eliminarRepartidor(idRepartidor);
    }

    @Override
    public boolean actualizarRepartidor(RepartidorDto repartidorDto) {
        Repartidor repartidor = mapper.repartidorDtoToRepartidor(repartidorDto);
        return empresaLogistica.actualizarRepartidor(repartidor);
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
        return empresaLogistica.agregarEnvio(idUsuario, envio);
    }

    @Override
    public boolean actualizarEnvio(EnvioDto envioDto) {
        Envio envio = mapper.envioDtoToEnvio(envioDto);
        return empresaLogistica.actualizarEnvio(envio);
    }

    @Override
    public boolean eliminarEnvio(String idEnvio) {
        return empresaLogistica.eliminarEnvio(idEnvio);
    }

    @Override
    public Factura pagarEnvio(String idEnvio, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        return empresaLogistica.pagarEnvio(idEnvio, metodoPago);
    }

    @Override
    public EnvioDto obtenerEnvio(String numeroSeguimiento) {
        return mapper.envioToEnvioDto(empresaLogistica.obtenerEnvio(numeroSeguimiento));
    }

    @Override
    public List<String> obtenerHistorial(String idEnvio) {
        return empresaLogistica.obtenerEnvio(idEnvio).getHistorial();
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
        return empresaLogistica.agregarDireccion(idUsuario, direccion);
    }

    @Override
    public boolean actualizarDireccion(String idUsuario, DireccionDto direccionDto) {
        Direccion direccion = mapper.direccionDtoToDireccion(direccionDto);
        return empresaLogistica.actualizarDireccion(idUsuario, direccion);
    }

    @Override
    public boolean eliminarDireccion(String idUsuario, String aliasDireccion) {
        return empresaLogistica.eliminarDireccion(idUsuario, aliasDireccion);
    }

    @Override
    public List<MetodoPagoDto> obtenerMetodosPago(String idUsuario) {
        return mapper.getMetodosPagoDto(empresaLogistica.obtenerMetodosPago(idUsuario));
    }

    @Override
    public boolean agregarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        return empresaLogistica.agregarMetodoPago(idUsuario, metodoPago);
    }

    @Override
    public boolean actualizarMetodoPago(String idUsuario, MetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = mapper.metodoPagoDtoToMetodoPago(metodoPagoDto);
        return empresaLogistica.actualizarMetodoPago(idUsuario, metodoPago);
    }

    @Override
    public boolean eliminarMetodoPago(String idUsuario, String aliasMetodoPago) {
        return empresaLogistica.eliminarMetodoPago(idUsuario, aliasMetodoPago);
    }
}
