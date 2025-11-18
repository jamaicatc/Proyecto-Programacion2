package co.edu.uniquindio.envio.mapping.dto;

public record RepartidorDto(
        String idRepartidor,
        String nombre,
        String documento,
        String telefono,
        String usuario,
        String contrasena,
        String zona,
        String disponibilidad) {
}
