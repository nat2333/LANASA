package co.edu.unbosque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.ClienteDTOs.*;
import co.edu.unbosque.dto.EmpresaDTOs.*;
import co.edu.unbosque.dto.EmpresaDTOs.EmpresaDTO;
import co.edu.unbosque.dto.PersonaNaturalDTOs.*;
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
	private EmpresaServiceImpl empService;
	@Autowired
	PersonaNaturalServiceImpl pnService;
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
        
        c = repo.save(c);
        EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
		PersonaNaturalDTO p = new PersonaNaturalDTO(0, "","","","","", true);
        int id = c.getIdCliente();
        
        if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
        	e = empService.crear(crearEmpresaRequest(req, id));
        }else {
            p = pnService.crear(crearPersonaRequest(req, id));
        }

        return toDTO(c, e, p);
	}
	
	private CrearEmpresaRequest crearEmpresaRequest(CrearClienteRequest req, int id) {
		CrearEmpresaRequest e = new CrearEmpresaRequest(
				id,
				req.nombreEmpresa(),
				req.rut(),
				req.razonSocial()
				);
		return e;
	}
	
	private CrearPersonaNaturalRequest crearPersonaRequest(CrearClienteRequest req, int id) {
		CrearPersonaNaturalRequest p = new CrearPersonaNaturalRequest(
				id,
				req.cedula(),
				req.primerNombre(),
				req.segundoNombre(),
				req.primerApellido(),
				req.segundoApellido());
        return p;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ClienteDTO obtener(Integer id) {
		Cliente c = obtenerEntidad(id);
		EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
		PersonaNaturalDTO p = new PersonaNaturalDTO(0, "","","","","", true);
		
		if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
			e = empService.obtenerEmpresaDTO(id);
			
		}else {
			p = pnService.obtenerPersonaDTO(id);
		}
	        return toDTO(c, e, p);
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<ClienteDTO> listar() {
		List<ClienteDTO> clientes = new ArrayList<>();
		EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
		PersonaNaturalDTO p = new PersonaNaturalDTO(0, "","","","","", true);
		
		for(Cliente c : repo.findAll()) {
			int id = c.getIdCliente();
			
			if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
				e = empService.obtenerEmpresaDTO(id);
				
			}else {
				p = pnService.obtenerPersonaDTO(id);
			}
			
			clientes.add(toDTO(c, e, p));
		}
        return clientes;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> listarPorTipo(Short idTipoCliente) {
	    List<ClienteDTO> clientes = new ArrayList<>();
	    EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
	    PersonaNaturalDTO p = new PersonaNaturalDTO(0, "", "", "", "", "", true);
	    
	    for (Cliente c : repo.findByTipoCliente_IdTipoCliente(idTipoCliente)) {
	        int id = c.getIdCliente();
	        
	        if (c.getTipoCliente().getTipo().equals("EMPRESA")) {
	            e = empService.obtenerEmpresaDTO(id);
	        } else {
	            p = pnService.obtenerPersonaDTO(id);
	        }
	        
	        clientes.add(toDTO(c, e, p));
	    }
	    
	    return clientes;
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
		
		c = repo.save(c);
		EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
		PersonaNaturalDTO p = new PersonaNaturalDTO(0, "","","","","", true);
		
		if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
			e = empService.actualizar(id, actualizarEmpresaRequest(req));
			
		}else {
			p = pnService.actualizar(id, actualizarPersonaRequest(req));
		}

		return toDTO(c, e, p);
	}
	
	private ActualizarEmpresaRequest actualizarEmpresaRequest(ActualizarClienteRequest req) {
		return new ActualizarEmpresaRequest(
				req.nombre(),
				req.razonSocial());
	}
	
	private ActualizarPersonaNaturalRequest actualizarPersonaRequest(ActualizarClienteRequest req) {
		return new ActualizarPersonaNaturalRequest(
				req.primerNombre(),
				req.segundoNombre(),
				req.primerApellido(),
				req.segundoApellido());
	}
	
	
	@Override
	public void eliminar(Integer id) {
		Cliente c = obtenerEntidad(id);
		
		if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
			empService.delete(id);
			
		}else {
			pnService.delete(id);
		}
		delete(id);
	}
	
	@Override
	public ClienteDTO cambiarEstado(Integer id) {
		Cliente c = obtenerEntidad(id);
		c.setEstado(!c.getEstado());
		
		EmpresaDTO e = new EmpresaDTO(0, "", "", "", true);
		PersonaNaturalDTO p = new PersonaNaturalDTO(0, "","","","","", true);
		
		if(c.getTipoCliente().getTipo().equals("EMPRESA")) {
			e = empService.cambiarEstado(id);
			
		}else {
			p = pnService.cambiarEstado(id);
		}
	      
		return toDTO(c, e, p);
	}
	
	@Override
	public JpaRepository<Cliente, Integer> getDao() {
		return repo;
	}
		
	private ClienteDTO toDTO(Cliente c, EmpresaDTO e, PersonaNaturalDTO p) {
        return new ClienteDTO(
            c.getIdCliente(),
            c.getEstado(),
            c.getDireccion(),
            c.getPais(),
            c.getCiudad(),
            c.getTelefono(),
            c.getCorreo(),
            c.getTipoCliente().getIdTipoCliente(),
            c.getTipoCliente().getTipo(),
            e.nombre(),
            e.rut(),
            e.razonSocial(),
            p.cedula(),
            p.primerNombre(),
            p.segundoNombre(),
            p.primerApellido(),
            p.segundoApellido()
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
