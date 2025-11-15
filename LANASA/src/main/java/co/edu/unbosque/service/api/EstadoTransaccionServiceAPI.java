package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.EstadoTransaccionDTOs.*;

public interface EstadoTransaccionServiceAPI {
    EstadoTransaccionDTO crear(CrearEstadoTransaccionRequest req);
    EstadoTransaccionDTO obtener(Short id);
    List<EstadoTransaccionDTO> listar();
    EstadoTransaccionDTO actualizar(Short id, ActualizarEstadoTransaccionRequest req);
    EstadoTransaccionDTO cambiarEstado(Short id);
    void eliminar(Short id);
}
