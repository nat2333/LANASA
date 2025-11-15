package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.DetalleOrdenCompraDTOs.*;

public interface DetalleOrdenCompraServiceAPI {

	DetalleOrdenCompraDTO crear(CrearDetalleOCRequest req);
    
	DetalleOrdenCompraDTO obtener(Integer id);
    
	List<DetalleOrdenCompraDTO> listar();
    
	DetalleOrdenCompraDTO actualizar(Integer id, ActualizarDetalleOCRequest req);
    
	void eliminar(Integer id);
    
	DetalleOrdenCompraDTO cambiarEstado(Integer id);

    List<DetalleOrdenCompraDTO> listarPorOrden(Integer idOrdenCompra);
    
    List<DetalleOrdenCompraDTO> listarPorProducto(Integer idProducto);
}
