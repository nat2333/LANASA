package co.edu.unbosque.service.api;

import java.time.LocalDate;
import java.util.List;

import co.edu.unbosque.dto.HistorialEmpleadoDTOs.*;

public interface HistorialServiceAPI {
	
	HistorialDTO crear(CrearHistorialRequest req);
    
	HistorialDTO obtener(Integer id);
    
	List<HistorialDTO> listar();
    
	HistorialDTO actualizar(Integer id, ActualizarHistorialRequest req);
    
	void eliminar(Integer id);
    
	HistorialDTO cambiarEstado(Integer id, LocalDate fechaFin); 
    
	List<HistorialDTO> listarPorEmpleado(Integer idEmpleado);

}
