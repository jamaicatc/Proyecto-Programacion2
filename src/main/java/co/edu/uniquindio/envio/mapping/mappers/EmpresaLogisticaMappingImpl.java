package co.edu.uniquindio.envio.mapping.mappers;

import co.edu.uniquindio.envio.mapping.dto.*;
import co.edu.uniquindio.envio.model.*;
import co.edu.uniquindio.envio.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpresaLogisticaMappingImpl implements IEmpresaLogisticaMapping {

    @Override
    public List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios) {
        if (listaUsuarios == null) {
            return null;
        }
        return listaUsuarios.stream()
                .map(this::usuarioToUsuarioDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDto(
                usuario.getIdUsuario(),
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getTelefono()
        );
    }

    @Override
    public Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) return null;
        return new Usuario(
                usuarioDto.idUsuario(),
                usuarioDto.nombreCompleto(),
                usuarioDto.correo(),
                usuarioDto.telefono()
        );
    }

    @Override
    public List<RepartidorDto> getRepartidoresDto(List<Repartidor> listaRepartidores) {
        if (listaRepartidores == null) {
            return null;
        }
        return listaRepartidores.stream()
                .map(this::repartidorToRepartidorDto)
                .collect(Collectors.toList());
    }

    @Override
    public RepartidorDto repartidorToRepartidorDto(Repartidor repartidor) {
        if (repartidor == null) return null;
        return new RepartidorDto(
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getDocumento(),
                repartidor.getTelefono(),
                repartidor.getZonaCobertura(),
                repartidor.getDisponibilidad()
        );
    }

    @Override
    public Repartidor repartidorDtoToRepartidor(RepartidorDto repartidorDto) {
        if (repartidorDto == null) return null;
        return new Repartidor(
                repartidorDto.idRepartidor(),
                repartidorDto.nombre(),
                repartidorDto.documento(),
                repartidorDto.telefono(),
                repartidorDto.disponibilidad(),
                repartidorDto.zona()
        );
    }

    @Override
    public DireccionDto direccionToDireccionDto(Direccion direccion) {
        if(direccion == null) return null;
        return new DireccionDto(
            direccion.getIdDireccion(),
            direccion.getAlias(),
            direccion.getCalle(),
            direccion.getCiudad(),
            direccion.getCoordenadas()
        );
    }

    @Override
    public Direccion direccionDtoToDireccion(DireccionDto direccionDto) {
        if(direccionDto == null) return null;
        return new Direccion(
            direccionDto.idDireccion(),
            direccionDto.alias(),
            direccionDto.calleCarrera(),
            direccionDto.ciudad(),
            direccionDto.coordenadas()
        );
    }

    @Override
    public List<DireccionDto> getDireccionesDto(List<Direccion> direcciones) {
        if(direcciones == null) return new ArrayList<>();
        return direcciones.stream()
                .map(this::direccionToDireccionDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Direccion> getDirecciones(List<DireccionDto> direccionesDto) {
        if(direccionesDto == null) return new ArrayList<>();
        return direccionesDto.stream()
                .map(this::direccionDtoToDireccion)
                .collect(Collectors.toList());
    }

    public FacturaDto facturaToFacturaDto(Factura factura) {
        if (factura == null) return null;
        return new FacturaDto(
                factura.getIdFactura(),
                factura.getFecha(),
                factura.getMonto(),
                metodoPagoToMetodoPagoDto(factura.getMetodoPago())
        );
    }

    public Factura facturaDtoToFactura(FacturaDto facturaDto) {
        if (facturaDto == null) return null;
        return new Factura(
                facturaDto.idFactura(),
                facturaDto.fecha(),
                facturaDto.monto(),
                metodoPagoDtoToMetodoPago(facturaDto.metodoPago())
        );
    }

    @Override
    public EnvioDto envioToEnvioDto(Envio envio) {
        if (envio == null) return null;
        String ultimaIncidenciaDescripcion = null;
        if (envio.getIncidencias() != null && !envio.getIncidencias().isEmpty()) {
            ultimaIncidenciaDescripcion = envio.getIncidencias().get(envio.getIncidencias().size() - 1).getDescripcion();
        }
        return new EnvioDto(
                envio.getId(),
                envio.getFechaCreacion(),
                envio.getFechaEntrega(),
                envio.getDireccionOrigen(),
                envio.getDireccionDestino(),
                envio.getEstadoActual(),
                envio.getPeso(),
                envio.getLargo(),
                envio.getAncho(),
                envio.getAlto(),
                envio.getCosto(),
                repartidorToRepartidorDto(envio.getRepartidorAsignado()),
                envio.getPago(),
                ultimaIncidenciaDescripcion,
                facturaToFacturaDto(envio.getFactura())
        );
    }

    @Override
    public Envio envioDtoToEnvio(EnvioDto envioDto) {
        if (envioDto == null) return null;
        Envio envio = new Envio(
                envioDto.id(),
                envioDto.fechaCreacion(),
                envioDto.fechaEntrega(),
                envioDto.direccionOrigen(),
                envioDto.direccionDestino(),
                envioDto.estadoActual(),
                envioDto.peso(),
                envioDto.largo(),
                envioDto.ancho(),
                envioDto.alto(),
                envioDto.costo(),
                repartidorDtoToRepartidor(envioDto.repartidorAsignado()),
                envioDto.pago()
        );
        envio.setFactura(facturaDtoToFactura(envioDto.factura()));
        return envio;
    }

    @Override
    public List<EnvioDto> getEnviosDto(List<Envio> listaEnvios) {
        if (listaEnvios == null) {
            return null;
        }
        return listaEnvios.stream()
                .map(this::envioToEnvioDto)
                .collect(Collectors.toList());
    }

    @Override
    public MetodoPagoDto metodoPagoToMetodoPagoDto(MetodoPago metodoPago) {
        if (metodoPago == null) return null;
        return new MetodoPagoDto(
                metodoPago.getAlias(),
                metodoPago.getNumero(),
                metodoPago.getTipo()
        );
    }

    @Override
    public MetodoPago metodoPagoDtoToMetodoPago(MetodoPagoDto metodoPagoDto) {
        if (metodoPagoDto == null) return null;
        return new MetodoPago(
                metodoPagoDto.alias(),
                metodoPagoDto.numero(),
                metodoPagoDto.tipo()
        );
    }

    @Override
    public List<MetodoPagoDto> getMetodosPagoDto(List<MetodoPago> metodosPago) {
        if (metodosPago == null) {
            return null;
        }
        return metodosPago.stream()
                .map(this::metodoPagoToMetodoPagoDto)
                .collect(Collectors.toList());
    }

    public IncidenciaDto incidenciaToIncidenciaDto(Incidencia incidencia) {
        if (incidencia == null) return null;
        return new IncidenciaDto(
                incidencia.getAsunto(),
                incidencia.getDescripcion(),
                incidencia.getFecha()
        );
    }

    public Incidencia incidenciaDtoToIncidencia(IncidenciaDto incidenciaDto) {
        if (incidenciaDto == null) return null;
        return new Incidencia(
                incidenciaDto.asunto(),
                incidenciaDto.descripcion(),
                incidenciaDto.fecha()
        );
    }
}
