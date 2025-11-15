package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.TipoUsuarioDTOs.*;

public interface TipoUsuarioServiceAPI {

	TipoUsuarioDTO crear(CrearTipoUsuarioRequest req);
	
    TipoUsuarioDTO obtener(Short id);
    
    List<TipoUsuarioDTO> listar();
    
    TipoUsuarioDTO actualizar(Short id, ActualizarTipoUsuarioRequest req);
    
    void eliminar(Short id);
    
    TipoUsuarioDTO cambiarEstado(Short id);
}
