package co.edu.unbosque.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.ProductoDTOs.*;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.repository.ProductoRepository;
import co.edu.unbosque.service.api.ProductoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class ProductoServiceImpl extends GenericServiceImpl<Producto, Integer> implements ProductoServiceAPI {

	@Autowired
    private ProductoRepository repo;

	@Override
	public ProductoDTO crear(CrearProductoRequest req) {
		String sku = req.sku().trim();
        if (repo.existsBySku(sku)) throw new IllegalArgumentException("Ya existe SKU=" + sku);

        Producto p = new Producto();
        p.setSku(sku);
        p.setNombre(req.nombre().trim());
        p.setDescripcion(req.descripcion());
        p.setCategoria(req.categoria());
        p.setPrecioCompra(req.precioCompra());
        p.setPrecioVentaSugerido(req.precioVentaSugerido());
        p.setStockMinimo(req.stockMinimo() != null ? req.stockMinimo() : 0);
        p.setStockActual(req.stockActual() != null ? req.stockActual() : 0);
        p.setStockMaximo(req.stockMaximo() != null ? req.stockMaximo() : 0);
        p.setEstado(true);

        return toDTO(repo.save(p));
	}

	@Override
	public ProductoDTO obtener(Integer id) {
		return toDTO(getRequired(id));
	}

	@Override
	public List<ProductoDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public ProductoDTO actualizar(Integer id, ActualizarProductoRequest req) {
		Producto p = getRequired(id);

		p.setNombre(req.nombre().trim());
		p.setDescripcion(req.descripcion());
		p.setPrecioCompra(req.precioCompra());
		p.setPrecioVentaSugerido(req.precioVentaSugerido());
		p.setStockMinimo(Math.max(0, req.stockMinimo()));
		p.setStockActual(Math.max(0, req.stockActual()));
		p.setStockMaximo(Math.max(0, req.stockMaximo()));

		return toDTO(repo.save(p));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public ProductoDTO cambiarEstado(Integer id) {
		var p = getRequired(id);
		p.setEstado(!p.getEstado());
		return toDTO(repo.save(p));
	}

	@Override
	public JpaRepository<Producto, Integer> getDao() {
		return repo;
	}
	
	private ProductoDTO toDTO(Producto p) {
        return new ProductoDTO(
            p.getIdProducto(), p.getSku(), p.getNombre(), p.getDescripcion(), p.getCategoria(),
            p.getPrecioCompra(), p.getPrecioVentaSugerido(),
            p.getStockMinimo(), p.getStockActual(), p.getStockMaximo(),
            p.getEstado()
        );
    }

    private Producto getRequired(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe Producto id=" + id));
    }
	
	

}
