package co.edu.unbosque.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EmpresaDTOs.*;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Empresa;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.EmpresaRepository;
import co.edu.unbosque.service.api.EmpresaServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class EmpresaServiceImpl extends GenericServiceImpl<Empresa, Integer> implements EmpresaServiceAPI  {

	@Autowired
	private EmpresaRepository repo;
	@Autowired
    private ClienteRepository clienteRepo;
	
	@Override
    public EmpresaDTO crear(CrearEmpresaRequest req) {
        if (repo.existsByCliente_IdCliente(req.idCliente())) {
            throw new IllegalStateException("El Cliente id=" + req.idCliente() + " ya tiene Empresa");
        }

        Cliente cliente = obtenerEntidad(req.idCliente());

        String rut = req.rut() != null ? req.rut().trim() : "";
        if (!rut.isEmpty() && repo.existsByRut(rut)) {
            throw new IllegalArgumentException("El RUT ya existe: " + rut);
        }

        Empresa e = new Empresa();
        e.setCliente(cliente);  
        e.setNombre(req.nombre());
        e.setRut(rut.isEmpty() ? null : rut);
        e.setRazonSocial(req.razonSocial());
        e.setEstado(true);

        return toDTO(repo.save(e));
    }
	
	@Override
	@Transactional(readOnly = true)
	public EmpresaDTO obtener(Integer idCliente) {
		Empresa e = obtenerEmpresa(idCliente);
		return toDTO(e);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EmpresaDTO> listar() {
		return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public EmpresaDTO actualizar(Integer idCliente, ActualizarEmpresaRequest req) {
		Empresa e = obtenerEmpresa(idCliente);

		e.setNombre(req.nombre());
		e.setRazonSocial(req.razonSocial());

		return toDTO(repo.save(e));
	}
	
	@Override
	public void eliminar(Integer idCliente) {
		delete(idCliente);
	}
	
	@Override
	public EmpresaDTO cambiarEstado(Integer idCliente) {
		Empresa e = repo.findById(idCliente)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empresa para idCliente=" + idCliente));
		e.setEstado(!e.getEstado());
		return toDTO(repo.save(e));
	}
	
	@Override
	public JpaRepository<Empresa, Integer> getDao() {
		return repo;
	}

	private EmpresaDTO toDTO(Empresa e) {
		return new EmpresaDTO(
				e.getIdCliente(),
				e.getNombre(),
				e.getRut(),
				e.getRazonSocial(),
				e.getEstado()
				);
	}
	
	private Cliente obtenerEntidad(Integer idCliente) {
        return clienteRepo.findById(idCliente)
            .orElseThrow(() -> new ResourceNotFoundException("No existe Cliente id=" + idCliente));
    }
	
	public Empresa obtenerEmpresa(Integer idCliente) {
		return repo.findById(idCliente)
				.orElseThrow(() -> new ResourceNotFoundException("No existe Empresa para idCliente=" + idCliente));
	}
	
	public EmpresaDTO obtenerEmpresaDTO(Integer idCliente) {
		return toDTO(obtenerEmpresa(idCliente));
	}
}
