package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.TipoClienteDTOs.*;

public interface TipoClienteServiceAPI {

	TipoClienteDTO crear(CrearTipoClienteRequest req);
    
	TipoClienteDTO obtener(Short id);
    
	List<TipoClienteDTO> listar();
    
	void eliminar(Short id);
    
	TipoClienteDTO cambiarEstado(Short id);
}
