package co.edu.unbosque.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CargoCostoDTO {

	private String cargo;
	private Long cantidadEmpleados;
	private BigDecimal costoTotal;
	private BigDecimal costoPromedio;

	public CargoCostoDTO(String cargo, Long cantidadEmpleados, BigDecimal costoTotal) {
		this.cargo = cargo;
		this.cantidadEmpleados = cantidadEmpleados;
		this.costoTotal = costoTotal;
		this.costoPromedio = costoTotal.divide(BigDecimal.valueOf(cantidadEmpleados), 2, RoundingMode.HALF_UP);
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Long getCantidadEmpleados() {
		return cantidadEmpleados;
	}

	public void setCantidadEmpleados(Long cantidadEmpleados) {
		this.cantidadEmpleados = cantidadEmpleados;
	}

	public BigDecimal getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(BigDecimal costoTotal) {
		this.costoTotal = costoTotal;
	}

	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}
	
	
}
