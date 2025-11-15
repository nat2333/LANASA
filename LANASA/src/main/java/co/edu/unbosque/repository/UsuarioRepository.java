package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@EntityGraph(attributePaths = "tipoUsuario")
	Optional<Usuario> findByLogin(String login);
	
	boolean existsByLogin(String login);
}
