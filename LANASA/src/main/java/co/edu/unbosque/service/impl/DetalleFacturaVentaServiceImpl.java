package co.edu.unbosque.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.DetalleFacturaVentaDTOs.*;
import co.edu.unbosque.entity.*;
import co.edu.unbosque.repository.*;
import co.edu.unbosque.service.api.DetalleFacturaVentaServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class DetalleFacturaVentaServiceImpl extends GenericServiceImpl<DetalleFacturaVenta, Integer> implements DetalleFacturaVentaServiceAPI {

	@Autowired
    private DetalleFacturaVentaRepository repo;
	@Autowired
    private FacturaVentaRepository facRepo;
	@Autowired
    private ProductoRepository prodRepo;

    @Override
    public DetalleFacturaVentaDTO crear(CrearDetalleFacturaVentaRequest req) {
        FacturaVenta f = obtenerFactura(req.idFacturaVenta());
       
        if(!f.getEstadoFactura().getNombre().equals("PENDIENTE")) {
			throw new IllegalArgumentException("La factura ya fue pagada o anulada");
		}
		
        Producto p = obtenerProducto(req.idProducto());
        
        if (repo.existsByFacturaVenta_IdFacturaVentaAndProducto_IdProducto(f.getIdFacturaVenta(), p.getIdProducto()))
            throw new IllegalArgumentException("Ya existe un detalle para ese producto en la factura");

        DetalleFacturaVenta d = new DetalleFacturaVenta();
        d.setFacturaVenta(f);
        d.setProducto(p);
        d.setCantidad(req.cantidad());
        d.setPrecioUnitario(req.precioUnitario());
        d.setTipo(req.tipo());
        d.setEstado(true);
        
        recalcularTotalesFactura(f);

        return toDTO(repo.save(d));
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleFacturaVentaDTO obtener(Integer id) {
    	return toDTO(obtenerEntidad(id)); 
    }

    @Override
    public DetalleFacturaVentaDTO actualizar(Integer id, ActualizarDetalleFacturaVentaRequest req) {
        DetalleFacturaVenta d = obtenerEntidad(id);
        
        if (req.cantidad() != null) d.setCantidad(req.cantidad());
        if (req.precioUnitario() != null) d.setPrecioUnitario(req.precioUnitario());

        recalcularTotalesFactura(d.getFacturaVenta());
        return toDTO(repo.save(d));
    }

    @Override
    public void eliminar(Integer id) {
        DetalleFacturaVenta d = obtenerEntidad(id);
        FacturaVenta f = obtenerFactura(id);
        repo.delete(d);
        recalcularTotalesFactura(f);
    }

	@Override
	public List<DetalleFacturaVentaDTO> listarPorFactura(Integer idFacturaVenta) {
		List<DetalleFacturaVenta> detalles = repo.findByFacturaVenta_IdFacturaVenta(idFacturaVenta);
		List<DetalleFacturaVentaDTO> detallesDTO = new ArrayList<>();
		for(DetalleFacturaVenta d : detalles) {
			detallesDTO.add(toDTO(d));
		}
		return detallesDTO;
	}
	
	@Override
	public DetalleFacturaVentaDTO cambiarEstado(Integer id) {
		DetalleFacturaVenta d = obtenerEntidad(id);
		d.setEstado(!d.isEstado());
		return toDTO(repo.save(d));
	}
	
	@Override 
	public DetalleFacturaVentaRepository getDao() { 
		return repo; 
	}

	private DetalleFacturaVenta obtenerEntidad(Integer id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Detalle id=" + id));
	}
	
	private FacturaVenta obtenerFactura(int id) {
		return facRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("FacturaVenta no existe: " + id));
	}
	
	private Producto obtenerProducto(int id) {
		return prodRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no existe: " + id));
	}

	private DetalleFacturaVentaDTO toDTO(DetalleFacturaVenta d) {
		Producto p = d.getProducto();
		BigDecimal subtotalLinea = calcularSubtotal(d.getCantidad(), d.getPrecioUnitario());
		
		return new DetalleFacturaVentaDTO(
				d.getIdDetalleFacturaVenta(),
				d.getFacturaVenta().getIdFacturaVenta(),
				p.getIdProducto(),
				p.getSku(),
				p.getNombre(),
				d.getCantidad(),
				d.getPrecioUnitario(),
				subtotalLinea,
				d.getTipo()
		);
	}
	
	private BigDecimal calcularSubtotal(int cantidad, BigDecimal precioUnit) {
		return precioUnit.multiply(BigDecimal.valueOf(cantidad));
	}
	
	private void recalcularTotalesFactura(FacturaVenta f) {
        List<DetalleFacturaVenta> detalles = repo.findByFacturaVenta_IdFacturaVenta(f.getIdFacturaVenta());
        
        BigDecimal nuevoSubtotal = BigDecimal.ZERO;
        BigDecimal subtotalDetalle = BigDecimal.ZERO;
        
        for(DetalleFacturaVenta d : detalles) {
        	subtotalDetalle = calcularSubtotal(d.getCantidad(), d.getPrecioUnitario());
        	nuevoSubtotal = nuevoSubtotal.add(subtotalDetalle);
        }
        
        f.setSubtotal(nuevoSubtotal);
        f.setTotal(f.getSubtotal().add(f.getImpuestos()));
        facRepo.save(f);
    }
}
