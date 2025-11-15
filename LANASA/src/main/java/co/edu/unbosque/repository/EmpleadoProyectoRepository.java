package co.edu.unbosque.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.EmpleadoProyecto;

public interface EmpleadoProyectoRepository extends JpaRepository<EmpleadoProyecto, Integer> {
    List<EmpleadoProyecto> findByProyecto_IdProyecto(Integer idProyecto);
    List<EmpleadoProyecto> findByEmpleado_IdEmpleado(Integer idEmpleado);
    List<EmpleadoProyecto> findByEstadoTrue();
    List<EmpleadoProyecto> findByProyecto_IdProyectoAndEstadoTrue(Integer idProyecto);
    List<EmpleadoProyecto> findByEmpleado_IdEmpleadoAndEstadoTrue(Integer idEmpleado);
    List<EmpleadoProyecto> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);
    boolean existsByEmpleado_IdEmpleadoAndProyecto_IdProyectoAndEstadoTrue(Integer idEmpleado, Integer idProyecto);
}
