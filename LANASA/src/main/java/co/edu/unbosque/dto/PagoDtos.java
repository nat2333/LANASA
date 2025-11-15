package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public final class PagoDtos {
    private PagoDtos(){}

    public static record CrearPagoRequest(
        @NotNull Integer idFacturaCompra,
        @NotNull Short idMetodoPago,
        @NotNull @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal monto
    ) {}

    public static record PagoDTO(
        Integer idPago,
        String numeroFactura,
        String nombreMetodoPago,
        LocalDateTime fechaPago,
        BigDecimal monto,
        Boolean estado
    ) {}

    public static record ResumenPagosFacturaDTO(
        Integer idFacturaCompra,
        String numeroFactura,
        BigDecimal totalFactura,
        BigDecimal totalPagado,
        BigDecimal saldoPendiente
    ) {}
}
