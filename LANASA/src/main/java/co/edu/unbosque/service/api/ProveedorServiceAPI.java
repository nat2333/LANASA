package co.edu.unbosque.service.api;

import java.util.List;

import co.edu.unbosque.dto.ProveedorCalificacionDTO;
import co.edu.unbosque.dto.ProveedorDTOs.*;
import co.edu.unbosque.dto.ProveedorProductoDTO;
import co.edu.unbosque.dto.ProveedorPuntualidadDTO;
import co.edu.unbosque.dto.ProveedorRelacionProductoDTO;

public interface ProveedorServiceAPI {

	ProveedorDTO crear(CrearProveedorRequest req);

	ProveedorDTO obtener(Integer id);

	List<ProveedorDTO> listar();

	ProveedorDTO actualizar(Integer id, ActualizarProveedorRequest req);

	void eliminar(Integer id);

	ProveedorDTO cambiarEstado(Integer id);

	List<ProveedorCalificacionDTO> obtenerTopCalificacion();

	List<ProveedorProductoDTO> obtenerMejoresPrecioProducto(Integer idProducto);

	List<ProveedorPuntualidadDTO> obtenerPuntualidad();

	List<ProveedorRelacionProductoDTO> obtenerRelacionProducto(Integer idProducto);
}
