package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Tipo_Usuario database table.
 * 
 */
@Entity
@Table(name="tipo_usuario")
public class TipoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_usuario", nullable=false)
	private Short idTipoUsuario;

	@Column(name="tipo", nullable = false, unique = true, length = 50)
	private String tipo;

	@Column(name="estado", nullable = false)
	private boolean estado;
	
	public TipoUsuario() {
	}

	public Short getIdTipoUsuario() {
		return this.idTipoUsuario;
	}

	public void setIdTipoUsuario(Short idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}