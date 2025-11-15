package co.edu.unbosque.service.api;


import java.util.List;

import co.edu.unbosque.dto.EstadoCompraDTOs.*;

public interface EstadoCompraServiceAPI {

	EstadoCompraDTO crear(CrearEstadoCompraRequest req);
    
	EstadoCompraDTO obtener(Short id);
    
	List<EstadoCompraDTO> listar();
    
	EstadoCompraDTO actualizar(Short id, ActualizarEstadoCompraRequest req);
    
	void eliminar(Short id);
    
	EstadoCompraDTO cambiarEstado(Short id);
}
