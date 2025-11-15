package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.DetalleFacturaVenta;

public interface DetalleFacturaVentaRepository extends JpaRepository<DetalleFacturaVenta, Integer> {
   
	boolean existsByFacturaVenta_IdFacturaVentaAndProducto_IdProducto(Integer idFacturaVenta, Integer idProducto);

	List<DetalleFacturaVenta> findByFacturaVenta_IdFacturaVenta(int idFacturaVenta);

}
