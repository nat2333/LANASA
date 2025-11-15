package co.edu.unbosque.service.api;

import java.time.LocalDate;
import java.util.List;

import co.edu.unbosque.dto.OrdenCompraDTOs.*;

public interface OrdenCompraServiceAPI {

	OrdenCompraDTO crear(CrearOrdenCompraRequest req);
    
	OrdenCompraDTO obtener(Integer id);
    
	List<OrdenCompraDTO> listar();
    
	OrdenCompraDTO actualizar(Integer id, ActualizarOrdenCompraRequest req);
    
	void eliminar(Integer id);
    
	OrdenCompraDTO cambiarEstado(Integer id);

    List<OrdenCompraDTO> listarPorProveedor(Integer idProveedor);
    
    List<OrdenCompraDTO> listarPorRangoFechas(LocalDate desde, LocalDate hasta);
}
