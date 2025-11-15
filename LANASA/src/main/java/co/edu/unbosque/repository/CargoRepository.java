package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Short> {
    
	boolean existsByNombreCargo(String nombreCargo);
    
	Optional<Cargo> findByNombreCargo(String nombreCargo);
}
