package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.RolEmpleado;

public interface RolEmpleadoRepository extends JpaRepository<RolEmpleado, Short> {
    boolean existsByRolEmpleadoIgnoreCase(String rolEmpleado);
    Optional<RolEmpleado> findByRolEmpleadoIgnoreCase(String rolEmpleado);
}
