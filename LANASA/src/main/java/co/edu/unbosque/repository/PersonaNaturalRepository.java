package co.edu.unbosque.repository;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.PersonaNatural;

@Repository
public interface PersonaNaturalRepository extends JpaRepository<PersonaNatural, Integer>{

	boolean existsByCedula(String cedula);

    @EntityGraph(attributePaths = { "cliente", "cliente.tipoCliente" })
    Optional<PersonaNatural> findById(Integer idCliente);

    @Override
    @EntityGraph(attributePaths = { "cliente", "cliente.tipoCliente" })
    List<PersonaNatural> findAll();

    boolean existsByCliente_IdCliente(Integer idCliente);
}
