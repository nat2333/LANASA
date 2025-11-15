package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The persistent class for the Tipo_cliente database table.
 * 
 */
@Entity
@Table(name="tipo_cliente")
public class TipoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_cliente", nullable=false)
	private Short idTipoCliente;

	@Column(name="tipo", unique = true, length = 40)
	private String tipo;
	
	@Column(name="estado", nullable = false)
	private Boolean estado;

	public TipoCliente() {
	}

	public Short getIdTipoCliente() {
		return this.idTipoCliente;
	}

	public void setIdTipoCliente(Short idTipoCliente) {
		this.idTipoCliente = idTipoCliente;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean isEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}