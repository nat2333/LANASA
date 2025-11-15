package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    
	boolean existsByCodigo(String codigo);
    
	Optional<Departamento> findByCodigo(String codigo);
    
	boolean existsByNombre(String nombre);
}
