package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class EmpleadoSalarioDTO {

	private String nombreCompleto;
    private String cargo;
    private BigDecimal salario;
    
    public EmpleadoSalarioDTO(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String cargo, BigDecimal salario) {
    	    String nombres = primerNombre + (segundoNombre != null && !segundoNombre.isBlank() ? " " + segundoNombre : "");
    	    String apellidos = primerApellido + (segundoApellido != null && !segundoApellido.isBlank() ? " " + segundoApellido : "");
    	    this.nombreCompleto = (nombres + " " + apellidos).trim().replaceAll("\\s+", " ");
    	    this.cargo = cargo;
    	    this.salario = salario;
    	  }

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
    
    
}
