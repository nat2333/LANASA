package co.edu.unbosque.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.RolEmpleadoDtos.*;
import co.edu.unbosque.entity.RolEmpleado;
import co.edu.unbosque.repository.RolEmpleadoRepository;
import co.edu.unbosque.service.api.RolEmpleadoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/RolEmpleado")
@CrossOrigin(origins = "http://localhost:4200")
public class RolEmpleadoServiceImpl extends GenericServiceImpl<RolEmpleado, Short> implements RolEmpleadoServiceAPI{
	
	private RolEmpleadoRepository repo;

	@Override
	public RolEmpleadoDTO crear(CrearRolEmpleadoRequest req) {
	    String rolEmpleado = req.rolEmpleado();
	    if(repo.existsByRolEmpleadoIgnoreCase(rolEmpleado)) throw new IllegalArgumentException("El Rol ya existe: " + rolEmpleado);
	    RolEmpleado r = new RolEmpleado();
	    r.setRolEmpleado(rolEmpleado);
	    r.setEstado(true);
	   
		return toDTO(repo.save(r));
	}

	@Override 
	@Transactional(readOnly = true)
	public RolEmpleadoDTO obtener(Short id) {
		return toDTO(getRequired(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolEmpleadoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public RolEmpleadoDTO actualizar(Short id, ActualizarRolEmpleadoRequest req) {
		RolEmpleado r = getRequired(id);
		String rolEmpleado = req.rolEmpleado();
	    if(repo.existsByRolEmpleadoIgnoreCase(rolEmpleado)) throw new IllegalArgumentException("El Rol ya existe: " + rolEmpleado);
	    
	    r.setRolEmpleado(rolEmpleado);
	   
		return toDTO(repo.save(r));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public RolEmpleadoDTO cambiarEstado(Short id) {
		RolEmpleado r = getRequired(id);
		r.setEstado(!r.getEstado());
		return toDTO(repo.save(r));
	}

	@Override
	public JpaRepository<RolEmpleado, Short> getDao() {
		return repo;
	}
	
	private RolEmpleadoDTO toDTO(RolEmpleado r) {
		return new RolEmpleadoDTO(
				r.getIdRolEmpleado(), r.getRolEmpleado(), r.getTarifaHora(), r.getEstado());
	}
	
	private RolEmpleado getRequired(Short id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe RolEmpleado id=" + id));
    }
}
