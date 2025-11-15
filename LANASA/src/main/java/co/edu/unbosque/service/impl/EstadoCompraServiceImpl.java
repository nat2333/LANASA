package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EstadoCompraDTOs.*;
import co.edu.unbosque.entity.EstadoCompra;
import co.edu.unbosque.repository.EstadoCompraRepository;
import co.edu.unbosque.service.api.EstadoCompraServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class EstadoCompraServiceImpl extends GenericServiceImpl<EstadoCompra, Short> implements EstadoCompraServiceAPI{

	@Autowired
	private EstadoCompraRepository repo;

	@Override
	public EstadoCompraDTO crear(CrearEstadoCompraRequest req) {
		String nombre = req.estadoCompra().trim();
        if (repo.existsByEstadoCompra(nombre))
            throw new IllegalArgumentException("Ya existe estado_compra=" + nombre);

        EstadoCompra e = new EstadoCompra();
        e.setEstadoCompra(nombre);
        e.setEstado(true);
        return toDTO(repo.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	public EstadoCompraDTO obtener(Short id) {
		return toDTO(getRequired(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoCompraDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public EstadoCompraDTO actualizar(Short id, ActualizarEstadoCompraRequest req) {
		EstadoCompra e = getRequired(id);
        if (req.estadoCompra() != null) {
            String nuevo = req.estadoCompra().trim();
            if (!nuevo.equalsIgnoreCase(e.getEstadoCompra()) && repo.existsByEstadoCompra(nuevo))
                throw new IllegalArgumentException("Ya existe estado_compra=" + nuevo);
            e.setEstadoCompra(nuevo);
        }
        return toDTO(repo.save(e));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public EstadoCompraDTO cambiarEstado(Short id) {
		EstadoCompra e = getRequired(id);
        e.setEstado(!e.getEstado());
        return toDTO(repo.save(e));
	}

	@Override
	public JpaRepository<EstadoCompra, Short> getDao() {
		return repo;
	}
	
	private EstadoCompraDTO toDTO(EstadoCompra e) {
        return new EstadoCompraDTO(e.getIdEstadoCompra(), e.getEstadoCompra(), e.getEstado());
    }

    private EstadoCompra getRequired(Short id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No existe EstadoCompra id=" + id));
    }
	
}
