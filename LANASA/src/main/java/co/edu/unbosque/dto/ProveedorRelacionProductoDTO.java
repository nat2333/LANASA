package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProveedorRelacionProductoDTO {

    private String nombreComercial;
    private BigDecimal calificacion;
    private String skuProducto;
    private String nombreProducto;
    private BigDecimal precioPromedioCompra;
    private Long numeroOrdenes;
    private BigDecimal relacion;
    
	public ProveedorRelacionProductoDTO(String nombreComercial, BigDecimal calificacion, String skuProducto,
			String nombreProducto, BigDecimal precioPromedioCompra, Long numeroOrdenes, BigDecimal relacion) {
		super();
		this.nombreComercial = nombreComercial;
		this.calificacion = calificacion;
		this.skuProducto = skuProducto;
		this.nombreProducto = nombreProducto;
		this.precioPromedioCompra = precioPromedioCompra;
		this.numeroOrdenes = numeroOrdenes;
		this.relacion = relacion;
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

	public Long getNumeroOrdenes() {
		return numeroOrdenes;
	}

	public void setNumeroOrdenes(Long numeroOrdenes) {
		this.numeroOrdenes = numeroOrdenes;
	}

	public BigDecimal getRelacion() {
		return relacion;
	}

	public void setRelacion(BigDecimal relacion) {
		this.relacion = relacion;
	}
    
	
    
}
