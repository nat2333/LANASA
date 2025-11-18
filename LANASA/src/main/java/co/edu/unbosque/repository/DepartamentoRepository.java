package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionDepartamentoEstadistica;
import co.edu.unbosque.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    
	boolean existsByCodigo(String codigo);
    
	Optional<Departamento> findByCodigo(String codigo);
    
	boolean existsByNombre(String nombre);

	@Query(value = """
			SELECT 
			    d.nombre AS nombreDepartamento,
			    d.codigo AS codigo,
			    COUNT(e.id_empleado) AS cantidadEmpleados,
			    COALESCE(SUM(e.salario), 0) AS nominaTotal,
			    d.presupuesto_anual AS presupuestoAnual,
			    d.presupuesto_anual - COALESCE(SUM(e.salario), 0) AS diferencia
			FROM departamento d
			LEFT JOIN empleado e 
			       ON e.id_departamento = d.id_departamento 
			      AND e.estado = 1
			GROUP BY d.id_departamento, d.nombre, d.codigo, d.presupuesto_anual
			ORDER BY d.nombre
			""",
			nativeQuery = true)
	List<ProyeccionDepartamentoEstadistica> obtenerEstadisticasDepartamentos();
}
