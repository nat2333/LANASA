package co.edu.unbosque.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.FacturaVentaDTOs.*;
import co.edu.unbosque.entity.Cliente;
import co.edu.unbosque.entity.EstadoFactura;
import co.edu.unbosque.entity.FacturaVenta;
import co.edu.unbosque.entity.Proyecto;
import co.edu.unbosque.repository.ClienteRepository;
import co.edu.unbosque.repository.EstadoFacturaRepository;
import co.edu.unbosque.repository.FacturaVentaRepository;
import co.edu.unbosque.repository.ProyectoRepository;
import co.edu.unbosque.service.api.FacturaVentaServiceAPI;
import co.edu.unbosque.utils.CrearCodigos;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class FacturaVentaServiceImpl extends GenericServiceImpl<FacturaVenta, Integer> implements FacturaVentaServiceAPI {

	@Autowired
	private FacturaVentaRepository repo;
	@Autowired
    private ClienteRepository clienteRepo;
	@Autowired
    private ProyectoRepository proyectoRepo;
	@Autowired
    private EstadoFacturaRepository estadoFactRepo;
	
	@Override
	public FacturaVentaDTO crear(CrearFacturaVentaRequest req) {
		Cliente cliente = obtenerCliente(req.idCliente());
		Proyecto proyecto = obtenerProyecto(req.idProyecto());
		EstadoFactura estadoFactura = obtenerEstadoFactura(req.idEstadoFactura());
		
		 FacturaVenta f= new FacturaVenta();
	        f.setNumero(CrearCodigos.generarCodigoTemporal());
	        f.setFechaFacturaVenta(req.fechaFacturaVenta());
	        f.setSubtotal(req.subtotal());
	        f.setImpuestos(req.impuestos());
	        f.setTotal(calcularTotal(req.subtotal(), req.impuestos()));
	        f.setEstado(true);
	        f.setCliente(cliente);
	        f.setProyecto(proyecto);
	        f.setEstadoFactura(estadoFactura);
	        f = repo.save(f);
	        f.setNumero(CrearCodigos.generarCodigo("FACTURA", String.valueOf(f.getIdFacturaVenta()), 3, 3));
	        return toDTO(repo.save(f));
	}
	
	@Override
	@Transactional(readOnly = true)
	public FacturaVentaDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FacturaVentaDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public FacturaVentaDTO actualizar(Integer id, ActualizarFacturaVentaRequest req) {
		FacturaVenta f = obtenerEntidad(id);
		EstadoFactura estadoFactura = obtenerEstadoFactura(req.idEstadoFactura());
		f.setEstadoFactura(estadoFactura);		
		return toDTO(repo.save(f));
	}
	
	@Override
	public void eliminar(Integer id) {
		delete(id);
	}
	
	@Override
	public FacturaVentaDTO cambiarEstado(Integer id) {
		FacturaVenta f = obtenerEntidad(id);
		f.setEstado(!f.getEstado());
		return toDTO(repo.save(f));
	}
	
	@Override
	public JpaRepository<FacturaVenta, Integer> getDao() {
		return repo;
	}
	
	private FacturaVenta obtenerEntidad(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe FacturaVenta id=" + id));
    }
	
	private Cliente obtenerCliente(Integer id) {
		return clienteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Cliente id=" + id));
	}
	
	private Proyecto obtenerProyecto(Integer id) {
		return proyectoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Proyecto id=" + id));
	}
	
	private EstadoFactura obtenerEstadoFactura(Short id) {
		return estadoFactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe EstadoFactura id=" + id));
	}
	
	private FacturaVentaDTO toDTO(FacturaVenta f) {
        Cliente c = f.getCliente();
        Proyecto p = f.getProyecto();
        EstadoFactura e = f.getEstadoFactura();
        return new FacturaVentaDTO(
            f.getIdFacturaVenta(),
            f.getNumero(),
            f.getFechaFacturaVenta(),
            f.getSubtotal(),
            f.getImpuestos(),
            f.getTotal(),
            f.getEstado(),
            c.getIdCliente(),
            c.getCorreo(),
            p != null ? p.getIdProyecto() : null,
            p != null ? p.getCodigo() : null,
            e != null ? e.getIdEstadoFactura() : null,
            e != null ? e.getNombre() : null
        );
    }
	
	private BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal impuestos) {
		impuestos = impuestos.divide(BigDecimal.valueOf(100));
		BigDecimal valorImpuestos = subtotal.multiply(impuestos);
		return subtotal.add(valorImpuestos);
	}

	@Override @Transactional(readOnly = true)
    public List<FacturaVentaDTO> porCliente(Integer idCliente) {
        return repo.findByCliente_IdCliente(idCliente).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<FacturaVentaDTO> porProyecto(Integer idProyecto) {
        return repo.findByProyecto_IdProyecto(idProyecto).stream().map(this::toDTO).collect(Collectors.toList());
    }
	
	
}
