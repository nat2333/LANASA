package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.ClienteDTOs.*;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.TipoCliente;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.TipoClienteRepository;
import co.edu.unbosque.service.api.ClienteServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class ClienteServiceImpl extends GenericServiceImpl<Cliente, Integer> implements ClienteServiceAPI{

	@Autowired
	private ClienteRepository repo;
	@Autowired
    private TipoClienteRepository tipoRepo;
	
	@Override
	public ClienteDTO crear(CrearClienteRequest req) {
		String tel = req.telefono().trim();
        String mail = req.correo().trim().toLowerCase();

        if (repo.existsByTelefono(tel)) throw new IllegalArgumentException("Teléfono ya registrado");
        if (repo.existsByCorreo(mail))  throw new IllegalArgumentException("Correo ya registrado");

        Cliente c = new Cliente();
        c.setTipoCliente(getTipo(req.idTipoCliente()));
        c.setDireccion(req.direccion());
        c.setPais(req.pais());
        c.setCiudad(req.ciudad());
        c.setTelefono(tel);
        c.setCorreo(mail);
        c.setEstado(true);

        return toDTO(repo.save(c));
	}
	
	@Override
	@Transactional(readOnly = true)
	public ClienteDTO obtener(Integer id) {
		Cliente c = obtenerEntidad(id);
	        return toDTO(c);
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<ClienteDTO> listar() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public ClienteDTO actualizar(Integer id, ActualizarClienteRequest req) {
		Cliente c = obtenerEntidad(id);

		c.setDireccion(req.direccion());
		c.setPais(req.pais());
		c.setCiudad(req.ciudad());

		String nuevoTel = req.telefono().trim();
		if (!nuevoTel.equalsIgnoreCase(c.getTelefono()) && repo.existsByTelefono(nuevoTel)) {
			throw new IllegalArgumentException("Teléfono ya registrado");
		}
		c.setTelefono(nuevoTel);
		
		String nuevoMail = req.correo().trim().toLowerCase();
		if (!nuevoMail.equalsIgnoreCase(c.getCorreo()) && repo.existsByCorreo(nuevoMail)) {
			throw new IllegalArgumentException("Correo ya registrado");
		}
		c.setCorreo(nuevoMail);

		return toDTO(repo.save(c));
	}
	
	@Override
	public void eliminar(Integer id) {
		delete(id);
	}
	
	@Override
	public ClienteDTO cambiarEstado(Integer id) {
		Cliente c = obtenerEntidad(id);
		c.setEstado(!c.getEstado());
		return toDTO(repo.save(c));
	}
	
	@Override
	public JpaRepository<Cliente, Integer> getDao() {
		return repo;
	}
	
	private ClienteDTO toDTO(Cliente c) {
        return new ClienteDTO(
            c.getIdCliente(),
            c.getEstado(),
            c.getDireccion(),
            c.getPais(),
            c.getCiudad(),
            c.getTelefono(),
            c.getCorreo(),
            c.getTipoCliente().getTipo()
        );
    }
	
	private TipoCliente getTipo(Short id) {
        return tipoRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe TipoCliente id=" + id));
    }
	
	private Cliente obtenerEntidad(int id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Cliente con id=" + id));
	}
}
