package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class ProyectoPresupuestoDTO {

	private Integer idProyecto;
    private String codigo;
    private String nombreProyecto;
    private String nombreDepartamento;
    private String correoCliente;
    private String tipoProyecto;
    private BigDecimal presupuestoAprobado;
    private BigDecimal presupuestoUtilizado;
    private BigDecimal saldoPresupuesto;
    private BigDecimal porcentajeUtilizado;
    private boolean sobrepasa;
    
	public ProyectoPresupuestoDTO(Integer idProyecto, String codigo, String nombreProyecto, String nombreDepartamento,
			String correoCliente, String tipoProyecto, BigDecimal presupuestoAprobado, BigDecimal presupuestoUtilizado,
			BigDecimal saldoPresupuesto, BigDecimal porcentajeUtilizado, boolean sobrepasa) {
		super();
		this.idProyecto = idProyecto;
		this.codigo = codigo;
		this.nombreProyecto = nombreProyecto;
		this.nombreDepartamento = nombreDepartamento;
		this.correoCliente = correoCliente;
		this.tipoProyecto = tipoProyecto;
		this.presupuestoAprobado = presupuestoAprobado;
		this.presupuestoUtilizado = presupuestoUtilizado;
		this.saldoPresupuesto = saldoPresupuesto;
		this.porcentajeUtilizado = porcentajeUtilizado;
		this.sobrepasa = sobrepasa;
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

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
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

	public BigDecimal getSaldoPresupuesto() {
		return saldoPresupuesto;
	}

	public void setSaldoPresupuesto(BigDecimal saldoPresupuesto) {
		this.saldoPresupuesto = saldoPresupuesto;
	}

	public BigDecimal getPorcentajeUtilizado() {
		return porcentajeUtilizado;
	}

	public void setPorcentajeUtilizado(BigDecimal porcentajeUtilizado) {
		this.porcentajeUtilizado = porcentajeUtilizado;
	}

	public boolean isSobrepasa() {
		return sobrepasa;
	}

	public void setSobrepasa(boolean sobrepasa) {
		this.sobrepasa = sobrepasa;
	}
	
	
    
    
}
