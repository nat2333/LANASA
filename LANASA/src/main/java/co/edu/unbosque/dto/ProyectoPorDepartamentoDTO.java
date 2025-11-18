package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProyectoPorDepartamentoDTO {

	private Integer idDepartamento;
    private String nombreDepartamento;
    private Long numeroProyectos;
    private BigDecimal presupuestoTotalAprobado;
    private BigDecimal presupuestoTotalUtilizado;
	
    public ProyectoPorDepartamentoDTO(Integer idDepartamento, String nombreDepartamento, Long numeroProyectos,
			BigDecimal presupuestoTotalAprobado, BigDecimal presupuestoTotalUtilizado) {
		super();
		this.idDepartamento = idDepartamento;
		this.nombreDepartamento = nombreDepartamento;
		this.numeroProyectos = numeroProyectos;
		this.presupuestoTotalAprobado = presupuestoTotalAprobado;
		this.presupuestoTotalUtilizado = presupuestoTotalUtilizado;
	}
	public Integer getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
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
