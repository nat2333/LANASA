package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.FacturaVentaDTOs.*;

public interface FacturaVentaServiceAPI {

	FacturaVentaDTO crear(CrearFacturaVentaRequest req);
    
	FacturaVentaDTO obtener(Integer id);
    
	List<FacturaVentaDTO> listar();
    
	FacturaVentaDTO actualizar(Integer id, ActualizarFacturaVentaRequest req);
    
	void eliminar(Integer id);
    
	FacturaVentaDTO cambiarEstado(Integer id);
	
	List<FacturaVentaDTO> porCliente(Integer idCliente);
	
    List<FacturaVentaDTO> porProyecto(Integer idProyecto);
}
