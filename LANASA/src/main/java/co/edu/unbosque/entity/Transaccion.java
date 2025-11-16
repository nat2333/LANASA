package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the Transaccion database table.
 * 
 */
@Entity
@Table(name="transaccion")
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_transaccion", nullable=false)
	private int idTransaccion;

	@Column(name="estado")
	private boolean estado;

	@Column(name="fecha_hora")
	private LocalDateTime fechaHora;

	@ManyToOne
	@JoinColumn(name="id_metodo_pago")
	private MetodoPago metodoPago;

	@Column(name="valor", precision=10, scale=2)
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name="id_factura_venta")
	private FacturaVenta facturaVenta;

	@ManyToOne
	@JoinColumn(name="id_estado_transaccion")
	private EstadoTransaccion estadoTransaccion;

	public Transaccion() {
	}

	public int getIdTransaccion() {
		return this.idTransaccion;
	}

	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public MetodoPago getIdMetodoPago() {
		return this.metodoPago;
	}

	public void setIdMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public FacturaVenta getFacturaVenta() {
		return this.facturaVenta;
	}

	public void setFacturaVenta(FacturaVenta facturaVenta) {
		this.facturaVenta = facturaVenta;
	}

	public EstadoTransaccion getEstadoTransaccion() {
		return this.estadoTransaccion;
	}

	public void setEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
		this.estadoTransaccion = estadoTransaccion;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

}
