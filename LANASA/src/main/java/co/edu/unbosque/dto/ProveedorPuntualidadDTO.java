package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProveedorPuntualidadDTO {

    private String nombreComercial;
    private BigDecimal calificacion;
    private Long totalOrdenes;
    private BigDecimal promedioDiasRetraso;  
    private BigDecimal porcentajeOrdenesATiempo;
    
	public ProveedorPuntualidadDTO(String nombreComercial, BigDecimal calificacion, Long totalOrdenes,
			BigDecimal promedioDiasRetraso, BigDecimal porcentajeOrdenesATiempo) {
		super();
		this.nombreComercial = nombreComercial;
		this.calificacion = calificacion;
		this.totalOrdenes = totalOrdenes;
		this.promedioDiasRetraso = promedioDiasRetraso;
		this.porcentajeOrdenesATiempo = porcentajeOrdenesATiempo;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public BigDecimal getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	public Long getTotalOrdenes() {
		return totalOrdenes;
	}

	public void setTotalOrdenes(Long totalOrdenes) {
		this.totalOrdenes = totalOrdenes;
	}

	public BigDecimal getPromedioDiasRetraso() {
		return promedioDiasRetraso;
	}

	public void setPromedioDiasRetraso(BigDecimal promedioDiasRetraso) {
		this.promedioDiasRetraso = promedioDiasRetraso;
	}

	public BigDecimal getPorcentajeOrdenesATiempo() {
		return porcentajeOrdenesATiempo;
	}

	public void setPorcentajeOrdenesATiempo(BigDecimal porcentajeOrdenesATiempo) {
		this.porcentajeOrdenesATiempo = porcentajeOrdenesATiempo;
	} 
    
	
    
}
