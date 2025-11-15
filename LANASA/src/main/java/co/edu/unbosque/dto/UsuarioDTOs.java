package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTOs {

	public static record ActualizarUsuarioRequest(
			@NotBlank() @Size(max = 50)
			String login,
			@NotBlank() 
			String clave,
			@NotBlank()
			Short tipoUsuarioID
		) {}
	
	public static record CrearUsuarioRequest(
			@NotBlank() @Size(max = 50)
			String login,
			@NotBlank() 
			String clave,
			@NotBlank()
			Short tipoUsuarioID
	) {}
	
	public static record UsuarioDTO(
			Integer id,
		    String login,
		    boolean estado,
		    String tipoUsuarioNombre
	){}
}
