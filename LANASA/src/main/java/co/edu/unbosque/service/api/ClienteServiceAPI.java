package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ClienteDTOs.*;

public interface ClienteServiceAPI {

	ClienteDTO crear(CrearClienteRequest req);
    
	ClienteDTO obtener(Integer id);
    
	List<ClienteDTO> listar();
    
	ClienteDTO actualizar(Integer id, ActualizarClienteRequest req);
    
	void eliminar(Integer id);
    
	ClienteDTO cambiarEstado(Integer id);
}
