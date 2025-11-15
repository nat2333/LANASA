package co.edu.unbosque.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.DetalleOrdenCompraDTOs.*;
import co.edu.unbosque.entity.DetalleOrdenCompra;
import co.edu.unbosque.entity.OrdenCompra;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.repository.DetalleOrdenCompraRepository;
import co.edu.unbosque.repository.OrdenCompraRepository;
import co.edu.unbosque.repository.ProductoRepository;
import co.edu.unbosque.service.api.DetalleOrdenCompraServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class DetalleOrdenCompraServiceImpl extends GenericServiceImpl<DetalleOrdenCompra, Integer> implements DetalleOrdenCompraServiceAPI {

	@Autowired
	private DetalleOrdenCompraRepository repo;
	@Autowired
	private OrdenCompraRepository ocRepo;
	@Autowired
	private ProductoRepository prodRepo;
	
	@Override
	public DetalleOrdenCompraDTO crear(CrearDetalleOCRequest req) {
		OrdenCompra oc = obtenerOrdenCompra(req.idOrdenCompra());
		
		if(!oc.getEstadoCompra().getEstadoCompra().equals("PENDIENTE")) {
			throw new IllegalArgumentException("La orden ya fue pagada o anulada");
		}
		
		Producto p = obtenerProducto(req.idProducto());
		
		if (repo.existsByOrdenCompra_IdOrdenCompraAndProducto_IdProducto(oc.getIdOrdenCompra(), p.getIdProducto())) {
			throw new IllegalArgumentException("La orden ya contiene ese producto");
		}

		DetalleOrdenCompra d = new DetalleOrdenCompra();
		d.setOrdenCompra(oc);
		d.setProducto(p);
		d.setCantidad(req.cantidad());
		d.setPrecioUnitario(req.precioUnitario());
		d.setEstado(true);

		return toDTO(repo.save(d));
	}
	
	@Override
	@Transactional(readOnly = true)
	public DetalleOrdenCompraDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DetalleOrdenCompraDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public DetalleOrdenCompraDTO actualizar(Integer id, ActualizarDetalleOCRequest req) {
		var d = obtenerEntidad(id);
        d.setCantidad(req.cantidad());
        d.setPrecioUnitario(req.precioUnitario());
        return toDTO(repo.save(d));
	}
	
	@Override
	public void eliminar(Integer id) {
		delete(id);
	}
	
	@Override
	public DetalleOrdenCompraDTO cambiarEstado(Integer id) {
		var d = obtenerEntidad(id);
        d.setEstado(!d.getEstado());
        return toDTO(repo.save(d));
	}

	@Override 
	@Transactional(readOnly = true)
	public List<DetalleOrdenCompraDTO> listarPorOrden(Integer idOrdenCompra) {
		return repo.findByOrdenCompra_IdOrdenCompra(idOrdenCompra).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override 
	@Transactional(readOnly = true)
	public List<DetalleOrdenCompraDTO> listarPorProducto(Integer idProducto) {
		return repo.findByProducto_IdProducto(idProducto).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public JpaRepository<DetalleOrdenCompra, Integer> getDao() {
		return repo;
	}
	
	private DetalleOrdenCompra obtenerEntidad(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe DetalleOC id=" + id));
    }
	
	private OrdenCompra obtenerOrdenCompra(Integer id) {
		return ocRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe OrdenCompra id=" + id));
	}
	
	private Producto obtenerProducto(Integer id) {
		return prodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Producto id=" + id));
	}

	private BigDecimal calcularSubtotal(int cantidad, BigDecimal precioUnit) {
		return precioUnit.multiply(BigDecimal.valueOf(cantidad));
	}
	
    private DetalleOrdenCompraDTO toDTO(DetalleOrdenCompra d) {
        OrdenCompra oc = d.getOrdenCompra();
        Producto p  = d.getProducto();
        BigDecimal subtotal = calcularSubtotal(d.getCantidad(), d.getPrecioUnitario());
        return new DetalleOrdenCompraDTO(
            d.getIdDetalleOrdenCompra(),
            oc.getIdOrdenCompra(),
            oc.getNumero(),
            p.getIdProducto(),
            p.getSku(),
            p.getNombre(),
            d.getCantidad(),
            d.getPrecioUnitario(),
            subtotal,
            d.getEstado()
        );
    }
	
}
