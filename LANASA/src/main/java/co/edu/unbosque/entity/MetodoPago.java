package co.edu.unbosque.entity;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the Metodo_Pago database table.
 * 
 */
@Entity
@Table(name="metodo_pago")
public class MetodoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_metodo_pago", nullable=false)
	private short idMetodoPago;

    @Column(name = "estado", nullable = false)
	private boolean estado;

    @Column(name = "metodo_pago", nullable = false, unique = true, length = 50)
	private String metodoPago;

	public MetodoPago() {
	}

	public short getIdMetodoPago() {
		return this.idMetodoPago;
	}

	public void setIdMetodoPago(short idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getMetodoPago() {
		return this.metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

}