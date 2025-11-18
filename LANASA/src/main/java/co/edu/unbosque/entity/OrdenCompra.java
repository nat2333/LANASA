package co.edu.unbosque.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;


/**
 * The persistent class for the Orden_Compra database table.
 * 
 */
@Entity
@Table(name="orden_compra")
public class OrdenCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_orden_compra", nullable=false)
	private int idOrdenCompra;

	@Column(name = "estado", nullable = false)
	private boolean estado;

	@Column(name="fecha_entrega_esperada")
	private LocalDate fechaEntregaEsperada;

	@Column(name="fecha_entrega_real")
	private LocalDate fechaEntregaReal;

	@Column(name = "fecha_orden", nullable = false)
	private LocalDate fechaOrden;

	@Column(name="id_proyecto")
	private Integer idProyecto;

    @Column(name = "numero", nullable = false, unique = true, length = 40)
	private String numero;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_compra")
	private EstadoCompra estadoCompra;

	public OrdenCompra() {
	}

	public int getIdOrdenCompra() {
		return this.idOrdenCompra;
	}

	public void setIdOrdenCompra(int idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechaEntregaEsperada() {
		return this.fechaEntregaEsperada;
	}

	public void setFechaEntregaEsperada(LocalDate fechaEntregaEsperada) {
		this.fechaEntregaEsperada = fechaEntregaEsperada;
	}

	public LocalDate getFechaEntregaReal() {
		return this.fechaEntregaReal;
	}

	public void setFechaEntregaReal(LocalDate fechaEntregaReal) {
		this.fechaEntregaReal = fechaEntregaReal;
	}

	public LocalDate getFechaOrden() {
		return this.fechaOrden;
	}

	public void setFechaOrden(LocalDate fechaOrden) {
		this.fechaOrden = fechaOrden;
	}

	public Integer getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public EstadoCompra getEstadoCompra() {
		return this.estadoCompra;
	}

	public void setEstadoCompra(EstadoCompra estadoCompra) {
		this.estadoCompra = estadoCompra;
	}

}