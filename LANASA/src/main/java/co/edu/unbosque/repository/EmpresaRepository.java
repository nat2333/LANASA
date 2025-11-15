package co.edu.unbosque.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

	boolean existsByCliente_IdCliente(Integer idCliente);
    
	boolean existsByRut(String rut);

    @EntityGraph(attributePaths = { "cliente", "cliente.tipoCliente" })
    Optional<Empresa> findById(Integer idCliente);

    @Override
    @EntityGraph(attributePaths = { "cliente", "cliente.tipoCliente" })
    List<Empresa> findAll();
}
