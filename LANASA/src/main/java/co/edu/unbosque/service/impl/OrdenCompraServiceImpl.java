package co.edu.unbosque.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.OrdenCompraDTOs.*;
import co.edu.unbosque.entity.EstadoCompra;
import co.edu.unbosque.entity.OrdenCompra;
import co.edu.unbosque.entity.Proveedor;
import co.edu.unbosque.repository.EstadoCompraRepository;
import co.edu.unbosque.repository.OrdenCompraRepository;
import co.edu.unbosque.repository.ProveedorRepository;
import co.edu.unbosque.service.api.OrdenCompraServiceAPI;
import co.edu.unbosque.utils.CrearCodigos;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class OrdenCompraServiceImpl extends GenericServiceImpl<OrdenCompra, Integer> implements OrdenCompraServiceAPI{

	@Autowired
	private OrdenCompraRepository repo;
	@Autowired
    private ProveedorRepository proveedorRepo;
	@Autowired
    private EstadoCompraRepository estadoRepo;
	
	@Override
	public OrdenCompraDTO crear(CrearOrdenCompraRequest req) {
	        Proveedor prov = obtenerProveedor(req.idProveedor());
	        EstadoCompra estado = obtenerEstadoCompra(req.idEstadoCompra());

	        OrdenCompra oc = new OrdenCompra();
	        oc.setNumero(CrearCodigos.generarCodigoTemporal());
	        oc.setProveedor(prov);
	        oc.setEstadoCompra(estado);
	        oc.setFechaOrden(req.fechaOrden());
	        oc.setFechaEntregaEsperada(req.fechaEntregaEsperada());
	        oc.setFechaEntregaReal(req.fechaEntregaReal());
	        oc.setIdProyecto(req.idProyecto());
	        oc.setEstado(true);
	        oc = repo.save(oc);
	        String numero = CrearCodigos.generarCodigo("OC", String.valueOf(oc.getIdOrdenCompra()), 3, 3);
	        oc.setNumero(numero);
	        return toDTO(repo.save(oc));
	}
	
	@Override
	public OrdenCompraDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrdenCompraDTO> listar() {
        return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public OrdenCompraDTO actualizar(Integer id, ActualizarOrdenCompraRequest req) {
		OrdenCompra oc = obtenerEntidad(id);
        oc.setFechaEntregaReal(req.fechaEntregaReal());
        return toDTO(repo.save(oc));
	}
	
	@Override
	public void eliminar(Integer id) {
		delete(id);
	}
	
	@Override
	public OrdenCompraDTO cambiarEstado(Integer id) {
		OrdenCompra oc = obtenerEntidad(id);
        oc.setEstado(!oc.getEstado());
        return toDTO(repo.save(oc));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrdenCompraDTO> listarPorProveedor(Integer idProveedor) {
        return repo.findByProveedor_IdProveedor(idProveedor).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<OrdenCompraDTO> listarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return repo.findByFechaOrdenBetween(desde, hasta).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public JpaRepository<OrdenCompra, Integer> getDao() {
		return repo;
	}
	
	private OrdenCompraDTO toDTO(OrdenCompra oc) {
        Proveedor prov = oc.getProveedor();
        EstadoCompra est  = oc.getEstadoCompra();
        return new OrdenCompraDTO(
                oc.getIdOrdenCompra(),
                oc.getNumero(),
                oc.getFechaOrden(),
                oc.getFechaEntregaEsperada(),
                oc.getFechaEntregaReal(),
                (oc.getIdProyecto() == null) ? 0 : oc.getIdProyecto() ,
                prov.getNombreComercial(),
                est.getEstadoCompra(),
                oc.getEstado()
            );
    }
	
	private OrdenCompra obtenerEntidad(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe OrdenCompra id=" + id));
    }
	
	private Proveedor obtenerProveedor(Integer id) {
		return proveedorRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Proveedor id=" + id));
	}
	
	private EstadoCompra obtenerEstadoCompra(Short id) {
		return estadoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe EstadoCompra id=" + id));
	}

	
	
}
