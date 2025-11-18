package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.CategoriaUtilidadDTO;
import co.edu.unbosque.dto.ProductoDTOs.*;
import co.edu.unbosque.dto.ProductoUtilidadDTO;

public interface ProductoServiceAPI {

	ProductoDTO crear(CrearProductoRequest req);
    
	ProductoDTO obtener(Integer id);
    
	List<ProductoDTO> listar();
    
	ProductoDTO actualizar(Integer id, ActualizarProductoRequest req);
    
	void eliminar(Integer id);
    
	ProductoDTO cambiarEstado(Integer id);
	
	List<ProductoUtilidadDTO> obtenerUtilidadProductos();
	
	List<CategoriaUtilidadDTO> obtenerUtilidadCategoria();
}
