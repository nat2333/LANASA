package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DepartamentoDTOs {

	public record DepartamentoDTO(
			Integer id,
		    String nombre,
		    String codigo,
		    LocalDateTime fechaCreacion,
		    BigDecimal presupuestoAnual,
		    Boolean estado
		) {}
	
	public record CrearDepartamentoRequest(
	        @NotBlank @Size(max = 100) String nombre,
	        @NotNull  @DecimalMin(value = "0.00") BigDecimal presupuestoAnual
	) {}
	
	public record ActualizarDepartamentoRequest(
	        @DecimalMin(value = "0.00") BigDecimal presupuestoAnual
	) {}
}
