package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Historial;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer>{

	@EntityGraph(attributePaths = { "empleado", "departamento", "cargo" })
    Optional<Historial> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = { "empleado", "departamento", "cargo" })
    List<Historial> findAll();

    List<Historial> findByEmpleado_IdEmpleadoAndEstadoTrue(Integer idEmpleado);
    List<Historial> findByEmpleado_IdEmpleadoOrderByFechaInicioDesc(Integer idEmpleado);

    boolean existsByEmpleado_IdEmpleadoAndEstadoTrue(Integer idEmpleado);
}
