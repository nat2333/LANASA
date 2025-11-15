package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * The persistent class for the Proyecto database table.
 * 
 */
@Entity
@Table(name="Proyecto")
public class Proyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_proyecto", nullable=false)
	private int idProyecto;

	@Column(name="codigo", unique=true, length=40)
	private String codigo;

	@Lob
	@Column(name="descripcion")
	private String descripcion;

	@Column(name="estado",  nullable=false)
	private boolean estado;

	@Column(name="fecha_fin_estimada")
	private LocalDate fechaFinEstimada;

	@Column(name="fecha_fin_real")
	private LocalDate fechaFinReal;

	@Column(name="fecha_inicio", nullable=false)
	private LocalDate fechaInicio;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_departamento")
	private Departamento departamento;

	@Column(name="nombre", nullable=false, length=150)
	private String nombre;

	@Column(name="presupuesto_aprobado", precision=14, scale=2)
	private BigDecimal presupuestoAprobado;

	@Column(name="presupuesto_utilizado", precision=14, scale=2)
	private BigDecimal presupuestoUtilizado;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_tipo_proyecto")
	private TipoProyecto tipoProyecto;

	public Proyecto() {
	}

	public int getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechaFinEstimada() {
		return this.fechaFinEstimada;
	}

	public void setFechaFinEstimada(LocalDate fechaFinEstimada) {
		this.fechaFinEstimada = fechaFinEstimada;
	}

	public LocalDate getFechaFinReal() {
		return this.fechaFinReal;
	}

	public void setFechaFinReal(LocalDate fechaFinReal) {
		this.fechaFinReal = fechaFinReal;
	}

	public LocalDate getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPresupuestoAprobado() {
		return this.presupuestoAprobado;
	}

	public void setPresupuestoAprobado(BigDecimal presupuestoAprobado) {
		this.presupuestoAprobado = presupuestoAprobado;
	}

	public BigDecimal getPresupuestoUtilizado() {
		return this.presupuestoUtilizado;
	}

	public void setPresupuestoUtilizado(BigDecimal presupuestoUtilizado) {
		this.presupuestoUtilizado = presupuestoUtilizado;
	}

	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}
}