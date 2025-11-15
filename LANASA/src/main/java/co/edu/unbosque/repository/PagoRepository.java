package co.edu.unbosque.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import co.edu.unbosque.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByFacturaCompra_IdFacturaCompra(Integer idFacturaCompra);

    List<Pago> findByMetodoPago_IdMetodoPago(Short idMetodoPago);

    List<Pago> findByFechaPagoBetween(LocalDateTime desde, LocalDateTime hasta);

    @Query("select coalesce(sum(p.monto), 0) from Pago p where p.facturaCompra.idFacturaCompra = :idFacturaCompra and p.estado = true")
    BigDecimal totalPagadoFactura(Integer idFacturaCompra);
}
