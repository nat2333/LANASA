package co.edu.unbosque.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.entity.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
    List<Transaccion> findByFacturaVenta_IdFacturaVenta(Integer idFacturaVenta);
    List<Transaccion> findByEstadoTransaccion_IdEstadoTransaccion(Short idEstadoTransaccion);
    List<Transaccion> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);
}
