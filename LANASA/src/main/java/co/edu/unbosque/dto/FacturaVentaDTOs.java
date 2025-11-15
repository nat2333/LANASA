package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class FacturaVentaDTOs {

	public record CrearFacturaVentaRequest(
	        @NotNull Integer idCliente,
	        Integer idProyecto,             
	        Short idEstadoFactura,   
	        @NotNull LocalDateTime fechaFacturaVenta,
	        @NotNull @DecimalMin("0.00") BigDecimal subtotal,
	        @NotNull @DecimalMin("0.00") BigDecimal impuestos
	    ) {}
	
	public record ActualizarFacturaVentaRequest(
	        Short idEstadoFactura
	    ) {}
	
	public record FacturaVentaDTO(
	        Integer idFacturaVenta,
	        String numero,
	        LocalDateTime fechaFacturaVenta,
	        BigDecimal subtotal,
	        BigDecimal impuestos,
	        BigDecimal total,
	        Boolean estado,
	        Integer idCliente,
	        String correoCliente,
	        Integer idProyecto,
	        String codigoProyecto,
	        Short idEstadoFactura,
	        String nombreEstadoFactura
	    ) {}
}
