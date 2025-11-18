package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class CategoriaUtilidadDTO {

	private String categoria;
    private Long cantidadProductos;
    private BigDecimal precioCompraPromedio;
    private BigDecimal precioVentaSugeridoPromedio;
    private BigDecimal utilidadPromedio;
    private BigDecimal utilidadPromedioPorcentaje;
    
	public CategoriaUtilidadDTO(String categoria, Long cantidadProductos, BigDecimal precioCompraPromedio,
			BigDecimal precioVentaSugeridoPromedio, BigDecimal utilidadPromedio,
			BigDecimal utilidadPromedioPorcentaje) {
		super();
		this.categoria = categoria;
		this.cantidadProductos = cantidadProductos;
		this.precioCompraPromedio = precioCompraPromedio;
		this.precioVentaSugeridoPromedio = precioVentaSugeridoPromedio;
		this.utilidadPromedio = utilidadPromedio;
		this.utilidadPromedioPorcentaje = utilidadPromedioPorcentaje;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Long getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(Long cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	public BigDecimal getPrecioCompraPromedio() {
		return precioCompraPromedio;
	}

	public void setPrecioCompraPromedio(BigDecimal precioCompraPromedio) {
		this.precioCompraPromedio = precioCompraPromedio;
	}

	public BigDecimal getPrecioVentaSugeridoPromedio() {
		return precioVentaSugeridoPromedio;
	}

	public void setPrecioVentaSugeridoPromedio(BigDecimal precioVentaSugeridoPromedio) {
		this.precioVentaSugeridoPromedio = precioVentaSugeridoPromedio;
	}

	public BigDecimal getUtilidadPromedio() {
		return utilidadPromedio;
	}

	public void setUtilidadPromedio(BigDecimal utilidadPromedio) {
		this.utilidadPromedio = utilidadPromedio;
	}

	public BigDecimal getUtilidadPromedioPorcentaje() {
		return utilidadPromedioPorcentaje;
	}

	public void setUtilidadPromedioPorcentaje(BigDecimal utilidadPromedioPorcentaje) {
		this.utilidadPromedioPorcentaje = utilidadPromedioPorcentaje;
	}
    
	
    
}
