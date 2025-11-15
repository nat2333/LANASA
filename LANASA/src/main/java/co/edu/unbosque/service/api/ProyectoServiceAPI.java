package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ProyectoDtos.*;

public interface ProyectoServiceAPI {

	ProyectoDTO crear(CrearProyectoRequest req);
    ProyectoDTO obtener(Integer id);
    List<ProyectoDTO> listar();
    ProyectoDTO actualizar(Integer id, ActualizarProyectoRequest req);
    void eliminar(Integer id);
    ProyectoDTO cambiarEstado(Integer id);

    /*List<ProyectoDTO> listarActivos();
    List<ProyectoDTO> porDepartamento(Integer idDepartamento);
    List<ProyectoDTO> porCliente(Integer idCliente);
    List<ProyectoDTO> porTipoProyecto(Short idTipoProyecto);
	*/
}
