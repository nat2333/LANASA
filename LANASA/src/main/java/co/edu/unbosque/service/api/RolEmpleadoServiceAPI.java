package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.RolEmpleadoDtos.*;

public interface RolEmpleadoServiceAPI {
    RolEmpleadoDTO crear(CrearRolEmpleadoRequest req);
    RolEmpleadoDTO obtener(Short id);
    List<RolEmpleadoDTO> listar();
    RolEmpleadoDTO actualizar(Short id, ActualizarRolEmpleadoRequest req);
    void eliminar(Short id);
    RolEmpleadoDTO cambiarEstado(Short id);
}
