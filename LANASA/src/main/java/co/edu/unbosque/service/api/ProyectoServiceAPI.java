package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ProyectoDtos.*;
import co.edu.unbosque.dto.ProyectoEstadoDepartamentoDTO;
import co.edu.unbosque.dto.ProyectoPorClienteDTO;
import co.edu.unbosque.dto.ProyectoPorDepartamentoDTO;
import co.edu.unbosque.dto.ProyectoPresupuestoDTO;

public interface ProyectoServiceAPI {

	ProyectoDTO crear(CrearProyectoRequest req);
    ProyectoDTO obtener(Integer id);
    List<ProyectoDTO> listar();
    ProyectoDTO actualizar(Integer id, ActualizarProyectoRequest req);
    void eliminar(Integer id);
    ProyectoDTO cambiarEstado(Integer id);

    List<ProyectoPresupuestoDTO> obtenerPresupuestos();

    List<ProyectoPorDepartamentoDTO> obtenerProyectosPorDepartamento();

    List<ProyectoPorClienteDTO> obtenerProyectosPorCliente();
    
    List<ProyectoEstadoDepartamentoDTO> obtenerEstadoProyectosPorDepartamento(Integer idDepartamento);

}
