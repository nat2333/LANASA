package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Detalle_Orden_Compra database table.
 * 
 */
@Entity
@Table(name="detalle_orden_compra")
public class DetalleOrdenCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_detalle_orden_compra", nullable=false)
	private int idDetalleOrdenCompra;

	@Column(name="cantidad", nullable=false)
	private int cantidad;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="precio_unitario", nullable=false, precision=12, scale=2)
	private BigDecimal precioUnitario;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_orden_compra")
	private OrdenCompra ordenCompra;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name="id_producto")
	private Producto producto;

	public DetalleOrdenCompra() {
	}

	public int getIdDetalleOrdenCompra() {
		return this.idDetalleOrdenCompra;
	}

	public void setIdDetalleOrdenCompra(int idDetalleOrdenCompra) {
		this.idDetalleOrdenCompra = idDetalleOrdenCompra;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public OrdenCompra getOrdenCompra() {
		return this.ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
