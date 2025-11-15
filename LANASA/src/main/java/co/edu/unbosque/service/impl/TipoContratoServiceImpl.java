package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.TipoContratoDTOs.*;
import co.edu.unbosque.entity.TipoContrato;
import co.edu.unbosque.repository.TipoContratoRepository;
import co.edu.unbosque.service.api.TipoContratoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class TipoContratoServiceImpl extends GenericServiceImpl<TipoContrato, Short> implements TipoContratoServiceAPI {

	@Autowired
	private  TipoContratoRepository rep;
	
	@Override
	public TipoContratoDTO crear(CrearTipoContratoRequest req) {
		String nombre = req.nombreTipocontrato().trim();
        if (rep.existsByNombreTipocontrato(nombre)) {
            throw new IllegalArgumentException("Ya existe un TipoContrato con nombre=" + nombre);
        }
        var t = new TipoContrato();
        t.setNombreTipocontrato(nombre);
        t.setEstado(true);
        return toDTO(rep.save(t));
	}

	@Override
	@Transactional(readOnly = true)
	public TipoContratoDTO obtener(Short id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoContratoDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public TipoContratoDTO actualizar(Short id, ActualizarTipoContratoRequest req) {
		TipoContrato t = obtenerEntidad(id);
        if (req.nombreTipocontrato() != null) {
            String nuevo = req.nombreTipocontrato().trim();
            if (!nuevo.equalsIgnoreCase(t.getNombreTipocontrato())
                && rep.existsByNombreTipocontrato(nuevo)) {
                throw new IllegalArgumentException("Ya existe un TipoContrato con nombre=" + nuevo);
            }
            t.setNombreTipocontrato(nuevo);
        }
        return toDTO(rep.save(t));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public TipoContratoDTO cambiarEstado(Short id) {
		var t = get(id);
        t.setEstado(!t.getEstado());
        return toDTO(rep.save(t));
	}

	@Override
	public JpaRepository<TipoContrato, Short> getDao() {
		return rep;
	}
	
	private TipoContratoDTO toDTO(TipoContrato t) {
        return new TipoContratoDTO(t.getIdTipoContrato(), t.getNombreTipocontrato(), t.getEstado());
    }
	
	private TipoContrato obtenerEntidad(Short id) {
		return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe TipoContrato id=" + id));
	}

}
