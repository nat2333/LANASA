package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.dto.CargoCostoDTO;
import co.edu.unbosque.dto.ContratoDistribucionDTO;
import co.edu.unbosque.dto.EmpleadoSalarioDTO;
import co.edu.unbosque.entity.Empleado;

@Repository
public interface EstadisticasEmpleadoRepository extends JpaRepository<Empleado, Integer> {

	@Query("""
			SELECT new co.edu.unbosque.dto.EmpleadoSalarioDTO(
			  e.primerNombre,
			  e.segundoNombre,
			  e.primerApellido,
			  e.segundoApellido,
			  c.nombreCargo,
			  e.salario
			)
			FROM Empleado e
			JOIN e.cargo c
			WHERE e.estado = true
			ORDER BY e.salario DESC
			""")
	List<EmpleadoSalarioDTO> findTopEmpleadosPorSalario();

	@Query("""
			SELECT new co.edu.unbosque.dto.ContratoDistribucionDTO(
			  tc.nombreTipocontrato,
			  COUNT(e)
			)
			FROM Empleado e
			JOIN e.tipoContrato tc
			WHERE e.estado = true
			GROUP BY tc.nombreTipocontrato
			ORDER BY COUNT(e) DESC
			""")
	List<ContratoDistribucionDTO> findDistribucionPorTipoContrato();

	@Query("""
			SELECT new co.edu.unbosque.dto.CargoCostoDTO(
			  c.nombreCargo,
			  COUNT(e),
			  SUM(e.salario)
			)
			FROM Empleado e
			JOIN e.cargo c
			WHERE e.estado = true
			GROUP BY c.nombreCargo
			ORDER BY SUM(e.salario) DESC
			""")
	List<CargoCostoDTO> findCostoPorCargo();
	
}
