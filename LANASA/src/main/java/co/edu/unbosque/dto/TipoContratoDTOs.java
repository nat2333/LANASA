package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoContratoDTOs {

	public record ActualizarTipoContratoRequest(
	        @Size(max = 100) String nombreTipocontrato
	    ) {}
	
	public record CrearTipoContratoRequest(
	        @NotBlank @Size(max = 100) String nombreTipocontrato
	    ) {}
	
	public record TipoContratoDTO(
	        Short id,
	        String nombreTipocontrato,
	        Boolean estado
	    ) {}

}
