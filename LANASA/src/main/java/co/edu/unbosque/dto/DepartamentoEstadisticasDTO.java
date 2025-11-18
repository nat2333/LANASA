package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class DepartamentoEstadisticasDTO {

	private String nombreDepartamento;
    private String codigo;
    private Long cantidadEmpleados;
    private BigDecimal nominaTotal;
    private BigDecimal presupuestoAnual;
    private BigDecimal diferencia;
    
	public DepartamentoEstadisticasDTO(String nombreDepartamento, String codigo, Long cantidadEmpleados,
			BigDecimal nominaTotal, BigDecimal presupuestoAnual, BigDecimal diferencia) {
		super();
		this.nombreDepartamento = nombreDepartamento;
		this.codigo = codigo;
		this.cantidadEmpleados = cantidadEmpleados;
		this.nominaTotal = nominaTotal;
		this.presupuestoAnual = presupuestoAnual;
		this.diferencia = diferencia;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getCantidadEmpleados() {
		return cantidadEmpleados;
	}

	public void setCantidadEmpleados(Long cantidadEmpleados) {
		this.cantidadEmpleados = cantidadEmpleados;
	}

	public BigDecimal getNominaTotal() {
		return nominaTotal;
	}

	public void setNominaTotal(BigDecimal nominaTotal) {
		this.nominaTotal = nominaTotal;
	}

	public BigDecimal getPresupuestoAnual() {
		return presupuestoAnual;
	}

	public void setPresupuestoAnual(BigDecimal presupuestoAnual) {
		this.presupuestoAnual = presupuestoAnual;
	}

	public BigDecimal getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(BigDecimal diferencia) {
		this.diferencia = diferencia;
	}
    
	
    
}
