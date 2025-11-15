package co.edu.unbosque.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class HistorialEmpleadoDTOs {

	public record ActualizarHistorialRequest(
	        LocalDate fechaFin
	    ) {}
	
	public record CrearHistorialRequest(
	        @NotNull Integer idEmpleado,
	        @NotNull Integer idDepartamento,
	        @NotNull Short   idCargo,
	        @NotNull LocalDate fechaInicio,
	        LocalDate fechaFin   
	    ) {}
	
	public record HistorialDTO(
	        Integer id,
	        Boolean estado,
	        LocalDate fechaInicio,
	        LocalDate fechaFin,
	        String cedulaEmpleado,
	        String nombreDepartamento,
	        String nombreCargo
	    ) {}
}
