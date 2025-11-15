package co.edu.unbosque.dto;

import jakarta.validation.constraints.*;

public final class EstadoTransaccionDTOs {
    private EstadoTransaccionDTOs(){}

    public static record CrearEstadoTransaccionRequest(
        @NotBlank @Size(max=40) String nombre
    ) {}

    public static record ActualizarEstadoTransaccionRequest(
        @Size(max=40) String nombre,
        Boolean estado
    ) {}

    public static record EstadoTransaccionDTO(
        Short id,
        String nombre,
        Boolean estado
    ) {}
}
