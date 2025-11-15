package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Estado_Factura database table.
 * 
 */
@Entity
@Table(name="estado_factura")
public class EstadoFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_estado_factura", nullable=false)
	private short idEstadoFactura;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="nombre", nullable=false, length=40, unique=true)
	private String nombre;

	public EstadoFactura() {
	}

	public short getIdEstadoFactura() {
		return this.idEstadoFactura;
	}

	public void setIdEstadoFactura(short idEstadoFactura) {
		this.idEstadoFactura = idEstadoFactura;
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
}