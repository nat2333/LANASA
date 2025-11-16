package co.edu.unbosque.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.*;


/**
 * The persistent class for the Historial database table.
 * 
 */
@Entity
@Table(name="historial")
public class Historial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_historial", nullable=false)
	private Integer idHistorial;

	@Column(name = "estado", nullable = false)
	private boolean estado;

	@Column(name="fecha_fin")
	private LocalDate fechaFin;

	@Column(name = "fecha_inicio", nullable = false)
	private LocalDate fechaInicio;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
	private Empleado empleado;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_departamento", nullable = false)
	private Departamento departamento;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cargo", nullable = false)
	private Cargo cargo;

	public Historial() {
	}

	public Integer getIdHistorial() {
		return this.idHistorial;
	}

	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
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

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}