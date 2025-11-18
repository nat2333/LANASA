package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProveedorCalificacionDTO {

    private String nombreComercial;
    private BigDecimal calificacion;
    
	public ProveedorCalificacionDTO(String nombreComercial, BigDecimal calificacion) {
		super();
		this.nombreComercial = nombreComercial;
		this.calificacion = calificacion;
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
    
    
}
