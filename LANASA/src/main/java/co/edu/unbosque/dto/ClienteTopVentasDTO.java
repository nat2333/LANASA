package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ClienteTopVentasDTO {

	private String correo;
	private String telefono;
	private String pais;
	private String ciudad;
	private BigDecimal totalComprado;
	private Long numeroFacturas;
	
	public ClienteTopVentasDTO(String correo, String telefono, String pais, String ciudad, BigDecimal totalComprado,
			Long numeroFacturas) {
		super();
		this.correo = correo;
		this.telefono = telefono;
		this.pais = pais;
		this.ciudad = ciudad;
		this.totalComprado = totalComprado;
		this.numeroFacturas = numeroFacturas;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public BigDecimal getTotalComprado() {
		return totalComprado;
	}

	public void setTotalComprado(BigDecimal totalComprado) {
		this.totalComprado = totalComprado;
	}

	public Long getNumeroFacturas() {
		return numeroFacturas;
	}

	public void setNumeroFacturas(Long numeroFacturas) {
		this.numeroFacturas = numeroFacturas;
	}
	
	
	
	
}
