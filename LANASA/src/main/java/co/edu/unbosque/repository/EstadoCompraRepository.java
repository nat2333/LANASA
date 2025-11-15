package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.EstadoCompra;

public interface EstadoCompraRepository extends JpaRepository<EstadoCompra, Short> {

	boolean existsByEstadoCompra(String estadoCompra);
    
	Optional<EstadoCompra> findByEstadoCompra(String estadoCompra);
}
