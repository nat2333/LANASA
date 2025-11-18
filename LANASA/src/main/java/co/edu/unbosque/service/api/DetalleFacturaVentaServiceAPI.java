package co.edu.unbosque.service.api;

import java.util.List;
import co.edu.unbosque.dto.DetalleFacturaVentaDTOs.*;
import co.edu.unbosque.dto.ProductoMasVendidoDTO;

public interface DetalleFacturaVentaServiceAPI {
   
	DetalleFacturaVentaDTO crear(CrearDetalleFacturaVentaRequest req);
    
    DetalleFacturaVentaDTO obtener(Integer id);
    
    List<DetalleFacturaVentaDTO> listarPorFactura(Integer idFacturaVenta);
    
    DetalleFacturaVentaDTO actualizar(Integer id, ActualizarDetalleFacturaVentaRequest req);
    
    void eliminar(Integer id);
    
    DetalleFacturaVentaDTO cambiarEstado(Integer id);
    
    List<ProductoMasVendidoDTO> obtenerProductosMasVendidos();

}
