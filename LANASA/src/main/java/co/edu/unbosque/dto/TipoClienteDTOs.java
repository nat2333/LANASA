package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoClienteDTOs {

	public record CrearTipoClienteRequest(
			@NotBlank @Size(max = 40) String tipo
			) {}
	
	public record TipoClienteDTO(
	        Short id,
	        String tipo,
	        Boolean estado
	    ) {}
}
