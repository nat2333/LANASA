package co.edu.unbosque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class ProveedorProductoDTOs {

	public record ActualizarProveedorProductoRequest(
	        @Digits(integer=1, fraction=2) BigDecimal calificacion
	    ) {}
	
	public record CrearProveedorProductoRequest(
	        @NotNull Integer idProducto,
	        @NotNull Integer idProveedor,
	        @Digits(integer=1, fraction=2) BigDecimal calificacion
	    ) {}
	
	public record ProveedorProductoDTO(
	        Integer idProveedorProducto,
	        Integer idProducto,
	        String  skuProducto,
	        String  nombreProducto,
	        Integer idProveedor,
	        String  rutProveedor,
	        String  nombreComercialProveedor,
	        BigDecimal calificacion,
	        Boolean estado
	    ) {}
}
