package co.edu.unbosque.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.unbosque.entity.OrdenCompra;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

	boolean existsByNumero(String numero);
	
	Optional<OrdenCompra> findByNumero(String numero);

	List<OrdenCompra> findByProveedor_IdProveedor(Integer idProveedor);
	
	List<OrdenCompra> findByEstadoCompra_IdEstadoCompra(Byte idEstadoCompra);
	
	List<OrdenCompra> findByFechaOrdenBetween(LocalDate desde, LocalDate hasta);
	
	List<OrdenCompra> findByEstado(Boolean estado);
}
