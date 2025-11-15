package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.MetodoPagoDTOs.*;

public interface MetodoPagoServiceAPI {

	MetodoPagoDTO crear(CrearMetodoPagoRequest req);
	
	MetodoPagoDTO obtener(Short id);
	
	List<MetodoPagoDTO> listar();
	
	MetodoPagoDTO actualizar(Short id, ActualizarMetodoPagoRequest req);
	
	void eliminar(Short id);
	
	MetodoPagoDTO cambiarEstado(Short id);
}
