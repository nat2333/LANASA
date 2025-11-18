package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionTopCliente;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionVentasMensuales;
import co.edu.unbosque.entity.FacturaVenta;

public interface FacturaVentaRepository extends JpaRepository<FacturaVenta, Integer>{

	List<FacturaVenta> findByCliente_IdCliente(Integer idCliente);

	List<FacturaVenta> findByProyecto_IdProyecto(Integer idProyecto);

	@Query(value = """
			SELECT
			    YEAR(f.fecha_factura_venta)  AS anio,
			    MONTH(f.fecha_factura_venta) AS mes,
			    SUM(f.total)                 AS totalVentas,
			    COUNT(*)                     AS numeroFacturas
			FROM factura_venta f
			WHERE f.estado = 1
			GROUP BY YEAR(f.fecha_factura_venta), MONTH(f.fecha_factura_venta)
			ORDER BY anio, mes
			""",
			nativeQuery = true)
	List<ProyeccionVentasMensuales> obtenerVentasMensuales();

	@Query(value = """
			SELECT
			    c.correo                        AS correo,
			    c.telefono                      AS telefono,
			    c.pais                          AS pais,
			    c.ciudad                        AS ciudad,
			    SUM(f.total)                    AS totalComprado,
			    COUNT(DISTINCT f.id_factura_venta) AS numeroFacturas
			FROM cliente c
			JOIN factura_venta f 
			      ON f.id_cliente = c.id_cliente
			     AND f.estado = 1
			WHERE c.estado = 1
			GROUP BY c.id_cliente, c.correo, c.telefono, c.pais, c.ciudad
			ORDER BY totalComprado DESC
			""",
			nativeQuery = true)
	List<ProyeccionTopCliente> obtenerTopClientes();
}
