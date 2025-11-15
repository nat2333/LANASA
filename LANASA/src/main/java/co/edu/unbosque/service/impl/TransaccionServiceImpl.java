package co.edu.unbosque.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.TransaccionDTOs.*;
import co.edu.unbosque.entity.*;
import co.edu.unbosque.repository.*;
import co.edu.unbosque.service.api.TransaccionServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class TransaccionServiceImpl
        extends GenericServiceImpl<Transaccion, Integer>
        implements TransaccionServiceAPI {

    private final TransaccionRepository rep;
    private final FacturaVentaRepository facRep;
    private final MetodoPagoRepository mpRep;
    private final EstadoTransaccionRepository etRep;

    public TransaccionServiceImpl(TransaccionRepository rep,
                                  FacturaVentaRepository facRep,
                                  MetodoPagoRepository mpRep,
                                  EstadoTransaccionRepository etRep) {
        this.rep = rep; this.facRep = facRep; this.mpRep = mpRep; this.etRep = etRep;
    }

    @Override public JpaRepository<Transaccion, Integer> getDao() { return rep; }

    private Transaccion mustGet(Integer id){
        return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Transaccion id=" + id));
    }

    private TransaccionDTO toDTO(Transaccion t){
        var fv = t.getFacturaVenta();
        var mp = t.getMetodoPago();
        var est = t.getEstadoTransaccion();
        return new TransaccionDTO(
            t.getIdTransaccion(),
            fv != null ? fv.getIdFacturaVenta() : null,
            fv != null ? fv.getNumero() : null,
            mp != null ? mp.getIdMetodoPago() : null,
            mp != null ? mp.getMetodoPago() : null,
            est != null ? est.getIdEstadoTransaccion() : null,
            est != null ? est.getNombre() : null,
            t.getValor(),
            t.getFechaHora(),
            t.getEstado()
        );
    }

    @Override
    public TransaccionDTO crear(CrearTransaccionRequest req) {
        var fv = facRep.findById(req.idFacturaVenta())
                .orElseThrow(() -> new ResourceNotFoundException("FacturaVenta no existe: " + req.idFacturaVenta()));
        var mp = mpRep.findById(req.idMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no existe: " + req.idMetodoPago()));
        var est = etRep.findById(req.idEstadoTransaccion())
                .orElseThrow(() -> new ResourceNotFoundException("EstadoTransaccion no existe: " + req.idEstadoTransaccion()));

        var t = new Transaccion();
        t.setFacturaVenta(fv);
        t.setMetodoPago(mp);
        t.setEstadoTransaccion(est);
        t.setValor(req.valor());
        t.setFechaHora(req.fechaHora() != null ? req.fechaHora() : LocalDateTime.now());
        t.setEstado(true);

        return toDTO(rep.save(t));
    }

    @Override
    @Transactional(readOnly = true)
    public TransaccionDTO obtener(Integer id) { return toDTO(mustGet(id)); }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> listar() {
        return rep.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> listarPorFactura(Integer idFacturaVenta) {
        return rep.findByFacturaVenta_IdFacturaVenta(idFacturaVenta)
                  .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionDTO> listarEntreFechas(LocalDateTime desde, LocalDateTime hasta) {
        return rep.findByFechaHoraBetween(desde, hasta)
                  .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TransaccionDTO actualizar(Integer id, ActualizarTransaccionRequest req) {
        var t = mustGet(id);

        if (req.idMetodoPago() != null) {
            var mp = mpRep.findById(req.idMetodoPago())
                    .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no existe: " + req.idMetodoPago()));
            t.setMetodoPago(mp);
        }
        if (req.idEstadoTransaccion() != null) {
            var est = etRep.findById(req.idEstadoTransaccion())
                    .orElseThrow(() -> new ResourceNotFoundException("EstadoTransaccion no existe: " + req.idEstadoTransaccion()));
            t.setEstadoTransaccion(est);
        }
        if (req.valor() != null) t.setValor(req.valor());
        if (req.fechaHora() != null) t.setFechaHora(req.fechaHora());
        if (req.estado() != null) t.setEstado(req.estado());

        return toDTO(rep.save(t));
    }

    @Override
    public TransaccionDTO cambiarEstado(Integer id) {
        var t = mustGet(id);
        t.setEstado(!t.getEstado());
        return toDTO(rep.save(t));
    }

    @Override
    public void eliminar(Integer id) {
        rep.deleteById(id);
    }
}
