package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the Pago database table.
 * 
 */
@Entity
@Table(name="pago")
public class Pago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pago", nullable=false)
	private int idPago;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="fecha_pago", nullable=false)
	private LocalDateTime fechaPago;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)	@JoinColumn(name="id_metodo_pago")
	private MetodoPago metodoPago;

	@Column(name="monto", nullable=false, precision=12, scale=2)
	private BigDecimal monto;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)	@JoinColumn(name="id_factura_compra")
	private FacturaCompra facturaCompra;

	public Pago() {
	}

	public int getIdPago() {
		return this.idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public MetodoPago getIdMetodoPago() {
		return this.metodoPago;
	}

	public void setIdMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public FacturaCompra getFacturaCompra() {
		return this.facturaCompra;
	}

	public void setFacturaCompra(FacturaCompra facturaCompra) {
		this.facturaCompra = facturaCompra;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

}
