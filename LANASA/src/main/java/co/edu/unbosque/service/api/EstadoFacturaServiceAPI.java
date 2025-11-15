package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.EstadoFacturaDtos.*;

public interface EstadoFacturaServiceAPI {
    
	EstadoFacturaDTO crear(CrearEstadoFacturaRequest req);
    
	EstadoFacturaDTO obtener(Short id);
    
	List<EstadoFacturaDTO> listar();
    
	EstadoFacturaDTO actualizar(Short id, ActualizarEstadoFacturaRequest req);
    
	void eliminar(Short id);
    
	EstadoFacturaDTO cambiarEstado(Short id);
}
