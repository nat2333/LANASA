package co.edu.unbosque.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.FacturaCompraDTOs.*;
import co.edu.unbosque.entity.DetalleOrdenCompra;
import co.edu.unbosque.entity.EstadoFactura;
import co.edu.unbosque.entity.FacturaCompra;
import co.edu.unbosque.entity.OrdenCompra;
import co.edu.unbosque.repository.DetalleOrdenCompraRepository;
import co.edu.unbosque.repository.EstadoFacturaRepository;
import co.edu.unbosque.repository.FacturaCompraRepository;
import co.edu.unbosque.repository.OrdenCompraRepository;
import co.edu.unbosque.service.api.FacturaCompraServiceAPI;
import co.edu.unbosque.utils.CrearCodigos;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class FacturaCompraServiceImpl extends GenericServiceImpl<FacturaCompra, Integer> implements FacturaCompraServiceAPI {

	@Autowired
	private FacturaCompraRepository repo;
	@Autowired
    private OrdenCompraRepository ocRepo;
	@Autowired
    private EstadoFacturaRepository estRepo;
	@Autowired
	private DetalleOrdenCompraRepository detRepo;
	
	@Override
	public FacturaCompraDTO crear(CrearFacturaCompraRequest req) {
        OrdenCompra oc = obtenerOrdenCompra(req.idOrdenCompra());
        EstadoFactura est = obtenerEstadoFactura(req.idEstadoFactura());

        var f = new FacturaCompra();
        f.setNumero(CrearCodigos.generarCodigoTemporal());
        f.setFechaFactura(LocalDateTime.now());
        f.setMontoTotal(calcularMonto(oc.getIdOrdenCompra()));
        f.setEstado(true);
        f.setOrdenCompra(oc);
        f.setEstadoFactura(est);
        f = repo.save(f);
        String numero = CrearCodigos.generarCodigo("COMPRA", String.valueOf(f.getIdFacturaCompra()), 3, 3);
        f.setNumero(numero);
        return toDTO(repo.save(f));
	}

	@Override
	@Transactional(readOnly = true)
	public FacturaCompraDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaCompraDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public FacturaCompraDTO actualizar(Integer id, ActualizarFacturaCompraRequest req) {
		FacturaCompra f = obtenerEntidad(id);
		
		if (req.idEstadoFactura() != null) {
            var est = estRepo.findById(req.idEstadoFactura())
                .orElseThrow(() -> new ResourceNotFoundException("EstadoFactura no existe: " + req.idEstadoFactura()));
            f.setEstadoFactura(est);
        }

        return toDTO(repo.save(f));
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
	}

	@Override
	public FacturaCompraDTO cambiarEstado(Integer id) {
		FacturaCompra f = obtenerEntidad(id);
        f.setEstado(!Boolean.TRUE.equals(f.getEstado()));
        return toDTO(repo.save(f));
	}

	@Override @Transactional(readOnly = true)
    public List<FacturaCompraDTO> listarPorOrden(Integer idOrdenCompra) {
        return repo.findByOrdenCompra_IdOrdenCompra(idOrdenCompra).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<FacturaCompraDTO> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return repo.findByFechaFacturaBetween(desde, hasta).stream().map(this::toDTO).collect(Collectors.toList());
    }

	@Override
	public JpaRepository<FacturaCompra, Integer> getDao() {
		return repo;
	}
	
	private FacturaCompra obtenerEntidad(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe FacturaCompra id=" + id));
    }
	
	private OrdenCompra obtenerOrdenCompra(Integer id) {
		return ocRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe OrdenCompra id=" + id));
	}
	
	private EstadoFactura obtenerEstadoFactura(Short id) {
		return estRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe EstadoFactura id=" + id));
	}

    private FacturaCompraDTO toDTO(FacturaCompra f) {
        OrdenCompra oc  = f.getOrdenCompra();
        EstadoFactura est = f.getEstadoFactura();
        return new FacturaCompraDTO(
            f.getIdFacturaCompra(),
            f.getNumero(),
            f.getFechaFactura(),
            f.getMontoTotal(),
            f.getEstado(),
            oc.getNumero(),
            est.getNombre()
        );
    }

    private BigDecimal calcularMonto(Integer idCompra) {
    	List<DetalleOrdenCompra> detalles = detRepo.findByOrdenCompra_IdOrdenCompra(idCompra);
    	BigDecimal monto = BigDecimal.ZERO;
    	
    	for(DetalleOrdenCompra d: detalles) {
    		BigDecimal subtotal = d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad()));
    		monto = monto.add(subtotal);
    	}
    	
    	return monto;
    }
	
}
