package co.edu.unbosque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.edu.unbosque.dto.TipoUsuarioDTOs.*;
import co.edu.unbosque.entity.TipoUsuario;
import co.edu.unbosque.repository.TipoUsuarioRepository;
import co.edu.unbosque.service.api.TipoUsuarioServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
public class TipoUsuarioServiceImpl extends GenericServiceImpl<TipoUsuario, Short> implements TipoUsuarioServiceAPI{

	@Autowired
	private TipoUsuarioRepository rep;
	
	@Override
	public TipoUsuarioDTO crear(CrearTipoUsuarioRequest req) {
		if (rep.existsByTipo(req.tipo())) {
            throw new IllegalArgumentException("Ya existe un TipoUsuario con tipo=" + req.tipo());
        }
        TipoUsuario t = new TipoUsuario();
        t.setTipo(req.tipo());
        t.setEstado(true);
        return toDTO(rep.save(t));
	}

	@Override
	public TipoUsuarioDTO obtener(Short id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	public List<TipoUsuarioDTO> listar() {
		List<TipoUsuarioDTO> lista = new ArrayList<>();
		for(TipoUsuario t: getAll()){
			lista.add(toDTO(t));
		};
		return lista;
	}

	@Override
	public TipoUsuarioDTO actualizar(Short id, ActualizarTipoUsuarioRequest req) {
		TipoUsuario t = obtenerEntidad(id);
		if (!t.getTipo().equals(req.tipo()) && rep.existsByTipo(req.tipo())) {
            throw new IllegalArgumentException("Ya existe un TipoUsuario con tipo=" + req.tipo());
        }
		t.setTipo(req.tipo());
		return toDTO(rep.save(t));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
	}

	@Override
	public JpaRepository<TipoUsuario, Short> getDao() {
		return rep;
	}
	
	@Override
	public TipoUsuarioDTO cambiarEstado(Short id) {
		TipoUsuario t = obtenerEntidad(id);
		t.setEstado(!t.isEstado());
		return toDTO(rep.save(t));
	}
	
	private TipoUsuarioDTO toDTO(TipoUsuario e) {
        return new TipoUsuarioDTO(e.getIdTipoUsuario(), e.getTipo(), e.isEstado());
    }
	
	private TipoUsuario obtenerEntidad(Short id) {
		return rep.findById(id).orElseThrow(()-> new ResourceNotFoundException("No existe un Tipo Usario con id=" + id));
	}

}
