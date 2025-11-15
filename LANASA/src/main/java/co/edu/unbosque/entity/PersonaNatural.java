package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Persona_Natural database table.
 * 
 */
@Entity
@Table(name="persona_natural")
public class PersonaNatural implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cliente", nullable=false)
	private int idCliente;

	@Column(name="cedula", unique = true, length = 30)
	private String cedula;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="primer_apellido", nullable=false, length = 60)
	private String primerApellido;

	@Column(name="primer_nombre", nullable=false,  length = 60)
	private String primerNombre;

	@Column(name="segundo_apellido", length = 60)
	private String segundoApellido;

	@Column(name="segundo_nombre",  length = 60)
	private String segundoNombre;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId
	@JoinColumn(name="id_cliente", nullable = false)
	private Cliente cliente;

	public PersonaNatural() {
	}

	public int getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
