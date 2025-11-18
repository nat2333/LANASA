package co.edu.unbosque.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EmpleadoDTOs.*;
import co.edu.unbosque.entity.*;
import co.edu.unbosque.repository.*;
import co.edu.unbosque.service.api.EmpleadoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional

public class EmpleadoServiceImpl extends GenericServiceImpl<Empleado, Integer> implements EmpleadoServiceAPI  {

	@Autowired
	private EmpleadoRepository repo;
	@Autowired
	private CargoRepository cargoRepo;
	@Autowired
	private TipoContratoRepository tipoContratoRepo;
	@Autowired
	private DepartamentoRepository departamentoRepo;

	@Override
	public EmpleadoDTO crear(CrearEmpleadoRequest req) {
		String cedula = req.cedula().trim();
		if (repo.existsByCedula(cedula))
			throw new IllegalArgumentException("Ya existe un Empleado con cédula=" + cedula);

		if (repo.existsByCorreo(req.correo())) throw new IllegalArgumentException("Correo ya registrado");

		var e = new Empleado();
		e.setCedula(cedula);
		e.setPrimerNombre(req.primerNombre());
		e.setSegundoNombre(req.segundoNombre());
		e.setPrimerApellido(req.primerApellido());
		e.setSegundoApellido(req.segundoApellido());
		e.setCorreo(req.correo());
		e.setFechaNacimiento(req.fechaNacimiento());
		e.setDireccion(req.direccion());
		e.setCiudad(req.ciudad());
		e.setPais(req.pais());
		e.setFechaIngreso(LocalDate.now());
		e.setSalario(req.salario());
		e.setEstado(true);

		e.setCargo(getCargo(req.idCargo()));
		e.setTipoContrato(getTipoContrato(req.idTipoContrato()));
		e.setDepartamento(getDepartamento(req.idDepartamento()));

		return toDTO(repo.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	public EmpleadoDTO obtener(Integer id) {
		Empleado e = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empleado id=" + id));
		return toDTO(e);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoDTO> listar() {
		return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public EmpleadoDTO actualizar(Integer id, ActualizarEmpleadoRequest req) {
		Empleado e = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empleado id=" + id));

		e.setPrimerNombre(req.primerNombre());
		e.setSegundoNombre(req.segundoNombre());
		e.setPrimerApellido(req.primerApellido());
		e.setSegundoApellido(req.segundoApellido());
		e.setDireccion(req.direccion());
		e.setCiudad(req.ciudad());
		e.setPais(req.pais());
		e.setSalario(req.salario());
		e.setCargo(getCargo(req.idCargo()));
		e.setTipoContrato(getTipoContrato(req.idTipoContrato()));
		e.setDepartamento(getDepartamento(req.idDepartamento()));

		return toDTO(repo.save(e));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public EmpleadoDTO cambiarEstado(Integer id) {
		Empleado e = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empleado id=" + id));
		e.setEstado(!Boolean.TRUE.equals(e.getEstado()));
		return toDTO(repo.save(e));
	}

	@Override
	public JpaRepository<Empleado, Integer> getDao() {
		return repo;
	}

	private EmpleadoDTO toDTO(Empleado e) {
		return new EmpleadoDTO(
				e.getIdEmpleado(),
				e.getCedula(),
				e.getPrimerNombre(),
				e.getSegundoNombre(),
				e.getPrimerApellido(),
				e.getSegundoApellido(),
				e.getCorreo(),
				e.getFechaNacimiento(),
				e.getDireccion(),
				e.getCiudad(),
				e.getPais(),
				e.getFechaIngreso(),
				e.getSalario(),
				e.getEstado(),
				e.getCargo().getIdCargo(),
				e.getTipoContrato().getIdTipoContrato(),
				e.getDepartamento().getIdDepartamento(),
				e.getCargo().getNombreCargo(),
			    e.getTipoContrato().getNombreTipocontrato(),
				e.getDepartamento().getNombre()
				);
	}

	private Cargo getCargo(Short id) {
		return cargoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Cargo id=" + id));
	}

	private TipoContrato getTipoContrato(Short id) {
		return tipoContratoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe TipoContrato id=" + id));
	}

	private Departamento getDepartamento(Integer id) {
		return departamentoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Departamento id=" + id));
	}

	@Override
	public Empleado findByCorreo(String correo) {
		return repo.findByCorreo(correo).orElseThrow(() -> new ResourceNotFoundException("No existe Empleado correo=" + correo));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoDTO> listarPorDepartamento(Integer idDepartamento) {
	    return repo.findByDepartamento_IdDepartamento(idDepartamento)
	               .stream()
	               .map(this::toDTO)
	               .collect(Collectors.toList());
	}


}
