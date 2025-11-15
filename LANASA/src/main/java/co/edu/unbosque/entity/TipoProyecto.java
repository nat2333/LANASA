package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Tipo_Proyecto database table.
 * 
 */
@Entity
@Table(name="Tipo_Proyecto")
public class TipoProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_proyecto", nullable=false)
	private short idTipoProyecto;

	@Column(name="estado", nullable=false)
	private boolean estado;

    @Column(name="tipo_proyecto", nullable=false, unique=true, length=80)
	private String tipoProyecto;

	public TipoProyecto() {
	}

	public short getIdTipoProyecto() {
		return this.idTipoProyecto;
	}

	public void setIdTipoProyecto(short idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getTipoProyecto() {
		return this.tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

}
