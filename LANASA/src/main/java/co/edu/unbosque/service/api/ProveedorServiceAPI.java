package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ProveedorDTOs.*;

public interface ProveedorServiceAPI {

	ProveedorDTO crear(CrearProveedorRequest req);
	
	ProveedorDTO obtener(Integer id);
	
	List<ProveedorDTO> listar();
	
	ProveedorDTO actualizar(Integer id, ActualizarProveedorRequest req);
	
	void eliminar(Integer id);
	
	ProveedorDTO cambiarEstado(Integer id);
}
