package co.edu.unbosque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpresaDTOs {

	public record ActualizarEmpresaRequest(
	        @Size(max=150) String nombre,
	        @Size(max=150) String razonSocial
	    ) {}
	
	public record CrearEmpresaRequest(
	        @NotNull Integer idCliente,           
	        @Size(max=150) String nombre,
	        @Size(max=40)  String rut,          
	        @NotBlank @Size(max=150) String razonSocial
	    ) {}
	
	public record EmpresaDTO(
	        Integer idCliente,
	        String nombre,
	        String rut,
	        String razonSocial,
	        Boolean estado
	    ) {}
}
