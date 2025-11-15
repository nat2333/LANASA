package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public final class FacturaCompraDTOs {

    public static record CrearFacturaCompraRequest(
        @NotNull Integer idOrdenCompra,
        @NotNull @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal montoTotal,
        @NotNull Short idEstadoFactura
    ) {}

    public static record ActualizarFacturaCompraRequest(
        Short idEstadoFactura
    ) {}

    public static record FacturaCompraDTO(
        Integer idFacturaCompra,
        String numero,
        LocalDateTime fechaFactura,
        BigDecimal montoTotal,
        Boolean estado,
        String numeroOrden,
        String nombreEstadoFactura
    ) {}
}
