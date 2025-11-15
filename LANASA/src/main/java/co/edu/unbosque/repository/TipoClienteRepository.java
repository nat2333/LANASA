package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.entity.TipoCliente;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Short>{

	boolean existsByTipo(String tipo);
    
	Optional<TipoCliente> findByTipo(String tipo);
}
