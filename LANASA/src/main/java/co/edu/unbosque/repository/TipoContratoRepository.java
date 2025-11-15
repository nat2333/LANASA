package co.edu.unbosque.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.TipoContrato;

@Repository
public interface TipoContratoRepository extends JpaRepository<TipoContrato, Short> {

	 boolean existsByNombreTipocontrato(String nombreTipocontrato);
	    Optional<TipoContrato> findByNombreTipocontrato(String nombreTipocontrato);
	    
}
