package co.edu.unbosque.service.api;

import java.time.LocalDateTime;
import java.util.List;
import co.edu.unbosque.dto.FacturaCompraDTOs.*;

public interface FacturaCompraServiceAPI {
    FacturaCompraDTO crear(CrearFacturaCompraRequest req);
    
    FacturaCompraDTO obtener(Integer id);
    
    List<FacturaCompraDTO> listar();
    
    FacturaCompraDTO actualizar(Integer id, ActualizarFacturaCompraRequest req);
    
    void eliminar(Integer id);
    
    FacturaCompraDTO cambiarEstado(Integer id);

    List<FacturaCompraDTO> listarPorOrden(Integer idOrdenCompra);
    
    List<FacturaCompraDTO> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);
}
