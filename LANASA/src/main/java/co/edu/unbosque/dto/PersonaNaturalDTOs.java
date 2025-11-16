package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonaNaturalDTOs {

	public static record ActualizarPersonaNaturalRequest(
	        @Size(max=60) String primerNombre,
	        @Size(max=60) String segundoNombre,
	        @Size(max=60) String primerApellido,
	        @Size(max=60) String segundoApellido
	    ) {}
	
	public static record CrearPersonaNaturalRequest(
	        @NotNull Integer idCliente,            
	        @Size(max=30) String cedula,           
	        @NotBlank @Size(max=60) String primerNombre,
	        @Size(max=60) String segundoNombre,
	        @NotBlank @Size(max=60) String primerApellido,
	        @Size(max=60) String segundoApellido
	    ) {}
	
	public static record PersonaNaturalDTO(
	        Integer idCliente,
	        String cedula,
	        String primerNombre,
	        String segundoNombre,
	        String primerApellido,
	        String segundoApellido,
	        Boolean estado
	    ) {}
	
	
}
