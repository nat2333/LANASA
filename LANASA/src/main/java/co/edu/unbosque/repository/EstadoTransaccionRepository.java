package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.EstadoTransaccion;

public interface EstadoTransaccionRepository extends JpaRepository<EstadoTransaccion, Short> {
    boolean existsByNombre(String nombre);
    Optional<EstadoTransaccion> findByNombreIgnoreCase(String nombre);
}
