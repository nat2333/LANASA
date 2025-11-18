package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class VentasMensualesDTO {

	private Integer anio;
    private Integer mes;
    private BigDecimal totalVentas;
    private Long numeroFacturas;
    
	public VentasMensualesDTO(Integer anio, Integer mes, BigDecimal totalVentas, Long numeroFacturas) {
		super();
		this.anio = anio;
		this.mes = mes;
		this.totalVentas = totalVentas;
		this.numeroFacturas = numeroFacturas;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public BigDecimal getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}

	public Long getNumeroFacturas() {
		return numeroFacturas;
	}

	public void setNumeroFacturas(Long numeroFacturas) {
		this.numeroFacturas = numeroFacturas;
	}
    
    
}
