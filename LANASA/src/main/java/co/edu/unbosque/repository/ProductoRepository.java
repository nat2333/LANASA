package co.edu.unbosque.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.unbosque.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	boolean existsBySku(String sku);
    
	Optional<Producto> findBySku(String sku);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    List<Producto> findByCategoriaIgnoreCase(String categoria);
    
    List<Producto> findByEstado(Boolean estado);
}
