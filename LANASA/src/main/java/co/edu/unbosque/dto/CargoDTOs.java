package co.edu.unbosque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CargoDTOs {

	public  record ActualizarCargoRequest(
			@NotNull  @DecimalMin(value = "0.00") BigDecimal salario) {};

    public record CargoDTO(
    		Short id,
    		String nombreCargo,
    		BigDecimal salario,
    		boolean estado
    		) {};	
					
	public record CrearCargoRequest(
			@NotBlank @Size(max = 100) String nombreCargo,
			@NotNull  @DecimalMin(value = "0.00") BigDecimal salario
			) {}
}
