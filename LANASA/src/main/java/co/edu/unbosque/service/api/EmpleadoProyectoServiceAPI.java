package co.edu.unbosque.service.api;

import java.time.LocalDate;
import java.util.List;
import co.edu.unbosque.dto.EmpleadoProyectoDTOs.*;

public interface EmpleadoProyectoServiceAPI {
    EmpleadoProyectoDTO crear(CrearEmpleadoProyectoRequest req);
    EmpleadoProyectoDTO obtener(Integer id);
    List<EmpleadoProyectoDTO> listar();
    List<EmpleadoProyectoDTO> listarActivos();
    List<EmpleadoProyectoDTO> listarPorProyecto(Integer idProyecto);
    List<EmpleadoProyectoDTO> listarPorEmpleado(Integer idEmpleado);
    List<EmpleadoProyectoDTO> listarPorFechaInicio(LocalDate desde, LocalDate hasta);
    EmpleadoProyectoDTO actualizar(Integer id, ActualizarEmpleadoProyectoRequest req);
    EmpleadoProyectoDTO cambiarEstado(Integer id);
    void eliminar(Integer id);
}
