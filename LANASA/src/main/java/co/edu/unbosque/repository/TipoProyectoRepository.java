package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.TipoProyecto;

public interface TipoProyectoRepository extends JpaRepository<TipoProyecto, Short> {
    boolean existsByTipoProyectoIgnoreCase(String tipoProyecto);
    Optional<TipoProyecto> findByTipoProyectoIgnoreCase(String tipoProyecto);
}
