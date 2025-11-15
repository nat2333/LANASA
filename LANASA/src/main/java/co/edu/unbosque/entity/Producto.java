package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the Producto database table.
 * 
 */
@Entity
@Table(name="producto")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_producto", nullable=false)
	private int idProducto;

    @Column(name = "categoria", length = 80)
	private String categoria;

	@Lob
    @Column(name = "descripcion", columnDefinition = "TEXT")
	private String descripcion;

    @Column(name = "estado", nullable = false)
	private boolean estado;

    @Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

    @Column(name = "precio_compra", nullable = false, precision = 12, scale = 2)
	private BigDecimal precioCompra;

    @Column(name = "precio_venta_sugerido", precision = 12, scale = 2)
	private BigDecimal precioVentaSugerido;

    @Column(name = "sku", nullable = false, unique = true, length = 60)
	private String sku;

    @Column(name = "stock_actual", columnDefinition = "INT UNSIGNED DEFAULT 0")
	private int stockActual;

    @Column(name = "stock_maximo", columnDefinition = "INT UNSIGNED DEFAULT 0")
	private int stockMaximo;

    @Column(name = "stock_minimo", precision = 12, scale = 2)
	private int stockMinimo;

	public Producto() {
	}

	public int getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecioCompra() {
		return this.precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getPrecioVentaSugerido() {
		return this.precioVentaSugerido;
	}

	public void setPrecioVentaSugerido(BigDecimal precioVentaSugerido) {
		this.precioVentaSugerido = precioVentaSugerido;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getStockActual() {
		return this.stockActual;
	}

	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}

	public int getStockMaximo() {
		return this.stockMaximo;
	}

	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public int getStockMinimo() {
		return this.stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

}
