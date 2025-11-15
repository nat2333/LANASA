package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.CargoDTOs.*;

public interface CargoServiceAPI {

	CargoDTO crear(CrearCargoRequest req);
	
    CargoDTO obtener(Short id);
    
    List<CargoDTO> listar();
    
    CargoDTO actualizar(Short id, ActualizarCargoRequest req);
   
    void eliminar(Short id);
    
    CargoDTO cambiarEstado(Short id);
}
