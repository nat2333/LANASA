package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProyectoPorClienteDTO {

	private Integer idCliente;
    private String correoCliente;
    private String pais;
    private String ciudad;
    private Long numeroProyectos;
    private BigDecimal presupuestoTotalAprobado;
    private BigDecimal presupuestoTotalUtilizado;
    
	public ProyectoPorClienteDTO(Integer idCliente, String correoCliente, String pais, String ciudad, Long numeroProyectos,
			BigDecimal presupuestoTotalAprobado, BigDecimal presupuestoTotalUtilizado) {
		super();
		this.idCliente = idCliente;
		this.correoCliente = correoCliente;
		this.pais = pais;
		this.ciudad = ciudad;
		this.numeroProyectos = numeroProyectos;
		this.presupuestoTotalAprobado = presupuestoTotalAprobado;
		this.presupuestoTotalUtilizado = presupuestoTotalUtilizado;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
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

	public Long getNumeroProyectos() {
		return numeroProyectos;
	}

	public void setNumeroProyectos(Long numeroProyectos) {
		this.numeroProyectos = numeroProyectos;
	}

	public BigDecimal getPresupuestoTotalAprobado() {
		return presupuestoTotalAprobado;
	}

	public void setPresupuestoTotalAprobado(BigDecimal presupuestoTotalAprobado) {
		this.presupuestoTotalAprobado = presupuestoTotalAprobado;
	}

	public BigDecimal getPresupuestoTotalUtilizado() {
		return presupuestoTotalUtilizado;
	}

	public void setPresupuestoTotalUtilizado(BigDecimal presupuestoTotalUtilizado) {
		this.presupuestoTotalUtilizado = presupuestoTotalUtilizado;
	}
    
    
}
