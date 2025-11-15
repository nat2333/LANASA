package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.TipoContratoDTOs.*;

public interface TipoContratoServiceAPI {

	TipoContratoDTO crear(CrearTipoContratoRequest req);
    
	TipoContratoDTO obtener(Short id);
    
	List<TipoContratoDTO> listar();
    
	TipoContratoDTO actualizar(Short id, ActualizarTipoContratoRequest req);
    
	void eliminar(Short id);
    
	TipoContratoDTO cambiarEstado(Short id);
}
