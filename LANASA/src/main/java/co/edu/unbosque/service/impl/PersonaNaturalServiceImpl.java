package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.PersonaNaturalDTOs.*;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.PersonaNatural;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.PersonaNaturalRepository;
import co.edu.unbosque.service.api.PersonaNaturalServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class PersonaNaturalServiceImpl extends GenericServiceImpl<PersonaNatural, Integer> implements PersonaNaturalServiceAPI {

	@Autowired
	private PersonaNaturalRepository repo;
	@Autowired
    private ClienteRepository clienteRepo;
	
	@Override
    public PersonaNaturalDTO crear(CrearPersonaNaturalRequest req) {
        if (repo.existsByCliente_IdCliente(req.idCliente())) {
            throw new IllegalStateException("El Cliente id=" + req.idCliente() + " ya tiene PersonaNatural");
        }

        Cliente cliente = obtenerCliente(req.idCliente());

        String ced = req.cedula() != null ? req.cedula().trim() : "";
        if (!ced.isEmpty() && repo.existsByCedula(ced)) {
            throw new IllegalArgumentException("La cédula ya existe: " + ced);
        }

        PersonaNatural pn = new PersonaNatural();
        pn.setCliente(cliente); 
        pn.setCedula(ced.isEmpty() ? null : ced);
        pn.setPrimerNombre(req.primerNombre());
        pn.setSegundoNombre(req.segundoNombre());
        pn.setPrimerApellido(req.primerApellido());
        pn.setSegundoApellido(req.segundoApellido());
        pn.setEstado(true);

        return toDTO(repo.save(pn));
    }
	
	@Override
	@Transactional(readOnly = true)
	public PersonaNaturalDTO obtener(Integer idCliente) {
		PersonaNatural pn = obtenerPersona(idCliente);
	        return toDTO(pn);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PersonaNaturalDTO> listar() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public PersonaNaturalDTO actualizar(Integer idCliente, ActualizarPersonaNaturalRequest req) {
		PersonaNatural pn = obtenerPersona(idCliente);

		pn.setPrimerNombre(req.primerNombre());
		pn.setSegundoNombre(req.segundoNombre());
		pn.setPrimerApellido(req.primerApellido());
		pn.setSegundoApellido(req.segundoApellido());

		return toDTO(repo.save(pn));
	}
	
	@Override
	public void eliminar(Integer idCliente) {
		delete(idCliente);
	}
	
	@Override
	public PersonaNaturalDTO cambiarEstado(Integer idCliente) {
		PersonaNatural pn = repo.findById(idCliente)
				.orElseThrow(() -> new ResourceNotFoundException("No existe PersonaNatural para idCliente=" + idCliente));
		pn.setEstado(!pn.getEstado());
		return toDTO(repo.save(pn));
	}
	
	@Override
	public JpaRepository<PersonaNatural, Integer> getDao() {
		return repo;
	}
	
	private PersonaNaturalDTO toDTO(PersonaNatural p) {
        return new PersonaNaturalDTO(
            p.getIdCliente(),
            p.getCedula(),
            p.getPrimerNombre(),
            p.getSegundoNombre(),
            p.getPrimerApellido(),
            p.getSegundoApellido(),
            p.getEstado()
        );
    }
	
	private Cliente obtenerCliente(Integer idCliente) {
        return clienteRepo.findById(idCliente)
            .orElseThrow(() -> new ResourceNotFoundException("No existe Cliente id=" + idCliente));
    }
	
	public PersonaNatural obtenerPersona(Integer idCliente) {
		return repo.findById(idCliente)
        .orElseThrow(() -> new ResourceNotFoundException("No existe PersonaNatural para idCliente=" + idCliente));
	}
	
	public PersonaNaturalDTO obtenerPersonaDTO(Integer idCliente) {
		return toDTO(obtenerPersona(idCliente));
	}
	
}
