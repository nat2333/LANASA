package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProductoUtilidadDTO {

	private String sku;
	private String nombre;
	private String categoria;
	private BigDecimal precioCompra;
	private BigDecimal precioVentaSugerido;
	private BigDecimal precioVentaPromedio;
	private BigDecimal utilidadReal;
	private BigDecimal utilidadRealPorcentaje;
	private BigDecimal utilidadPotencial;
	private BigDecimal utilidadmPotencialPorcentaje;

	public ProductoUtilidadDTO(String sku, String nombre, String categoria, BigDecimal precioCompra,
			BigDecimal precioVentaSugerido, BigDecimal precioVentaPromedio, BigDecimal utilidadReal,
			BigDecimal utilidadRealPorcentaje, BigDecimal utilidadPotencial, BigDecimal utilidadmPotencialPorcentaje) {
		super();
		this.sku = sku;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precioCompra = precioCompra;
		this.precioVentaSugerido = precioVentaSugerido;
		this.precioVentaPromedio = precioVentaPromedio;
		this.utilidadReal = utilidadReal;
		this.utilidadRealPorcentaje = utilidadRealPorcentaje;
		this.utilidadPotencial = utilidadPotencial;
		this.utilidadmPotencialPorcentaje = utilidadmPotencialPorcentaje;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}
	public BigDecimal getPrecioVentaSugerido() {
		return precioVentaSugerido;
	}
	public void setPrecioVentaSugerido(BigDecimal precioVentaSugerido) {
		this.precioVentaSugerido = precioVentaSugerido;
	}
	public BigDecimal getPrecioVentaPromedio() {
		return precioVentaPromedio;
	}
	public void setPrecioVentaPromedio(BigDecimal precioVentaPromedio) {
		this.precioVentaPromedio = precioVentaPromedio;
	}
	public BigDecimal getUtilidadReal() {
		return utilidadReal;
	}
	public void setUtilidadReal(BigDecimal utilidadReal) {
		this.utilidadReal = utilidadReal;
	}
	public BigDecimal getUtilidadRealPorcentaje() {
		return utilidadRealPorcentaje;
	}
	public void setUtilidadRealPorcentaje(BigDecimal utilidadRealPorcentaje) {
		this.utilidadRealPorcentaje = utilidadRealPorcentaje;
	}
	public BigDecimal getUtilidadPotencial() {
		return utilidadPotencial;
	}
	public void setUtilidadPotencial(BigDecimal utilidadPotencial) {
		this.utilidadPotencial = utilidadPotencial;
	}
	public BigDecimal getUtilidadmPotencialPorcentaje() {
		return utilidadmPotencialPorcentaje;
	}
	public void setUtilidadmPotencialPorcentaje(BigDecimal utilidadmPotencialPorcentaje) {
		this.utilidadmPotencialPorcentaje = utilidadmPotencialPorcentaje;
	}


}
