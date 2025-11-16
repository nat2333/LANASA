package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.ProyectoDtos.ActualizarProyectoRequest;
import co.edu.unbosque.dto.ProyectoDtos.CrearProyectoRequest;
import co.edu.unbosque.dto.ProyectoDtos.ProyectoDTO;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.Departamento;
import co.edu.unbosque.entity.Proyecto;
import co.edu.unbosque.entity.TipoProyecto;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.DepartamentoRepository;
import co.edu.unbosque.repository.ProyectoRepository;
import co.edu.unbosque.repository.TipoProyectoRepository;
import co.edu.unbosque.service.api.ProyectoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class ProyectoServiceImpl extends GenericServiceImpl<Proyecto, Integer> implements ProyectoServiceAPI{

	@Autowired
	private ProyectoRepository repo;
	@Autowired
	private TipoProyectoRepository tipoRepo;
	@Autowired
    private ClienteRepository clienteRepo;
	@Autowired
    private DepartamentoRepository deptoRepo;
	
	@Override
	public ProyectoDTO crear(CrearProyectoRequest req) {
		String nombre = req.nombre();
		if(repo.existsByNombre(nombre))
			throw new IllegalArgumentException("Ya existe un Proyecto con nombre=" + nombre);

		TipoProyecto tipo = tipoRepo.findById(req.idTipoProyecto())
				.orElseThrow(() -> new ResourceNotFoundException("TipoProyecto no existe: " + req.idTipoProyecto()));

		Cliente cli = clienteRepo.findById(req.idCliente())
				.orElseThrow(() -> new ResourceNotFoundException("Cliente no existe: " + req.idCliente()));

		Departamento dep = deptoRepo.findById(req.idDepartamento())
				.orElseThrow(() -> new ResourceNotFoundException("Departamento no existe: " + req.idDepartamento()));
		
		Proyecto p = new Proyecto();
		p.setCodigo(" ");
        p.setNombre(req.nombre().trim());
        p.setDescripcion(req.descripcion());
        p.setFechaInicio(req.fechaInicio());
        p.setFechaFinEstimada(req.fechaFinEstimada());
        p.setPresupuestoAprobado(req.presupuestoAprobado());
        p.setPresupuestoUtilizado(req.presupuestoUtilizado());
        p.setEstado(true);
        p.setTipoProyecto(tipo);
        p.setCliente(cli);
        p.setDepartamento(dep);
        
		return toDTO(repo.save(p));
	}

	@Override
	public ProyectoDTO obtener(Integer id) {
		return toDTO(getRequired(id));
	}

	@Override
	public List<ProyectoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public ProyectoDTO actualizar(Integer id, ActualizarProyectoRequest req) {
		Proyecto p = getRequired(id);
		if (req.fechaFinReal() != null) p.setFechaFinReal(req.fechaFinReal());
		if(req.presupuestoAprobado() != null) p.setPresupuestoAprobado(req.presupuestoAprobado());
		if(req.presupuestoUtilizado() != null) p.setPresupuestoUtilizado(req.presupuestoUtilizado());
		
		return toDTO(repo.save(p));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public ProyectoDTO cambiarEstado(Integer id) {
		Proyecto p = getRequired(id);
		p.setEstado(!p.getEstado());
		return toDTO(repo.save(p));
	}

	/*
	@Override
	public List<ProyectoDTO> listarActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProyectoDTO> porDepartamento(Integer idDepartamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProyectoDTO> porCliente(Integer idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProyectoDTO> porTipoProyecto(Short idTipoProyecto) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public JpaRepository<Proyecto, Integer> getDao() {
		return repo;
	}

	private Proyecto getRequired(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Proyecto id=" + id));
    }
	
	private ProyectoDTO toDTO(Proyecto p) {
        var cli = p.getCliente();
        var dep = p.getDepartamento();
        var tip = p.getTipoProyecto();
        return new ProyectoDTO(
            p.getIdProyecto(),
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            p.getFechaInicio(),
            p.getFechaFinEstimada(),
            p.getFechaFinReal(),
            p.getPresupuestoAprobado(),
            p.getPresupuestoUtilizado(),
            p.getEstado(),
            cli.getIdCliente(),
            cli.getCorreo(),
            dep.getIdDepartamento(),
            dep.getNombre(),
            tip.getIdTipoProyecto(),
            tip.getTipoProyecto()
        );
    }
	
}
