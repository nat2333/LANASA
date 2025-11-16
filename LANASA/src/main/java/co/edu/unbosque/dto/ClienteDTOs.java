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
			@Email @Size(max=150) String correo,
			@Size(max=150) String nombre,
			@Size(max=150) String razonSocial,
			@Size(max=60) String primerNombre,
			@Size(max=60) String segundoNombre,
			@Size(max=60) String primerApellido,
			@Size(max=60) String segundoApellido
			) {}

	public record ClienteDTO(
			Integer id,
			Boolean estado,
			String direccion,
			String pais,
			String ciudad,
			String telefono,
			String correo,
			Short idTipoCliente,
			String tipoCliente,
			// Datos de Empresa 
			String nombreEmpresa,
			String rut,
			String razonSocial,
			// Datos de Persona 
			String cedula,
			String primerNombre,
			String segundoNombre,
			String primerApellido,
			String segundoApellido
			) {}

	public record CrearClienteRequest(
			@NotNull  Short  idTipoCliente,
			@Size(max=150)   String direccion,
			@Size(max=80)    String pais,
			@Size(max=80)    String ciudad,
			@NotBlank @Size(max=40)  String telefono,
			@NotBlank @Size(max=150) String correo,
			
			@Size(max=150) String nombreEmpresa,
	        @Size(max=40)  String rut,          
	        @Size(max=150) String razonSocial,
	                 
	        @Size(max=30) String cedula,           
	        @Size(max=60) String primerNombre,
	        @Size(max=60) String segundoNombre,
	        @Size(max=60) String primerApellido,
	        @Size(max=60) String segundoApellido
			) {}
}
