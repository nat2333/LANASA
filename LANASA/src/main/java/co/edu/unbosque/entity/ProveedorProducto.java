package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Proveedor_Producto database table.
 * 
 */
@Entity
@Table(name="proveedor_producto", uniqueConstraints = @UniqueConstraint(
        name = "uk_producto_proveedor",
        columnNames = {"id_producto", "id_proveedor"}
    ))
public class ProveedorProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_proveedor_producto", nullable=false)
	private int idProveedorProducto;

	@Column(name="calificacion", precision=3, scale=2)
	private BigDecimal calificacion;

	@Column(name = "estado", nullable = false)
	private boolean estado;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
	private Proveedor proveedor;

	public int getIdProveedorProducto() {
		return this.idProveedorProducto;
	}

	public void setIdProveedorProducto(int idProveedorProducto) {
		this.idProveedorProducto = idProveedorProducto;
	}

	public BigDecimal getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}
