package co.edu.unbosque.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.HistorialEmpleadoDTOs.*;
import co.edu.unbosque.entity.Cargo;
import co.edu.unbosque.entity.Departamento;
import co.edu.unbosque.entity.Empleado;
import co.edu.unbosque.entity.Historial;
import co.edu.unbosque.repository.CargoRepository;
import co.edu.unbosque.repository.DepartamentoRepository;
import co.edu.unbosque.repository.EmpleadoRepository;
import co.edu.unbosque.repository.HistorialRepository;
import co.edu.unbosque.service.api.HistorialServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class HistorialServiceImpl extends GenericServiceImpl<Historial, Integer> implements HistorialServiceAPI  {

	@Autowired
	private  HistorialRepository repo;
	@Autowired
	private  EmpleadoRepository empleadoRepo;
	@Autowired
	private  DepartamentoRepository departamentoRepo;
	@Autowired
	private  CargoRepository cargoRepo;
	
	@Override
	public HistorialDTO crear(CrearHistorialRequest req) {
		validarFechas(req.fechaInicio(), req.fechaFin());

        var h = new Historial();
        h.setEmpleado(obtenerEmpleado(req.idEmpleado()));
        h.setDepartamento(obtenerDepartamento(req.idDepartamento()));
        h.setCargo(obtenerCargo(req.idCargo()));
        h.setFechaInicio(req.fechaInicio());
        h.setFechaFin(req.fechaFin());
        h.setEstado(true);

        return toDTO(repo.save(h));
	}

	@Override
	@Transactional(readOnly = true)
	public HistorialDTO obtener(Integer id) {
		Historial h = obtenerEntidad(id);
	    return toDTO(h);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HistorialDTO> listar() {
		return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public HistorialDTO actualizar(Integer id, ActualizarHistorialRequest req) {
		Historial h = obtenerEntidad(id);

		LocalDate nuevoFin = req.fechaFin();
		validarFechas(h.getFechaInicio(), nuevoFin);
		h.setFechaFin(req.fechaFin());
		return toDTO(repo.save(h));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public HistorialDTO cambiarEstado(Integer id, LocalDate fechaFin) {
		Historial h = get(id);
		h.setEstado(!h.getEstado());
		return toDTO(repo.save(h));
	}

	@Override
	@Transactional(readOnly = true)
	public List<HistorialDTO> listarPorEmpleado(Integer idEmpleado) {
		List<Historial> historiales = repo.findByEmpleado_IdEmpleadoOrderByFechaInicioDesc(idEmpleado);
		List<HistorialDTO> historialesDTO = new ArrayList<>();
		for(Historial h : historiales) {
			historialesDTO.add(toDTO(h));
		}
		return historialesDTO;
	}

	@Override
	public JpaRepository<Historial, Integer> getDao() {
		return repo;
	}
	
	private HistorialDTO toDTO(Historial h) {
        return new HistorialDTO(
            h.getIdHistorial(),
            h.getEstado(),
            h.getFechaInicio(),
            h.getFechaFin(),
            h.getEmpleado().getCedula(),
            h.getDepartamento().getNombre(),
            h.getCargo().getNombreCargo() 
        );
    }
	
	private Historial obtenerEntidad(Integer id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Historial id=" + id));
	}
	
	private Empleado obtenerEmpleado(Integer id) {
		return empleadoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empleado id=" + id));
	}

	private Departamento obtenerDepartamento(Integer id) {
		return departamentoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Departamento id=" + id));
	}
	
	private Cargo obtenerCargo(Short id) {
		return cargoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Cargo id=" + id));
	}

	private void validarFechas(LocalDate inicio, LocalDate fin) {
        if (fin != null && fin.isBefore(inicio)) {
            throw new IllegalArgumentException("fechaFin no puede ser anterior a fechaInicio");
        }
    }

}
