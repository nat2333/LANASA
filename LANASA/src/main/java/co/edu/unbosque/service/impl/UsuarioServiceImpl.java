package co.edu.unbosque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.UsuarioDTOs.*;
import co.edu.unbosque.entity.TipoUsuario;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.TipoUsuarioRepository;
import co.edu.unbosque.repository.UsuarioRepository;
import co.edu.unbosque.service.api.UsuarioServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.HashGenerator;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Integer> implements UsuarioServiceAPI{

	@Autowired
	private UsuarioRepository rep;
	@Autowired
	private TipoUsuarioRepository tuRep;

	@Override
	public UsuarioDTO crear(CrearUsuarioRequest req) {
		if(rep.existsByLogin(req.login())) {
			throw new IllegalArgumentException("Ya existe un Usuario con login=" + req.login());
		}
		TipoUsuario tipo = tuRep.findById(req.tipoUsuarioID())
		           .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario no existe: " + req.tipoUsuarioID()));
		
		Usuario u = new Usuario();
		u.setClave(HashGenerator.generarHash(req.clave()));
		u.setLogin(req.login());
		u.setTipoUsuario(tipo);
		u.setEstado(true);
		
		return toDTO(rep.save(u));
	}

	@Override
	@Transactional(readOnly=true)
	public UsuarioDTO obtener(Integer id) {
		return toDTO(get(id));
	}

	@Override
	@Transactional(readOnly=true)
	public List<UsuarioDTO> listar() {
		List<UsuarioDTO> lista = new ArrayList<>();
		for(Usuario u: getAll()){
			lista.add(toDTO(u));
		};
		return lista;
	}

	@Override
	@Transactional(readOnly=true)
	public UsuarioDTO actualizar(Integer id, ActualizarUsuarioRequest req) {
		Usuario u = get(id);
		if(!u.getLogin().equals(req.login()) && rep.existsByLogin(req.login())) {
			throw new IllegalArgumentException("Ya existe un Usuario con login=" + req.login());
		}
		TipoUsuario tipo = tuRep.findById(req.tipoUsuarioID())
		           .orElseThrow(() -> new ResourceNotFoundException("TipoUsuario no existe: " + req.tipoUsuarioID()));
		
		u.setClave(HashGenerator.generarHash(req.clave()));
		u.setLogin(req.login());
		u.setTipoUsuario(tipo);
		
		return toDTO(rep.save(u));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
		
	}

	@Override
	@Transactional(readOnly=true)
	public UsuarioDTO cambiarEstado(Integer id) {
		Usuario u = get(id);
		u.setEstado(!u.getEstado());
		return toDTO(u);
	}

	@Override
	public JpaRepository<Usuario, Integer> getDao() {
		return rep;
	}
	
	private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getIdUsuario(), u.getLogin(), u.getEstado(), u.getTipoUsuario().getTipo());
    }
	
}
