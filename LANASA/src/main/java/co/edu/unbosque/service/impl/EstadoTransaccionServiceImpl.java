package co.edu.unbosque.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EstadoTransaccionDTOs.*;
import co.edu.unbosque.entity.EstadoTransaccion;
import co.edu.unbosque.repository.EstadoTransaccionRepository;
import co.edu.unbosque.service.api.EstadoTransaccionServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class EstadoTransaccionServiceImpl
        extends GenericServiceImpl<EstadoTransaccion, Short>
        implements EstadoTransaccionServiceAPI {

    private final EstadoTransaccionRepository rep;

    public EstadoTransaccionServiceImpl(EstadoTransaccionRepository rep) {
        this.rep = rep;
    }

    @Override public JpaRepository<EstadoTransaccion, Short> getDao() { return rep; }

    private EstadoTransaccion mustGet(Short id) {
        return rep.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe EstadoTransaccion id=" + id));
    }

    private EstadoTransaccionDTO toDTO(EstadoTransaccion e) {
        return new EstadoTransaccionDTO(e.getIdEstadoTransaccion(), e.getNombre(), e.getEstado());
    }

    @Override
    public EstadoTransaccionDTO crear(CrearEstadoTransaccionRequest req) {
        if (rep.existsByNombre(req.nombre()))
            throw new IllegalArgumentException("Ya existe un EstadoTransaccion con nombre=" + req.nombre());

        var e = new EstadoTransaccion();
        e.setNombre(req.nombre().trim());
        e.setEstado(true);

        return toDTO(rep.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoTransaccionDTO obtener(Short id) {
        return toDTO(mustGet(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoTransaccionDTO> listar() {
        return rep.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EstadoTransaccionDTO actualizar(Short id, ActualizarEstadoTransaccionRequest req) {
        var e = mustGet(id);

        if (req.nombre() != null) {
            var nuevo = req.nombre().trim();
            if (!nuevo.equalsIgnoreCase(e.getNombre()) && rep.existsByNombre(nuevo))
                throw new IllegalArgumentException("Nombre ya usado: " + nuevo);
            e.setNombre(nuevo);
        }
        if (req.estado() != null) e.setEstado(req.estado());

        return toDTO(rep.save(e));
    }

    @Override
    public EstadoTransaccionDTO cambiarEstado(Short id) {
        var e = mustGet(id);
        e.setEstado(!e.getEstado());
        return toDTO(rep.save(e));
    }

    @Override
    public void eliminar(Short id) {
        // Si más adelante hay FKs a EstadoTransaccion, aquí convendría validar uso antes de eliminar.
        rep.deleteById(id);
    }
}
