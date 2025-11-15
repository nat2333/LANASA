package co.edu.unbosque.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class OrdenCompraDTOs {

	public record CrearOrdenCompraRequest(
	        @NotNull Integer idProveedor,
	        Integer idProyecto,
	        @NotNull LocalDate fechaOrden,
	        LocalDate fechaEntregaEsperada,
	        LocalDate fechaEntregaReal,
	        @NotNull short idEstadoCompra
	    ) {}
	
	public record ActualizarOrdenCompraRequest(
	        LocalDate fechaEntregaReal
	    ) {}

	public record OrdenCompraDTO(
	        Integer idOrdenCompra,
	        String numero,
	        LocalDate fechaOrden,
	        LocalDate fechaEntregaEsperada,
	        LocalDate fechaEntregaReal,
	        Integer idProyecto,
	        String nombreComercialProveedor,
	        String nombreEstadoCompra,
	        Boolean estado
	    ) {}
}
