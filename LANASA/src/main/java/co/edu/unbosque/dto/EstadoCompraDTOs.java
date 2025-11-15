package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EstadoCompraDTOs {

	public record ActualizarEstadoCompraRequest( 
			@Size(max = 50) String estadoCompra
		) {}

	public record CrearEstadoCompraRequest(
	        @NotBlank @Size(max = 50) String estadoCompra
	    ) {}
	
	public record EstadoCompraDTO(
	        Short id,
	        String estadoCompra,
	        Boolean estado
	    ) {}

}
