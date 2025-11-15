package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Short> {

	boolean existsByMetodoPago(String metodoPago);
	
    Optional<MetodoPago> findByMetodoPago(String metodoPago);
}
