package co.edu.unbosque.repository;

import co.edu.unbosque.entity.TipoUsuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Short> {

	Optional<TipoUsuario> findByTipo(String tipo);
    boolean existsByTipo(String tipo);
}
