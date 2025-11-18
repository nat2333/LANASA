package co.edu.unbosque.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.DepartamentoDTOs.*;
import co.edu.unbosque.dto.DepartamentoEstadisticasDTO;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionDepartamentoEstadistica;
import co.edu.unbosque.entity.Departamento;
import co.edu.unbosque.repository.DepartamentoRepository;
import co.edu.unbosque.service.api.DepartamentoServiceAPI;
import co.edu.unbosque.utils.CrearCodigos;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class DepartamentoServiceImpl extends GenericServiceImpl<Departamento, Integer> implements DepartamentoServiceAPI{

	@Autowired
	private DepartamentoRepository rep;
	
	@Override
	public DepartamentoDTO crear(CrearDepartamentoRequest req) {
		if (rep.existsByNombre(req.nombre()))
			throw new IllegalArgumentException("Ya existe un Departamento con nombre=" + req.nombre());
		
		Departamento d = new Departamento();
		d.setNombre(req.nombre());
		d.setCodigo(CrearCodigos.generarCodigoTemporal());
		d.setPresupuestoAnual(req.presupuestoAnual());
		d.setFechaCreacion(LocalDateTime.now());
		d.setEstado(true);
		d = rep.save(d);
		
		String codigo = CrearCodigos.generarCodigo(d.getNombre(), String.valueOf(d.getIdDepartamento()), 3, 3);
		d.setCodigo(codigo);
		return toDTO(rep.save(d));
	}

	@Override
	@Transactional(readOnly = true)
	public DepartamentoDTO obtener(Integer id) {
		Departamento d = obtenerEntidad(id);
		return toDTO(d);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartamentoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public DepartamentoDTO actualizar(Integer id, ActualizarDepartamentoRequest req) {
		Departamento d = obtenerEntidad(id);
        if (req.presupuestoAnual() != null) d.setPresupuestoAnual(req.presupuestoAnual());
        return toDTO(rep.save(d));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public DepartamentoDTO cambiarEstado(Integer id) {
		Departamento d = obtenerEntidad(id);
        d.setEstado(!d.getEstado());
        return toDTO(rep.save(d));
	}

	@Override
	public JpaRepository<Departamento, Integer> getDao() {
		return rep;
	}
	
	@Override
    public List<DepartamentoEstadisticasDTO> obtenerNomina() {
		
		List<DepartamentoEstadisticasDTO> dtos = new ArrayList<>();
        List<ProyeccionDepartamentoEstadistica> proyecciones = rep.obtenerEstadisticasDepartamentos();
        
        for(ProyeccionDepartamentoEstadistica p : proyecciones) {
        	dtos.add(toDTOEstadisticas(p));
        }
        
        return dtos;
    }
	
	private DepartamentoDTO toDTO(Departamento d) {
		return new DepartamentoDTO(d.getIdDepartamento(), d.getNombre(),d.getCodigo(), d.getFechaCreacion(),
	            d.getPresupuestoAnual(),d.getEstado());
	}
	
	private Departamento obtenerEntidad(int id) {
		return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Departamento con ID=" + id));
	}
	
	private DepartamentoEstadisticasDTO toDTOEstadisticas(ProyeccionDepartamentoEstadistica p) {
		return new DepartamentoEstadisticasDTO(
                p.getNombreDepartamento(),
                p.getCodigo(),
                p.getCantidadEmpleados(),
                p.getNominaTotal(),
                p.getPresupuestoAnual(),
                p.getDiferencia()
        );
	}

}
