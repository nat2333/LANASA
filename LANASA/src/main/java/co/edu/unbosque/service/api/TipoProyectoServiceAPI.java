package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.TipoProyectoDtos.*;

public interface TipoProyectoServiceAPI {
    TipoProyectoDTO crear(CrearTipoProyectoRequest req);
    TipoProyectoDTO obtener(Short id);
    List<TipoProyectoDTO> listar();
    void eliminar(Short id);
    TipoProyectoDTO cambiarEstado(Short id);
}
