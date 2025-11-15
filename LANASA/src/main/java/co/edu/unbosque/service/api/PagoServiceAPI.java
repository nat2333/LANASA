package co.edu.unbosque.service.api;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import co.edu.unbosque.dto.PagoDtos.*;

public interface PagoServiceAPI {
    PagoDTO crear(CrearPagoRequest req);
    PagoDTO obtener(Integer id);
    List<PagoDTO> listar();
    void eliminar(Integer id);
    PagoDTO cambiarEstado(Integer id);

    List<PagoDTO> listarPorFactura(Integer idFacturaCompra);
    List<PagoDTO> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    ResumenPagosFacturaDTO resumenPorFactura(Integer idFacturaCompra);

    void validarNoExcedeSaldo(Integer idFacturaCompra, BigDecimal montoNuevo, Integer idPagoAExcluir);
}
