package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EstadoFacturaDtos.*;
import co.edu.unbosque.entity.EstadoFactura;
import co.edu.unbosque.repository.EstadoFacturaRepository;
import co.edu.unbosque.service.api.EstadoFacturaServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class EstadoFacturaServiceImpl extends GenericServiceImpl<EstadoFactura, Short> implements EstadoFacturaServiceAPI {

	@Autowired
    private EstadoFacturaRepository repo;

	@Override
	public EstadoFacturaDTO crear(CrearEstadoFacturaRequest req) {
		String nombre = req.nombre().trim();
        if (repo.existsByNombre(nombre))
            throw new IllegalArgumentException("Ya existe estado_factura con nombre=" + nombre);

        EstadoFactura e = new EstadoFactura();
        e.setNombre(nombre);
        e.setEstado(true);
        return toDTO(repo.save(e));
	}

	@Override
	@Transactional(readOnly = true)
	public EstadoFacturaDTO obtener(Short id) {
		return toDTO(getRequired(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoFacturaDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public EstadoFacturaDTO actualizar(Short id, ActualizarEstadoFacturaRequest req) {
		EstadoFactura e = getRequired(id);
        if (req.nombre() != null) {
            String nuevo = req.nombre().trim();
            if (!nuevo.equalsIgnoreCase(e.getNombre()) && repo.existsByNombre(nuevo))
                throw new IllegalArgumentException("Ya existe estado_factura con nombre=" + nuevo);
            e.setNombre(nuevo);
        }
        return toDTO(repo.save(e));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public EstadoFacturaDTO cambiarEstado(Short id) {
		EstadoFactura e = getRequired(id);
        e.setEstado(!e.getEstado());
        return toDTO(repo.save(e));
	}

	@Override
	public JpaRepository<EstadoFactura, Short> getDao() {
		return repo;
	}
	
	private EstadoFactura getRequired(Short id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe EstadoFactura id=" + id));
	}

	private EstadoFacturaDTO toDTO(EstadoFactura e) {
		return new EstadoFacturaDTO(e.getIdEstadoFactura(), e.getNombre(), e.getEstado());
	}

   
}
