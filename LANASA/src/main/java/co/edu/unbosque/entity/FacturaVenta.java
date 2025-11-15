package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the Factura_Venta database table.
 * 
 */
@Entity
@Table(name="factura_venta")
public class FacturaVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_factura_venta")
	private int idFacturaVenta;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="fecha_factura_venta", nullable=false)
	private LocalDateTime fechaFacturaVenta;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	@Column(name="impuestos", nullable=false, precision=14, scale=2)
	private BigDecimal impuestos;

	@Column(name="numero", nullable=false, unique=true, length=60)
	private String numero;

	@Column(name="subtotal", nullable=false, precision=14, scale=2)
	private BigDecimal subtotal;

	@Column(name="total", nullable=false, precision=14, scale=2)
	private BigDecimal total;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="id_proyecto")
	private Proyecto proyecto;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="id_estado_factura")
	private EstadoFactura estadoFactura;


	public FacturaVenta() {
	}

	public int getIdFacturaVenta() {
		return this.idFacturaVenta;
	}

	public void setIdFacturaVenta(int idFacturaVenta) {
		this.idFacturaVenta = idFacturaVenta;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaFacturaVenta() {
		return this.fechaFacturaVenta;
	}

	public void setFechaFacturaVenta(LocalDateTime fechaFacturaVenta) {
		this.fechaFacturaVenta = fechaFacturaVenta;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setIdCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getImpuestos() {
		return this.impuestos;
	}

	public void setImpuestos(BigDecimal impuestos) {
		this.impuestos = impuestos;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public EstadoFactura getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(EstadoFactura estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	

}