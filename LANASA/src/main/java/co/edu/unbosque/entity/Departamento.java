package co.edu.unbosque.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * The persistent class for the Departamento database table.
 * 
 */
@Entity
@Table(name="departamento")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_departamento", nullable=false)
	private Integer idDepartamento;

	@Column(name="codigo", unique=true, nullable=false, length=20)
	private String codigo;

	@Column(name = "estado", nullable = false)
	private boolean estado;

	 @Column(name = "fecha_creacion", nullable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "nombre", unique=true, nullable = false, length = 100)
	private String nombre;

	@Column(name = "presupuesto_anual", precision = 15, scale = 2, nullable = false)
	private BigDecimal presupuestoAnual;

	public Departamento() {
	}

	public Integer getIdDepartamento() {
		return this.idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPresupuestoAnual() {
		return this.presupuestoAnual;
	}

	public void setPresupuestoAnual(BigDecimal presupuestoAnual) {
		this.presupuestoAnual = presupuestoAnual;
	}
}