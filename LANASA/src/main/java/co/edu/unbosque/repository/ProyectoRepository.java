package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProyectoEstadoDepartamento;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProyectoPorCliente;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProyectoPorDepartamento;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProyectosPresupuesto;
import co.edu.unbosque.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>{

	boolean existsByCodigo(String codigo);

	boolean existsByNombre(String nombre);

	Optional<Proyecto> findByCodigo(String codigo);

	@Query(value = """
			SELECT
			    p.id_proyecto                        AS idProyecto,
			    p.codigo                             AS codigo,
			    p.nombre                             AS nombreProyecto,
			    d.nombre                             AS nombreDepartamento,
			    c.correo                             AS correoCliente,
			    tp.tipo_proyecto                     AS tipoProyecto,
			    p.presupuesto_aprobado               AS presupuestoAprobado,
			    p.presupuesto_utilizado              AS presupuestoUtilizado,
			    (p.presupuesto_aprobado - p.presupuesto_utilizado) AS saldoPresupuesto,
			   (p.presupuesto_utilizado / p.presupuesto_aprobado) * 100 AS porcentajeUtilizado,
			    CASE 
			        WHEN p.presupuesto_utilizado > p.presupuesto_aprobado THEN 1 ELSE 0
			    END                                   AS sobrepasa
			FROM proyecto p
			JOIN departamento d   ON d.id_departamento   = p.id_departamento
			JOIN cliente c        ON c.id_cliente        = p.id_cliente
			JOIN tipo_proyecto tp ON tp.id_tipo_proyecto = p.id_tipo_proyecto
			WHERE p.estado = 1
			""",
			nativeQuery = true)
	List<ProyeccionProyectosPresupuesto> obtenerPresupuestos();


	@Query(value = """
			SELECT
			    d.id_departamento                    AS idDepartamento,
			    d.nombre                             AS nombreDepartamento,
			    COUNT(p.id_proyecto)                 AS numeroProyectos,
			    SUM(p.presupuesto_aprobado)          AS presupuestoTotalAprobado,
			    SUM(p.presupuesto_utilizado)         AS presupuestoTotalUtilizado
			FROM departamento d
			JOIN proyecto p ON p.id_departamento = d.id_departamento
			WHERE p.estado = 1
			GROUP BY d.id_departamento, d.nombre
			ORDER BY numeroProyectos DESC
			""",
			nativeQuery = true)
	List<ProyeccionProyectoPorDepartamento> obtenerProyectosPorDepartamento();


	@Query(value = """
			SELECT
			    c.id_cliente                         AS idCliente,
			    c.correo                             AS correoCliente,
			    c.pais                               AS pais,
			    c.ciudad                             AS ciudad,
			    COUNT(p.id_proyecto)                 AS numeroProyectos,
			    SUM(p.presupuesto_aprobado)          AS presupuestoTotalAprobado,
			    SUM(p.presupuesto_utilizado)         AS presupuestoTotalUtilizado
			FROM cliente c
			JOIN proyecto p ON p.id_cliente = c.id_cliente
			WHERE p.estado = 1
			GROUP BY c.id_cliente, c.correo, c.pais, c.ciudad
			ORDER BY presupuestoTotalAprobado DESC
			""",
			nativeQuery = true)
	List<ProyeccionProyectoPorCliente> obtenerProyectosPorCliente();
	
	@Query(value = """
	        SELECT 
	            p.id_proyecto                                    AS idProyecto,
	            p.codigo                                         AS codigo,
	            p.nombre                                         AS nombreProyecto,
	            d.nombre                                         AS nombreDepartamento,
	            p.presupuesto_aprobado                          AS presupuestoAprobado,
	            p.presupuesto_utilizado                         AS presupuestoUtilizado,
	            (p.presupuesto_aprobado 
	                - COALESCE(p.presupuesto_utilizado, 0))     AS diferenciaPresupuesto,
	            CASE
	                WHEN p.presupuesto_utilizado IS NULL THEN 'SIN_EJECUCION'
	                WHEN p.presupuesto_utilizado <= p.presupuesto_aprobado THEN 'EN_PRESUPUESTO'
	                ELSE 'SOBREPRESUPUESTO'
	            END                                             AS estadoPresupuesto,
	            CASE
	                WHEN p.fecha_fin_real IS NULL THEN 'EN_CURSO'
	                WHEN p.fecha_fin_estimada IS NULL THEN 'SIN_FECHA_ESTIMADA'
	                WHEN p.fecha_fin_real <= p.fecha_fin_estimada THEN 'A_TIEMPO'
	                ELSE 'CON_RETRASO'
	            END                                             AS estadoEntrega,
	            CASE 
	                WHEN p.fecha_fin_real IS NULL 
	                     OR p.fecha_fin_estimada IS NULL THEN NULL
	                ELSE DATEDIFF(p.fecha_fin_real, p.fecha_fin_estimada)
	            END                                             AS diasRetraso
	        FROM proyecto p
	        JOIN departamento d 
	              ON d.id_departamento = p.id_departamento
	        WHERE d.id_departamento = :idDepartamento
	        ORDER BY p.fecha_inicio DESC
	        """,
	        nativeQuery = true)
	    List<ProyeccionProyectoEstadoDepartamento> obtenerEstadoProyectosPorDepartamento(@Param("idDepartamento") Integer idDepartamento);
}
