package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.TipoProyectoDtos.*;
import co.edu.unbosque.entity.TipoProyecto;
import co.edu.unbosque.repository.TipoProyectoRepository;
import co.edu.unbosque.service.api.TipoProyectoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class TipoProyectoServiceImpl extends GenericServiceImpl<TipoProyecto, Short> implements TipoProyectoServiceAPI {

	@Autowired
	private TipoProyectoRepository repo;
	
	@Override
	public TipoProyectoDTO crear(CrearTipoProyectoRequest req) {
        String nombre = req.tipoProyecto().trim();
        if (repo.existsByTipoProyectoIgnoreCase(nombre))
            throw new IllegalArgumentException("Ya existe un TipoProyecto con nombre=" + nombre);

        var t = new TipoProyecto();
        t.setTipoProyecto(nombre);
        t.setEstado(true);
        return toDTO(repo.save(t));
    }

	@Override
	@Transactional(readOnly = true)
	public TipoProyectoDTO obtener(Short id) {
		return toDTO(mustGet(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoProyectoDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
		
	}

	@Override
	public TipoProyectoDTO cambiarEstado(Short id) {
		TipoProyecto t = mustGet(id);
        t.setEstado(!Boolean.TRUE.equals(t.getEstado()));
        return toDTO(repo.save(t));
	}

	@Override
	public JpaRepository<TipoProyecto, Short> getDao() {
		return repo;
	}

	private TipoProyecto mustGet(Short id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe TipoProyecto id=" + id));
	}

	private TipoProyectoDTO toDTO(TipoProyecto t) {
		return new TipoProyectoDTO(t.getIdTipoProyecto(), t.getTipoProyecto(), t.getEstado());
	}
	
}
