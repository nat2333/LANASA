package co.edu.unbosque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductoDTOs {

	public record ActualizarProductoRequest(
	        @Size(max=150) String nombre,
	        String descripcion,
	        @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioCompra,
	        @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioVentaSugerido,
	        @Min(0) Integer stockMinimo,
	        @Min(0) Integer stockActual,
	        @Min(0) Integer stockMaximo
	    ) {}
	
	public record CrearProductoRequest(
	        @NotBlank @Size(max=60)  String sku,
	        @NotBlank @Size(max=150) String nombre,
	        String descripcion,
	        @Size(max=80)            String categoria,
	        @NotNull @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioCompra,
	        @DecimalMin("0.00") @Digits(integer=10, fraction=2) BigDecimal precioVentaSugerido,
	        @Min(0) Integer stockMinimo,
	        @Min(0) Integer stockActual,
	        @Min(0) Integer stockMaximo
	    ) {}
	
	public record ProductoDTO(
	        Integer idProducto,
	        String sku,
	        String nombre,
	        String descripcion,
	        String categoria,
	        BigDecimal precioCompra,
	        BigDecimal precioVentaSugerido,
	        Integer stockMinimo,
	        Integer stockActual,
	        Integer stockMaximo,
	        Boolean estado
	    ) {}
}
