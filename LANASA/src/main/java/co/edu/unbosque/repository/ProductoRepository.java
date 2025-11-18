package co.edu.unbosque.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionUtilidadCategoria;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionUtilidadProducto;
import co.edu.unbosque.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	boolean existsBySku(String sku);

	Optional<Producto> findBySku(String sku);

	List<Producto> findByNombreContainingIgnoreCase(String nombre);

	List<Producto> findByCategoriaIgnoreCase(String categoria);

	List<Producto> findByEstado(Boolean estado);

	@Query(value = """
			SELECT 
			    p.sku AS sku,
			    p.nombre AS nombre,
			    p.categoria AS categoria,
			    p.precio_compra AS precioCompra,
			    p.precio_venta_sugerido AS precioVentaSugerido,

			    AVG(dfv.precio_unitario) AS precioVentaPromedio,

			    (AVG(dfv.precio_unitario) - p.precio_compra) AS utilidadReal,
			    ((AVG(dfv.precio_unitario) - p.precio_compra) / p.precio_compra) * 100 AS utilidadRealPorcentaje,

			    (p.precio_venta_sugerido - p.precio_compra) AS utilidadPotencial,
			    ((p.precio_venta_sugerido - p.precio_compra) / p.precio_compra) * 100 AS utilidadPotencialPorcentaje

			FROM producto p
			LEFT JOIN detalle_factura_venta dfv
			       ON dfv.id_producto = p.id_producto AND dfv.estado = 1
			WHERE p.estado = 1
			GROUP BY 
			    p.id_producto, p.sku, p.nombre, p.categoria,
			    p.precio_compra, p.precio_venta_sugerido
			ORDER BY utilidadRealPorcentaje DESC
			""",
			nativeQuery = true)
	List<ProyeccionUtilidadProducto> obtenerUtilidadProductos();

	@Query(value = """
			SELECT
			    p.categoria AS categoria,
			    COUNT(p.id_producto) AS cantidadProductos,
			    AVG(p.precio_compra) AS precioCompraPromedio,
			    AVG(p.precio_venta_sugerido) AS precioVentaSugeridoPromedio,
			    AVG(p.precio_venta_sugerido - p.precio_compra) AS utilidadPromedio,
			    ((AVG(p.precio_venta_sugerido) - AVG(p.precio_compra)) / AVG(p.precio_compra)) * 100 AS utilidadPorcentajePromedio
			FROM producto p
			WHERE p.estado = 1
			  AND p.precio_venta_sugerido IS NOT NULL
			GROUP BY p.categoria
			ORDER BY utilidadPorcentajePromedio DESC
			""",
			nativeQuery = true)
	List<ProyeccionUtilidadCategoria> obtenerUtilidadCategoria();
}


