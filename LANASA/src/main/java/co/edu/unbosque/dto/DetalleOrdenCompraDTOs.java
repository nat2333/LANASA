package co.edu.unbosque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DetalleOrdenCompraDTOs {

	public static record CrearDetalleOCRequest(
	        @NotNull Integer idOrdenCompra,
	        @NotNull Integer idProducto,
	        @NotNull @Min(1) Integer cantidad,
	        @NotNull @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioUnitario
	    ) {}
	
	public record ActualizarDetalleOCRequest(
	        @Min(1) Integer cantidad,
	        @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioUnitario
	    ) {}
	
	public record DetalleOrdenCompraDTO(
	        Integer idDetalleOrdenCompra,
	        Integer idOrdenCompra,
	        String  numeroOrden,
	        Integer idProducto,
	        String  skuProducto,
	        String  nombreProducto,
	        Integer cantidad,
	        BigDecimal precioUnitario,
	        BigDecimal subtotal, 
	        Boolean estado
	    ) {}
	
	
}
