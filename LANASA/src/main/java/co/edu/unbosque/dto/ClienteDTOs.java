package co.edu.unbosque.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClienteDTOs {

	public record ActualizarClienteRequest(
	        @Size(max=150)   String direccion,
	        @Size(max=80)    String pais,
	        @Size(max=80)    String ciudad,
	        @Size(max=40)    String telefono,
	        @Email @Size(max=150) String correo
	    ) {}
	
	public record ClienteDTO(
	        Integer id,
	        Boolean estado,
	        String direccion,
	        String pais,
	        String ciudad,
	        String telefono,
	        String correo,
	        String   tipoCliente
	    ) {}
	
	public record CrearClienteRequest(
			@NotNull  Short  idTipoCliente,
	        @Size(max=150)   String direccion,
	        @Size(max=80)    String pais,
	        @Size(max=80)    String ciudad,
	        @NotBlank @Size(max=40)  String telefono,
	        @NotBlank @Size(max=150) String correo
	    ) {}
}
