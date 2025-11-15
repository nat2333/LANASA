package co.edu.unbosque.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.FacturaCompra;

public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, Integer> {
    boolean existsByNumero(String numero);
    Optional<FacturaCompra> findByNumero(String numero);

    List<FacturaCompra> findByOrdenCompra_IdOrdenCompra(Integer idOrdenCompra);
    List<FacturaCompra> findByEstadoFactura_IdEstadoFactura(Short idEstadoFactura);
    List<FacturaCompra> findByFechaFacturaBetween(LocalDateTime desde, LocalDateTime hasta);
}
