package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * The persistent class for the Empleado_Proyecto database table.
 * 
 */
@Entity
@Table(name="Empleado_Proyecto")
public class EmpleadoProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empleado_proyecto")
	private int idEmpleadoProyecto;

	@Column(name="estado")
	private boolean estado;

	@Column(name="fecha_fin")
	private LocalDate fechaFin;

	@Column(name="fecha_inicio")
	private LocalDate fechaInicio;

	@Column(name="horas_trabajadas")
	private BigDecimal horasTrabajadas;

	@ManyToOne
	@JoinColumn(name="id_empleado")
	private Empleado empleado;

	@ManyToOne
	@JoinColumn(name="id_proyecto")
	private Proyecto proyecto;

	@ManyToOne
	@JoinColumn(name="id_rol")
	private RolEmpleado rolEmpleado;

	public EmpleadoProyecto() {
	}

	public int getIdEmpleadoProyecto() {
		return this.idEmpleadoProyecto;
	}

	public void setIdEmpleadoProyecto(int idEmpleadoProyecto) {
		this.idEmpleadoProyecto = idEmpleadoProyecto;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDate getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public BigDecimal getHorasTrabajadas() {
		return this.horasTrabajadas;
	}

	public void setHorasTrabajadas(BigDecimal horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public RolEmpleado getRolEmpleado() {
		return this.rolEmpleado;
	}

	public void setRolEmpleado(RolEmpleado rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	}

}
