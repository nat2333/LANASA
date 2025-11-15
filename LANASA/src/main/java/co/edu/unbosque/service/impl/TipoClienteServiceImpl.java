package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.TipoClienteDTOs.*;
import co.edu.unbosque.entity.TipoCliente;
import co.edu.unbosque.repository.TipoClienteRepository;
import co.edu.unbosque.service.api.TipoClienteServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class TipoClienteServiceImpl extends GenericServiceImpl<TipoCliente, Short> implements TipoClienteServiceAPI {

	@Autowired
	private TipoClienteRepository repo;
	
	@Override
	public TipoClienteDTO crear(CrearTipoClienteRequest req) {
		String nombre = req.tipo().trim();
        if (repo.existsByTipo(nombre)) {
            throw new IllegalArgumentException("Ya existe un TipoCliente con tipo=" + nombre);
        }
        TipoCliente t = new TipoCliente();
        t.setTipo(nombre);
        t.setEstado(true);
        return toDTO(repo.save(t));
	}

	@Override
	@Transactional(readOnly = true)
	public TipoClienteDTO obtener(Short id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoClienteDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());

	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public TipoClienteDTO cambiarEstado(Short id) {
		TipoCliente t = obtenerEntidad(id);
        t.setEstado(!t.isEstado());
        return toDTO(repo.save(t));
	}

	@Override
	public JpaRepository<TipoCliente, Short> getDao() {
		return repo;
	}

	private TipoClienteDTO toDTO(TipoCliente t) {
		return new TipoClienteDTO(t.getIdTipoCliente(), t.getTipo(), t.isEstado());
	}

	private TipoCliente obtenerEntidad(Short id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe TipoCliente id=" + id));
	}

}
