package co.edu.unbosque.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.DetalleOrdenCompra;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Integer> {

	boolean existsByOrdenCompra_IdOrdenCompraAndProducto_IdProducto(Integer idOrdenCompra, Integer idProducto);

    Optional<DetalleOrdenCompra> findByOrdenCompra_IdOrdenCompraAndProducto_IdProducto(Integer idOrdenCompra, Integer idProducto);

    List<DetalleOrdenCompra> findByOrdenCompra_IdOrdenCompra(Integer idOrdenCompra);

    List<DetalleOrdenCompra> findByProducto_IdProducto(Integer idProducto);
}
