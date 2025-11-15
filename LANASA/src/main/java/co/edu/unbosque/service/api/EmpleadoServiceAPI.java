package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.EmpleadoDTOs.*;
import co.edu.unbosque.entity.Empleado;

public interface EmpleadoServiceAPI {

	EmpleadoDTO crear(CrearEmpleadoRequest req);
	
	EmpleadoDTO obtener(Integer id);
	
	List<EmpleadoDTO> listar();
	
	EmpleadoDTO actualizar(Integer id, ActualizarEmpleadoRequest req);
	
	void eliminar(Integer id);
	
	EmpleadoDTO cambiarEstado(Integer id);
	
	Empleado findByCorreo(String correo);
}
