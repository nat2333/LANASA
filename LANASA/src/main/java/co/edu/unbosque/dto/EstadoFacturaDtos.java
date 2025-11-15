package co.edu.unbosque.dto;

import jakarta.validation.constraints.*;

public class EstadoFacturaDtos {
    private EstadoFacturaDtos(){}

    public static record CrearEstadoFacturaRequest(
        @NotBlank @Size(max=40) String nombre
    ) {}

    public static record ActualizarEstadoFacturaRequest(
        @Size(max=40) String nombre
    ) {}

    public static record EstadoFacturaDTO(
        Short idEstadoFactura,
        String nombre,
        Boolean estado
    ) {}
}
