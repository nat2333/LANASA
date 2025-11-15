package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Detalle_Factura_Venta database table.
 * 
 */
@Entity
@Table(name="detalle_factura_venta")
public class DetalleFacturaVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_detalle_factura_venta")
	private int idDetalleFacturaVenta;

	@Column(name="cantidad", nullable=false)
	private int cantidad;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="id_producto")
	private Producto producto;

	@Column(name="precio_unitario", nullable=false, precision=14, scale=2)
	private BigDecimal precioUnitario;

	@Column(name="tipo", length=40)
	private String tipo;
	
	@Column(name="estado", nullable=false)
	private boolean estado;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="id_factura_venta")
	private FacturaVenta facturaVenta;

	public int getIdDetalleFacturaVenta() {
		return this.idDetalleFacturaVenta;
	}

	public void setIdDetalleFacturaVenta(int idDetalleFacturaVenta) {
		this.idDetalleFacturaVenta = idDetalleFacturaVenta;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getIdProducto() {
		return this.producto;
	}

	public void setIdProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public FacturaVenta getFacturaVenta() {
		return this.facturaVenta;
	}

	public void setFacturaVenta(FacturaVenta facturaVenta) {
		this.facturaVenta = facturaVenta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
