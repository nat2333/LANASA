package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProyectoEstadoDepartamentoDTO {

	private Integer idProyecto;
	private String codigo;
	private String nombreProyecto;
	private String nombreDepartamento;
	private BigDecimal presupuestoAprobado;
	private BigDecimal presupuestoUtilizado;
	private BigDecimal diferenciaPresupuesto;
	private String estadoPresupuesto; // EN_PRESUPUESTO / SOBREPRESUPUESTO / SIN_EJECUCION
	private String estadoEntrega;     // EN_CURSO / A_TIEMPO / CON_RETRASO / SIN_FECHA_ESTIMADA
	private Integer diasRetraso;
	
	public ProyectoEstadoDepartamentoDTO(Integer idProyecto, String codigo, String nombreProyecto, String nombreDepartamento,
			BigDecimal presupuestoAprobado, BigDecimal presupuestoUtilizado, BigDecimal diferenciaPresupuesto,
			String estadoPresupuesto, String estadoEntrega, Integer diasRetraso) {
		super();
		this.idProyecto = idProyecto;
		this.codigo = codigo;
		this.nombreProyecto = nombreProyecto;
		this.nombreDepartamento = nombreDepartamento;
		this.presupuestoAprobado = presupuestoAprobado;
		this.presupuestoUtilizado = presupuestoUtilizado;
		this.diferenciaPresupuesto = diferenciaPresupuesto;
		this.estadoPresupuesto = estadoPresupuesto;
		this.estadoEntrega = estadoEntrega;
		this.diasRetraso = diasRetraso;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public BigDecimal getPresupuestoAprobado() {
		return presupuestoAprobado;
	}

	public void setPresupuestoAprobado(BigDecimal presupuestoAprobado) {
		this.presupuestoAprobado = presupuestoAprobado;
	}

	public BigDecimal getPresupuestoUtilizado() {
		return presupuestoUtilizado;
	}

	public void setPresupuestoUtilizado(BigDecimal presupuestoUtilizado) {
		this.presupuestoUtilizado = presupuestoUtilizado;
	}

	public BigDecimal getDiferenciaPresupuesto() {
		return diferenciaPresupuesto;
	}

	public void setDiferenciaPresupuesto(BigDecimal diferenciaPresupuesto) {
		this.diferenciaPresupuesto = diferenciaPresupuesto;
	}

	public String getEstadoPresupuesto() {
		return estadoPresupuesto;
	}

	public void setEstadoPresupuesto(String estadoPresupuesto) {
		this.estadoPresupuesto = estadoPresupuesto;
	}

	public String getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public Integer getDiasRetraso() {
		return diasRetraso;
	}

	public void setDiasRetraso(Integer diasRetraso) {
		this.diasRetraso = diasRetraso;
	}   
	
	
	
	
}
