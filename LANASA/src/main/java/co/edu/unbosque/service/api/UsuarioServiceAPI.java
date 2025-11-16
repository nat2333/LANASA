package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.UsuarioDTOs.*;
import co.edu.unbosque.entity.Usuario;

public interface UsuarioServiceAPI {

	UsuarioDTO crear(CrearUsuarioRequest req);
	
    UsuarioDTO obtener(Integer id);
    
    List<UsuarioDTO> listar();
    
    UsuarioDTO actualizar(Integer id, ActualizarUsuarioRequest req);
    
    void eliminar(Integer id);
    
    UsuarioDTO cambiarEstado(Integer id);
    
    Usuario findByLogin(String login);
    
}
