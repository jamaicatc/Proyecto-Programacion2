package co.edu.uniquindio.envio.mapping.dto;

import java.time.LocalDate;

public record IncidenciaDto(String asunto, String descripcion, LocalDate fecha) {
}
