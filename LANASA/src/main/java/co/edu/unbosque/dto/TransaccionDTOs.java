package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public final class TransaccionDTOs {
    private TransaccionDTOs(){}

    public static record CrearTransaccionRequest(
        @NotNull Integer idFacturaVenta,
        @NotNull Short idMetodoPago,           // PK de MetodoPago
        @NotNull Short idEstadoTransaccion,    // PK de EstadoTransaccion
        @NotNull @DecimalMin("0.00") BigDecimal valor,
        LocalDateTime fechaHora                // opcional; si null se pone now()
    ) {}

    public static record ActualizarTransaccionRequest(
        Short idMetodoPago,
        Short idEstadoTransaccion,
        @DecimalMin("0.00") BigDecimal valor,
        LocalDateTime fechaHora,
        Boolean estado
    ) {}

    public static record TransaccionDTO(
        Integer idTransaccion,
        Integer idFacturaVenta,
        String numeroFactura,
        Short idMetodoPago,
        String metodoPago,
        Short idEstadoTransaccion,
        String estadoTransaccion,
        BigDecimal valor,
        LocalDateTime fechaHora,
        Boolean estado
    ) {}
}
