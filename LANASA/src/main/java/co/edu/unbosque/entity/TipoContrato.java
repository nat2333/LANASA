package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Tipo_Contrato database table.
 * 
 */
@Entity
@Table(name="tipo_contrato")
public class TipoContrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_contrato", nullable=false)
	private Short idTipoContrato;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="nombre_tipocontrato", nullable=false, unique=true)
	private String nombreTipocontrato;


	public TipoContrato() {
	}

	public Short getIdTipoContrato() {
		return this.idTipoContrato;
	}

	public void setIdTipoContrato(Short idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombreTipocontrato() {
		return this.nombreTipocontrato;
	}

	public void setNombreTipocontrato(String nombreTipocontrato) {
		this.nombreTipocontrato = nombreTipocontrato;
	}

}