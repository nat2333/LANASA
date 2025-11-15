package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the Proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_proveedor", nullable=false)
	private int idProveedor;

	@Column(name="calificacion", precision=3, scale=2)
	private BigDecimal calificacion;

    @Column(name = "categoria", length = 80)
	private String categoria;

    @Column(name = "ciudad", length = 80)
	private String ciudad;

    @Column(name = "correo", length = 150)
	private String correo;

    @Column(name = "direccion", length = 150)
	private String direccion;

    @Column(name = "estado", nullable = false)
	private boolean estado;

    @Column(name = "nombre_comercial", nullable = false, length = 150)
	private String nombreComercial;

    @Column(name = "pais", length = 80)
	private String pais;

    @Column(name = "rut", nullable = false, unique = true, length = 40)
	private String rut;

    @Column(name = "telefono", length = 40)
	private String telefono;
	
	public Proveedor() {
	}

	public int getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public BigDecimal getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombreComercial() {
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}