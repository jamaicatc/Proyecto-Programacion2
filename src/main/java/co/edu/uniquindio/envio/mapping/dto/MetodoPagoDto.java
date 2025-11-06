package co.edu.uniquindio.envio.mapping.dto;

public record MetodoPagoDto(
        String alias,
        String numero,
        String tipo
) {
    @Override
    public String toString() {
        return alias + " (" + tipo + ")";
    }
}
