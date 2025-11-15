package co.edu.unbosque.service.api;

import java.time.LocalDateTime;
import java.util.List;
import co.edu.unbosque.dto.TransaccionDTOs.*;

public interface TransaccionServiceAPI {
    TransaccionDTO crear(CrearTransaccionRequest req);
    TransaccionDTO obtener(Integer id);
    List<TransaccionDTO> listar();
    List<TransaccionDTO> listarPorFactura(Integer idFacturaVenta);
    List<TransaccionDTO> listarEntreFechas(LocalDateTime desde, LocalDateTime hasta);
    TransaccionDTO actualizar(Integer id, ActualizarTransaccionRequest req);
    TransaccionDTO cambiarEstado(Integer id);
    void eliminar(Integer id);
}
