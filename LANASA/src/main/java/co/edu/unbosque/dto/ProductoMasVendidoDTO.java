package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProductoMasVendidoDTO {

    private String nombreProducto;
    private String categoria;
    private Long cantidadVendida;
    private BigDecimal totalVendido;
    
	public ProductoMasVendidoDTO(String nombreProducto, String categoria, Long cantidadVendida,
			BigDecimal totalVendido) {
		super();
		this.nombreProducto = nombreProducto;
		this.categoria = categoria;
		this.cantidadVendida = cantidadVendida;
		this.totalVendido = totalVendido;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Long getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(Long cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	public BigDecimal getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(BigDecimal totalVendido) {
		this.totalVendido = totalVendido;
	}
    
    
}
