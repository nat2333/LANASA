package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the Factura_Compra database table.
 * 
 */
@Entity
@Table(name="factura_compra")
public class FacturaCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_factura_compra", nullable=false)
	private int idFacturaCompra;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="fecha_factura", nullable=false)
	private LocalDateTime fechaFactura;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_orden_compra")
	private OrdenCompra ordenCompra;

    @Column(name="monto_total", nullable=false, precision=12, scale=2)
	private BigDecimal montoTotal;

	@Column(name="numero", nullable=false, length=60, unique=true)
	private String numero;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_factura")
	private EstadoFactura estadoFactura;

	public FacturaCompra() {
	}

	public int getIdFacturaCompra() {
		return this.idFacturaCompra;
	}

	public void setIdFacturaCompra(int idFacturaCompra) {
		this.idFacturaCompra = idFacturaCompra;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(LocalDateTime fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public OrdenCompra getIdOrdenCompra() {
		return this.ordenCompra;
	}

	public void setIdOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public BigDecimal getMontoTotal() {
		return this.montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public EstadoFactura getEstadoFactura() {
		return this.estadoFactura;
	}

	public void setEstadoFactura(EstadoFactura estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

}
