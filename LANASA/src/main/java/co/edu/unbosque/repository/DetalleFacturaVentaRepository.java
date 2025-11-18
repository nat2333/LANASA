package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProductoMasVendido;
import co.edu.unbosque.entity.DetalleFacturaVenta;

public interface DetalleFacturaVentaRepository extends JpaRepository<DetalleFacturaVenta, Integer> {
   
	boolean existsByFacturaVenta_IdFacturaVentaAndProducto_IdProducto(Integer idFacturaVenta, Integer idProducto);

	List<DetalleFacturaVenta> findByFacturaVenta_IdFacturaVenta(int idFacturaVenta);

	 @Query(value = """
		        SELECT
		            p.nombre                                    AS nombreProducto,
		            p.categoria                                 AS categoria,
		            SUM(dfv.cantidad)                           AS cantidadVendida,
		            SUM(dfv.cantidad * dfv.precio_unitario)     AS totalVendido
		        FROM producto p
		        JOIN detalle_factura_venta dfv
		              ON dfv.id_producto = p.id_producto
		             AND dfv.estado = 1
		        JOIN factura_venta f
		              ON f.id_factura_venta = dfv.id_factura_venta
		             AND f.estado = 1
		        WHERE p.estado = 1
		        GROUP BY p.id_producto, p.nombre, p.categoria
		        ORDER BY cantidadVendida DESC
		        """,
		        nativeQuery = true)
		    List<ProyeccionProductoMasVendido> obtenerProductosMasVendidos();
}
