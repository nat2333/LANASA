package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.MetodoPagoDTOs.*;
import co.edu.unbosque.entity.MetodoPago;
import co.edu.unbosque.repository.MetodoPagoRepository;
import co.edu.unbosque.service.api.MetodoPagoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class MetodoPagoServiceImpl extends GenericServiceImpl<MetodoPago, Short> implements MetodoPagoServiceAPI {

	@Autowired
    private MetodoPagoRepository repo;

	@Override
	public MetodoPagoDTO crear(CrearMetodoPagoRequest req) {
		String nombre = req.metodoPago().trim();
        if (repo.existsByMetodoPago(nombre))
            throw new IllegalArgumentException("Ya existe metodo_pago=" + nombre);

        MetodoPago m = new MetodoPago();
        m.setMetodoPago(nombre);
        m.setEstado(true);

        return toDTO(repo.save(m));
	}

	@Override
	@Transactional(readOnly = true)
	public MetodoPagoDTO obtener(Short id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<MetodoPagoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public MetodoPagoDTO actualizar(Short id, ActualizarMetodoPagoRequest req) {
		MetodoPago m = obtenerEntidad(id);
        if (req.metodoPago() != null) {
            String nuevo = req.metodoPago().trim();
            if (!nuevo.equalsIgnoreCase(m.getMetodoPago()) && repo.existsByMetodoPago(nuevo))
                throw new IllegalArgumentException("Ya existe metodo_pago=" + nuevo);
            m.setMetodoPago(nuevo);
        }
        return toDTO(repo.save(m));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public MetodoPagoDTO cambiarEstado(Short id) {
		MetodoPago m = obtenerEntidad(id);
        m.setEstado(!m.getEstado());
        return toDTO(repo.save(m));
	}
	
	private MetodoPagoDTO toDTO(MetodoPago m) {
		return new MetodoPagoDTO(m.getIdMetodoPago(), m.getMetodoPago(), m.getEstado());
	}

	private MetodoPago obtenerEntidad(Short id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe MetodoPago id=" + id));
	}

	@Override
	public JpaRepository<MetodoPago, Short> getDao() {
		return repo;
	}
	
	
}
