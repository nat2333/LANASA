package co.edu.unbosque.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	boolean existsByTelefono(String telefono);
   
	boolean existsByCorreo(String correo);

    @EntityGraph(attributePaths = { "tipoCliente" })
    Optional<Cliente> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = { "tipoCliente" })
    List<Cliente> findAll();
    
    @EntityGraph(attributePaths = { "tipoCliente" })
    List<Cliente> findByTipoCliente_IdTipoCliente(Short idTipoCliente);
}
