package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class EmpleadoDTOs {

	public static record ActualizarEmpleadoRequest(
	        @Size(max=60)   String primerNombre,
	        @Size(max=60)   String segundoNombre,
	        @Size(max=60)   String primerApellido,
	        @Size(max=60)   String segundoApellido,
	        @Size(max=150)  String direccion,
	        @Size(max=80)   String ciudad,
	        @Size(max=80)   String pais,
	        @DecimalMin("0.00") BigDecimal salario,
	        Short           idCargo,
	        Short           idTipoContrato,
	        Integer         idDepartamento
	    ) {}
	
	public static record CrearEmpleadoRequest(
	        @NotBlank @Size(max=20)  String cedula,
	        @NotBlank @Size(max=60)  String primerNombre,
	        @Size(max=60)            String segundoNombre,
	        @NotBlank @Size(max=60)  String primerApellido,
	        @Size(max=60)            String segundoApellido,
	        @NotBlank @Email @Size(max=100) String correo,
	        @NotNull  @Past          LocalDate fechaNacimiento,
	        @Size(max=150)           String direccion,
	        @Size(max=80)            String ciudad,
	        @Size(max=80)            String pais,
	        @NotNull @DecimalMin("0.00") BigDecimal salario,
	        @NotNull                 Short idCargo,
	        @NotNull                 Short idTipoContrato,
	        @NotNull                 Integer idDepartamento
	    ) {}
	
	public static record EmpleadoDTO(
	        Integer id,
	        String cedula,
	        String primerNombre,
	        String segundoNombre,
	        String primerApellido,
	        String segundoApellido,
	        String correo,
	        LocalDate fechaNacimiento,
	        String direccion,
	        String ciudad,
	        String pais,
	        LocalDate fechaIngreso,
	        BigDecimal salario,
	        boolean estado,
	        Short idCargo,              
	        Short idTipoContrato,     
	        Integer idDepartamento,
	        String nombreCargo,
	        String nombreTipoContrato,
	        String nombreDepartamento
	    ) {}
}
