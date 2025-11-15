package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Estado_Compra database table.
 * 
 */
@Entity
@Table(name="estado_compra")
public class EstadoCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_estado_compra", nullable=false)
	private short idEstadoCompra;

	@Column(name="estado", nullable = false)
	private boolean estado;

    @Column(name = "estado_compra", nullable = false, unique = true, length = 50)
	private String estadoCompra;

	public EstadoCompra() {
	}

	public short getIdEstadoCompra() {
		return this.idEstadoCompra;
	}

	public void setIdEstadoCompra(short idEstadoCompra) {
		this.idEstadoCompra = idEstadoCompra;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getEstadoCompra() {
		return this.estadoCompra;
	}

	public void setEstadoCompra(String estadoCompra) {
		this.estadoCompra = estadoCompra;
	}
}