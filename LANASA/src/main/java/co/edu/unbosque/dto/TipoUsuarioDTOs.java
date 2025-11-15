package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoUsuarioDTOs {

	public static record CrearTipoUsuarioRequest(
			@NotBlank()
		    @Size(max = 50)
		    String tipo
		) {}
	
	public static record TipoUsuarioDTO(
			Short id, 
			String tipo,
			boolean estado
	) {}
	
	public static record ActualizarTipoUsuarioRequest(
			@NotBlank()
		    @Size(max = 50)
		    String tipo	
	) {}

}
