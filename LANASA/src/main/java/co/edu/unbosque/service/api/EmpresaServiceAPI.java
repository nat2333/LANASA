package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.EmpresaDTOs.*;

public interface EmpresaServiceAPI {

	EmpresaDTO crear(CrearEmpresaRequest req);
	
	EmpresaDTO obtener(Integer idCliente);
	
	List<EmpresaDTO> listar();
	
	EmpresaDTO actualizar(Integer idCliente, ActualizarEmpresaRequest req);
	
	void eliminar(Integer idCliente);
	
	EmpresaDTO cambiarEstado(Integer idCliente);
}
