package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cliente", nullable=false)
	private int idCliente;

	@Column(name="ciudad", length = 80)
	private String ciudad;

	@Column(name="correo", nullable = false, unique = true, length = 150)
	private String correo;

	@Column(name="direccion", length = 150)
	private String direccion;

	@Column(name="estado", nullable = false)
	private boolean estado;

	@Column(name="pais", length = 80)
	private String pais;

	@Column(name="telefono", nullable = false, unique = true, length = 40)
	private String telefono;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="id_tipo_cliente")
	private TipoCliente tipoCliente;

	public Cliente() {
	}

	public int getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

}
