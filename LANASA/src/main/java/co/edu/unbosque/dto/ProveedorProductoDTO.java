package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProveedorProductoDTO {

	private String nombreComercial;
    private BigDecimal calificacion;
    private String skuProducto;
    private String nombreProducto;
    private BigDecimal precioPromedioCompra;
    private BigDecimal precioMinimoCompra;
    private BigDecimal precioMaximoCompra;
    private Long numeroOrdenes;
    
	public ProveedorProductoDTO(String nombreComercial, BigDecimal calificacion, String skuProducto,
			String nombreProducto, BigDecimal precioPromedioCompra, BigDecimal precioMinimoCompra,
			BigDecimal precioMaximoCompra, Long numeroOrdenes) {
		super();
		this.nombreComercial = nombreComercial;
		this.calificacion = calificacion;
		this.skuProducto = skuProducto;
		this.nombreProducto = nombreProducto;
		this.precioPromedioCompra = precioPromedioCompra;
		this.precioMinimoCompra = precioMinimoCompra;
		this.precioMaximoCompra = precioMaximoCompra;
		this.numeroOrdenes = numeroOrdenes;
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

	public String getSkuProducto() {
		return skuProducto;
	}

	public void setSkuProducto(String skuProducto) {
		this.skuProducto = skuProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioPromedioCompra() {
		return precioPromedioCompra;
	}

	public void setPrecioPromedioCompra(BigDecimal precioPromedioCompra) {
		this.precioPromedioCompra = precioPromedioCompra;
	}

	public BigDecimal getPrecioMinimoCompra() {
		return precioMinimoCompra;
	}

	public void setPrecioMinimoCompra(BigDecimal precioMinimoCompra) {
		this.precioMinimoCompra = precioMinimoCompra;
	}

	public BigDecimal getPrecioMaximoCompra() {
		return precioMaximoCompra;
	}

	public void setPrecioMaximoCompra(BigDecimal precioMaximoCompra) {
		this.precioMaximoCompra = precioMaximoCompra;
	}

	public Long getNumeroOrdenes() {
		return numeroOrdenes;
	}

	public void setNumeroOrdenes(Long numeroOrdenes) {
		this.numeroOrdenes = numeroOrdenes;
	}
    
	
    
}
