package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.PagoDtos.*;
import co.edu.unbosque.entity.FacturaCompra;
import co.edu.unbosque.entity.MetodoPago;
import co.edu.unbosque.entity.Pago;
import co.edu.unbosque.repository.FacturaCompraRepository;
import co.edu.unbosque.repository.MetodoPagoRepository;
import co.edu.unbosque.repository.PagoRepository;
import co.edu.unbosque.service.api.PagoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class PagoServiceImpl extends GenericServiceImpl<Pago, Integer> implements PagoServiceAPI {

	@Autowired
	private PagoRepository repo;
	@Autowired
    private FacturaCompraRepository facRepo;
	@Autowired
    private MetodoPagoRepository metRepo;
	
	@Override
	public PagoDTO crear(CrearPagoRequest req) {
		FacturaCompra fac = facRepo.findById(req.idFacturaCompra())
	            .orElseThrow(() -> new ResourceNotFoundException("FacturaCompra no existe: " + req.idFacturaCompra()));
	        MetodoPago met = metRepo.findById(req.idMetodoPago())
	            .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no existe: " + req.idMetodoPago()));

	        //validarNoExcedeSaldo(fac.getIdFacturaCompra(), req.monto(), null);

	        var p = new Pago();
	        p.setFacturaCompra(fac);
	        p.setMetodoPago(met);
	        p.setFechaPago(LocalDateTime.now()); //**
	        p.setMonto(req.monto());
	        p.setEstado(true);
	        return toDTO(repo.save(p));
	        		
	}

	@Override
	@Transactional(readOnly = true)
	public PagoDTO obtener(Integer id) {
		return toDTO(getRequired(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<PagoDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
		
	}

	@Override
	public PagoDTO cambiarEstado(Integer id) {
		Pago p = getRequired(id);
        p.setEstado(!p.getEstado());
        return toDTO(repo.save(p));
	}

	@Override
	public List<PagoDTO> listarPorFactura(Integer idFacturaCompra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PagoDTO> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResumenPagosFacturaDTO resumenPorFactura(Integer idFacturaCompra) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarNoExcedeSaldo(Integer idFacturaCompra, BigDecimal montoNuevo, Integer idPagoAExcluir) {
		// TODO Auto-generated method stub
		
	}

	private Pago getRequired(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Pago id=" + id));
    }

    private PagoDTO toDTO(Pago p) {
        var f = p.getFacturaCompra();
        var m = p.getMetodoPago();
        return new PagoDTO(
            p.getIdPago(),
            f.getNumero(),
            m.getMetodoPago(),
            p.getFechaPago(),
            p.getMonto(),
            p.getEstado()
        );
    }
	
	@Override
	public JpaRepository<Pago, Integer> getDao() {
		return repo;
	}

	
}
