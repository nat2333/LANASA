package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.DepartamentoDTOs.*;
import co.edu.unbosque.dto.DepartamentoEstadisticasDTO;

public interface DepartamentoServiceAPI {
	DepartamentoDTO crear(CrearDepartamentoRequest req);
	
    DepartamentoDTO obtener(Integer id);
    
    List<DepartamentoDTO> listar();
    
    DepartamentoDTO actualizar(Integer id, ActualizarDepartamentoRequest req);
    
    void eliminar(Integer id);
    
    DepartamentoDTO cambiarEstado(Integer id);
    
    List<DepartamentoEstadisticasDTO> obtenerNomina();
}
