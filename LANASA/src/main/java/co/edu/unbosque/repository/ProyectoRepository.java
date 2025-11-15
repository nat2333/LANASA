package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>{

	boolean existsByCodigo(String codigo);
	
	boolean existsByNombre(String nombre);
	
	Optional<Proyecto> findByCodigo(String codigo);
}
