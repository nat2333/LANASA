package co.edu.unbosque.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public final class DetalleFacturaVentaDTOs {

    public record CrearDetalleFacturaVentaRequest(
        @NotNull Integer idFacturaVenta,
        @NotNull Integer idProducto,
        @NotNull @Min(1) Integer cantidad,
        @NotNull @DecimalMin("0.00") BigDecimal precioUnitario,
        @Size(max=40) String tipo
    ) {}

    public record ActualizarDetalleFacturaVentaRequest(
        @Min(1) Integer cantidad,
        @DecimalMin("0.00") BigDecimal precioUnitario
    ) {}

    public record DetalleFacturaVentaDTO(
        Integer idDetalleFacturaVenta,
        Integer idFacturaVenta,
        Integer idProducto,
        String skuProducto,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotalLinea,           
        String tipo
    ) {}
}
