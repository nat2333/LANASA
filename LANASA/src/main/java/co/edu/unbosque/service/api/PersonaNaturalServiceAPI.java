package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.PersonaNaturalDTOs.*;

public interface PersonaNaturalServiceAPI {

	PersonaNaturalDTO crear(CrearPersonaNaturalRequest req);
    
	PersonaNaturalDTO obtener(Integer idCliente);
    
	List<PersonaNaturalDTO> listar();
    
	PersonaNaturalDTO actualizar(Integer idCliente, ActualizarPersonaNaturalRequest req);
    
	void eliminar(Integer idCliente);
    
	PersonaNaturalDTO cambiarEstado(Integer idCliente);
}
