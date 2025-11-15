package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.DepartamentoDTOs.*;

public interface DepartamentoServiceAPI {
	DepartamentoDTO crear(CrearDepartamentoRequest req);
	
    DepartamentoDTO obtener(Integer id);
    
    List<DepartamentoDTO> listar();
    
    DepartamentoDTO actualizar(Integer id, ActualizarDepartamentoRequest req);
    
    void eliminar(Integer id);
    
    DepartamentoDTO cambiarEstado(Integer id);
}
