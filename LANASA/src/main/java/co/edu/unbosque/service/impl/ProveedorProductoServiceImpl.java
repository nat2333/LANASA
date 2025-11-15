package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.ProveedorProductoDTOs.*;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.entity.Proveedor;
import co.edu.unbosque.entity.ProveedorProducto;
import co.edu.unbosque.repository.ProductoRepository;
import co.edu.unbosque.repository.ProveedorProductoRepository;
import co.edu.unbosque.repository.ProveedorRepository;
import co.edu.unbosque.service.api.ProveedorProductoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional

public class ProveedorProductoServiceImpl extends GenericServiceImpl<ProveedorProducto, Integer> implements ProveedorProductoServiceAPI {

	@Autowired
	private ProveedorProductoRepository repo;
	@Autowired
	private ProductoRepository prodRepo;
	@Autowired
	private ProveedorRepository provRepo;
	
	@Override
	public ProveedorProductoDTO crear(CrearProveedorProductoRequest req) {
		Producto prod = obtenerProducto(req.idProducto());
		Proveedor prov = obtenerProveedor(req.idProveedor());

		if (repo.existsByProducto_IdProductoAndProveedor_IdProveedor(prod.getIdProducto(), prov.getIdProveedor()))
			throw new IllegalArgumentException("Ya existe relación para ese producto y proveedor.");

		ProveedorProducto pp = new ProveedorProducto();
		pp.setProducto(prod);
		pp.setProveedor(prov);
		pp.setCalificacion(req.calificacion());
		pp.setEstado(true);

		return toDTO(repo.save(pp));
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProveedorProductoDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorProductoDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public ProveedorProductoDTO actualizar(Integer id, ActualizarProveedorProductoRequest req) {
		ProveedorProducto pp = obtenerEntidad(id);
        pp.setCalificacion(req.calificacion());
        return toDTO(repo.save(pp));
	}
	
	@Override
	public void eliminar(Integer id) {
		delete(id);
	}
	
	@Override
	public ProveedorProductoDTO cambiarEstado(Integer id) {
		ProveedorProducto pp = obtenerEntidad(id);
        pp.setEstado(!pp.getEstado());
        return toDTO(repo.save(pp));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorProductoDTO> listarPorProducto(Integer idProducto) {
        return repo.findByProducto_IdProducto(idProducto).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProveedorProductoDTO> listarPorProveedor(Integer idProveedor) {
        return repo.findByProveedor_IdProveedor(idProveedor).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public JpaRepository<ProveedorProducto, Integer> getDao() {
		return repo;
	}
	
	private ProveedorProducto obtenerEntidad(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe ProveedorProducto id=" + id));
    }
	
	private Proveedor obtenerProveedor(Integer id) {
		return provRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Proveedor id=" + id));
	}
	
	private Producto obtenerProducto(Integer id) {
		return prodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Producto id=" + id));
	}


    private ProveedorProductoDTO toDTO(ProveedorProducto pp) {
        var p  = pp.getProducto();
        var pr = pp.getProveedor();
        return new ProveedorProductoDTO(
            pp.getIdProveedorProducto(),
            p.getIdProducto(),
            p.getSku(),
            p.getNombre(),
            pr.getIdProveedor(),
            pr.getRut(),
            pr.getNombreComercial(),
            pp.getCalificacion(),
            pp.getEstado()
        );
    }

	
	
}
