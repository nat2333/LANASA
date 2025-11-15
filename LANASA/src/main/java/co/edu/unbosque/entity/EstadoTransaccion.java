package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Estado_Transaccion database table.
 * 
 */
@Entity
@Table(name="Estado_Transaccion")
public class EstadoTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_estado_transaccion", nullable=false)
	private Short idEstadoTransaccion;

	@Column(name="estado",  nullable=false)
	private boolean estado;

	@Column(name="nombre", nullable=false, unique=true, length=40)
	private String nombre;

	public EstadoTransaccion() {
	}

	public Short getIdEstadoTransaccion() {
		return this.idEstadoTransaccion;
	}

	public void setIdEstadoTransaccion(Short idEstadoTransaccion) {
		this.idEstadoTransaccion = idEstadoTransaccion;
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
