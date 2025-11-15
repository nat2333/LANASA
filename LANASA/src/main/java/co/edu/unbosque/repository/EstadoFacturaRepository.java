package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.EstadoFactura;

public interface EstadoFacturaRepository extends JpaRepository<EstadoFactura, Short> {
	
	boolean existsByNombre(String nombre);
	
	Optional<EstadoFactura> findByNombre(String nombre);
}
