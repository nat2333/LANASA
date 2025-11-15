package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Rol_empleado database table.
 * 
 */
@Entity
@Table(name="rol_empleado")
public class RolEmpleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rol_empleado")
	private short idRolEmpleado;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="rol_empleado", nullable=false, unique=true, length=80)
	private String rolEmpleado;

	@Column(name="tarifa_hora", precision=10, scale=2)
	private BigDecimal tarifaHora;


	public RolEmpleado() {
	}

	public short getIdRolEmpleado() {
		return this.idRolEmpleado;
	}

	public void setIdRolEmpleado(short idRolEmpleado) {
		this.idRolEmpleado = idRolEmpleado;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getRolEmpleado() {
		return this.rolEmpleado;
	}

	public void setRolEmpleado(String rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	}

	public BigDecimal getTarifaHora() {
		return this.tarifaHora;
	}

	public void setTarifaHora(BigDecimal tarifaHora) {
		this.tarifaHora = tarifaHora;
	}

}
