package co.edu.unbosque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProveedorDTOs {

	public record ActualizarProveedorRequest(
	        @Size(max=40)            String telefono,
	        @Email @Size(max=150)    String correo,
	        @Size(max=150)           String direccion,
	        @Size(max=80)            String ciudad,
	        @Size(max=80)            String pais,
	        @Digits(integer=1, fraction=2) BigDecimal calificacion
	    ) {}

	public record CrearProveedorRequest(
	        @NotBlank @Size(max=40)  String rut,
	        @NotBlank @Size(max=150) String nombreComercial,
	        @Size(max=40)            String telefono,
	        @Email @Size(max=150)    String correo,
	        @Size(max=150)           String direccion,
	        @Size(max=80)            String ciudad,
	        @Size(max=80)            String pais,
	        @Size(max=80)            String categoria,
	        @Digits(integer=1, fraction=2) BigDecimal calificacion 
	    ) {}
	
	public record ProveedorDTO(
	        Integer idProveedor,
	        String rut,
	        String nombreComercial,
	        String telefono,
	        String correo,
	        String direccion,
	        String ciudad,
	        String pais,
	        String categoria,
	        BigDecimal calificacion,
	        Boolean estado
	    ) {}
}
