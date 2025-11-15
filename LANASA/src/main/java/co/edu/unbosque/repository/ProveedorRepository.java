package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.unbosque.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

	boolean existsByRut(String rut);
    
	Optional<Proveedor> findByRut(String rut);
    
	List<Proveedor> findByNombreComercialContainingIgnoreCase(String nombre);
    
	List<Proveedor> findByEstado(Boolean estado);
}
