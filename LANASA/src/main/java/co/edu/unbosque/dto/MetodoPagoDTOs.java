package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MetodoPagoDTOs {

	public record ActualizarMetodoPagoRequest(
	        @Size(max = 50) String metodoPago
	    ) {}
	
	public record CrearMetodoPagoRequest(
	        @NotBlank @Size(max = 50) String metodoPago
	    ) {}
	
	public record MetodoPagoDTO(
	        Short id,
	        String metodoPago,
	        Boolean estado
	    ) {}
}
