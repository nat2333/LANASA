package co.edu.unbosque.repository;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	boolean existsByCedula(String cedula);
    
	boolean existsByCorreo(String correo); 
	
	Optional<Empleado> findByCorreo(String correo);

    @EntityGraph(attributePaths = { "cargo", "tipoContrato", "departamento" })
    Optional<Empleado> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = { "cargo", "tipoContrato", "departamento" })
    List<Empleado> findAll();
	
}
