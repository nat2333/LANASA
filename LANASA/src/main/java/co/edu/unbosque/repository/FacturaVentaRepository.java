package co.edu.unbosque.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.entity.FacturaVenta;

public interface FacturaVentaRepository extends JpaRepository<FacturaVenta, Integer>{

	List<FacturaVenta> findByCliente_IdCliente(Integer idCliente);
	
    List<FacturaVenta> findByProyecto_IdProyecto(Integer idProyecto);
	
}
