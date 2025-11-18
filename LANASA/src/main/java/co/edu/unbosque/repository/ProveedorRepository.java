package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionCalificacionPrecio;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorCalificacion;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorProducto;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorPuntualidad;
import co.edu.unbosque.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

	boolean existsByRut(String rut);

	Optional<Proveedor> findByRut(String rut);

	List<Proveedor> findByNombreComercialContainingIgnoreCase(String nombre);

	List<Proveedor> findByEstado(Boolean estado);

	@Query(value = """
			SELECT 
			    p.nombre_comercial AS nombreComercial,
			    p.calificacion AS calificacion
			FROM proveedor p
			WHERE p.estado = 1
			ORDER BY p.calificacion DESC, p.nombre_comercial ASC
			LIMIT 5
			""",
			nativeQuery = true)
	List<ProyeccionProveedorCalificacion> obtenerProveedoresPorCalificacion();


	@Query(value = """
			SELECT 
			    pr.nombre_comercial AS nombreComercial,
			    pr.calificacion AS calificacion,
			    p.sku AS skuProducto,
			    p.nombre AS nombreProducto,
			    AVG(doc.precio_unitario) AS precioPromedioCompra,
			    MIN(doc.precio_unitario) AS precioMinimoCompra,
			    MAX(doc.precio_unitario) AS precioMaximoCompra,
			    COUNT(DISTINCT oc.id_orden_compra) AS numeroOrdenes
			FROM proveedor pr
			JOIN orden_compra oc 
			      ON oc.id_proveedor = pr.id_proveedor AND oc.estado = 1
			JOIN detalle_orden_compra doc 
			      ON doc.id_orden_compra = oc.id_orden_compra AND doc.estado = 1
			JOIN producto p 
			      ON p.id_producto = doc.id_producto
			WHERE p.id_producto = :idProducto
			GROUP BY pr.nombre_comercial, pr.calificacion, p.sku, p.nombre
			ORDER BY precioPromedioCompra ASC
			LIMIT 3
			""",
			nativeQuery = true)
	List<ProyeccionProveedorProducto> obtenerMejoresProveedoresPorProducto(@Param("idProducto") Integer idProducto);

	@Query(value = """
			SELECT 
			    pr.nombre_comercial AS nombreComercial,
			    pr.calificacion AS calificacion,
			    COUNT(oc.id_orden_compra) AS totalOrdenes,
			    AVG(DATEDIFF(oc.fecha_entrega_real, oc.fecha_entrega_esperada)) AS promedioDiasRetraso,
			    SUM(
			        CASE 
			            WHEN DATEDIFF(oc.fecha_entrega_real, oc.fecha_entrega_esperada) <= 0 
			            THEN 1 ELSE 0 
			        END
			    ) AS ordenesATiempo
			FROM proveedor pr
			JOIN orden_compra oc 
			      ON oc.id_proveedor = pr.id_proveedor
			     AND oc.estado = 1
			WHERE oc.fecha_entrega_real IS NOT NULL
			  AND oc.fecha_entrega_esperada IS NOT NULL
			GROUP BY pr.id_proveedor, pr.nombre_comercial, pr.calificacion
			ORDER BY promedioDiasRetraso ASC
			""",
			nativeQuery = true)
	List<ProyeccionProveedorPuntualidad> obtenerPuntualidadProveedores();

	@Query(value = """
			SELECT 
			    pr.nombre_comercial AS nombreComercial,
			    pr.calificacion AS calificacion,
			    p.sku AS skuProducto,
			    p.nombre AS nombreProducto,
			    AVG(doc.precio_unitario) AS precioPromedioCompra,
			    COUNT(DISTINCT oc.id_orden_compra) AS numeroOrdenes,
			    (pr.calificacion / AVG(doc.precio_unitario)) AS relacion
			FROM proveedor pr
			JOIN orden_compra oc 
			      ON oc.id_proveedor = pr.id_proveedor AND oc.estado = 1
			JOIN detalle_orden_compra doc 
			      ON doc.id_orden_compra = oc.id_orden_compra AND doc.estado = 1
			JOIN producto p ON p.id_producto = doc.id_producto
			WHERE p.id_producto = :idProducto
			GROUP BY 
			    pr.id_proveedor, pr.nombre_comercial, pr.calificacion,
			    p.id_producto, p.nombre
			ORDER BY relacion DESC
			""",
			nativeQuery = true)
	List<ProyeccionCalificacionPrecio> obtenerRelacionProveedoresPorProducto(@Param("idProducto") Integer idProducto);


	
}
