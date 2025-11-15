package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.CargoDTOs.*;
import co.edu.unbosque.entity.Cargo;
import co.edu.unbosque.repository.CargoRepository;
import co.edu.unbosque.service.api.CargoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class CargoServiceImpl extends GenericServiceImpl<Cargo, Short> implements CargoServiceAPI {

	@Autowired
	private CargoRepository rep;
	
	@Override
	public CargoDTO crear(CrearCargoRequest req) {
        if (rep.existsByNombreCargo(req.nombreCargo())) {
            throw new IllegalArgumentException("Ya existe un Cargo con nombre=" + req.nombreCargo());
        }
        Cargo c = new Cargo();
        c.setNombreCargo(req.nombreCargo());
        c.setSalario(req.salario());
        c.setEstado(true);
        return toDTO(rep.save(c));
	}

	@Override
	@Transactional(readOnly = true)
	public CargoDTO obtener(Short id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CargoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public CargoDTO actualizar(Short id, ActualizarCargoRequest req) {
		Cargo c = obtenerEntidad(id);
        c.setSalario(req.salario());

        return toDTO(rep.save(c));
	}

	@Override
	public void eliminar(Short id) {
		delete(id);
		
	}

	@Override
	public CargoDTO cambiarEstado(Short id) {
		Cargo c = obtenerEntidad(id);
        c.setEstado(!c.getEstado());
        return toDTO(rep.save(c));
	}

	@Override
	public JpaRepository<Cargo, Short> getDao() {
		return rep;
	}
	
	private CargoDTO toDTO(Cargo c) {
        return new CargoDTO(c.getIdCargo(), c.getNombreCargo(), c.getSalario(), c.getEstado());
    }
	
	private Cargo obtenerEntidad(Short id) {
		return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Cargo con id=" + id));
	}

}
