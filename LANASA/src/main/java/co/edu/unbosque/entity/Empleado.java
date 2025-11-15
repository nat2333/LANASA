package co.edu.unbosque.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;

import java.time.LocalDate;


/**
 * The persistent class for the Empleado database table.
 * 
 */
@Entity
@Table(name="Empleado")
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empleado", nullable=false)
	private int idEmpleado;

	@Column(name = "cedula", nullable = false, unique = true, length = 20)
	private String cedula;

	@Column(name="ciudad", length = 80)
	private String ciudad;
	
	@Column(name = "correo", nullable = false, length = 100)
    private String correo;

	@Column(name="direccion", length = 150)
	private String direccion;

	@Column(name="estado", nullable=false)
	private boolean estado;

	@Column(name="fecha_ingreso", nullable = false)
	private LocalDate fechaIngreso;

	@Column(name="fecha_nacimiento", nullable = false)
	private LocalDate fechaNacimiento;

	@Column(name="pais")
	private String pais;

	@Column(name = "primer_apellido", nullable = false, length = 60)
	private String primerApellido;

	 @Column(name = "primer_nombre", nullable = false, length = 60)
	private String primerNombre;

	@Column(name="salario", precision=12, scale=2)
	private BigDecimal salario;

	@Column(name = "segundo_apellido", length = 60)
	private String segundoApellido;

	@Column(name = "segundo_nombre", length = 60)
	private String segundoNombre;

	@ManyToOne
	@JoinColumn(name="id_cargo")
	private Cargo cargo;

	@ManyToOne
	@JoinColumn(name="id_tipo_contrato")
	private TipoContrato tipoContrato;

	@ManyToOne
	@JoinColumn(name="id_departamento")
	private Departamento departamento;


	public Empleado() {
	}

	public int getIdEmpleado() {
		return this.idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
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

	public LocalDate getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
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

	public BigDecimal getSalario() {
		return this.salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
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

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public TipoContrato getTipoContrato() {
		return this.tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}