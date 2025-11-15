package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ProveedorProductoDTOs.*;

public interface ProveedorProductoServiceAPI {

	ProveedorProductoDTO crear(CrearProveedorProductoRequest req);
    
	ProveedorProductoDTO obtener(Integer id);
    
	List<ProveedorProductoDTO> listar();
    
	ProveedorProductoDTO actualizar(Integer id, ActualizarProveedorProductoRequest req);
    
	void eliminar(Integer id);
    
	ProveedorProductoDTO cambiarEstado(Integer id);
    
	List<ProveedorProductoDTO> listarPorProducto(Integer idProducto);
    
	List<ProveedorProductoDTO> listarPorProveedor(Integer idProveedor);
}
