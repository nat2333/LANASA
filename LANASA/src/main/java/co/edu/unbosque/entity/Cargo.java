package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Cargo database table.
 * 
 */
@Entity
@Table(name="cargo")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cargo", nullable=false)
	private Short idCargo;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="nombre_cargo", nullable=false, unique=true)
	private String nombreCargo;

	@Column(name="salario", precision=12, scale=2, nullable=false)
	private BigDecimal salario;

	public Cargo() {
	}

	public Short getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(Short idCargo) {
		this.idCargo = idCargo;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombreCargo() {
		return this.nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public BigDecimal getSalario() {
		return this.salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

}