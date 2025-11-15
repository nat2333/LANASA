package co.edu.unbosque.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EmpleadoProyectoDTOs.*;
import co.edu.unbosque.entity.*;
import co.edu.unbosque.repository.*;
import co.edu.unbosque.service.api.EmpleadoProyectoServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class EmpleadoProyectoServiceImpl
        extends GenericServiceImpl<EmpleadoProyecto, Integer>
        implements EmpleadoProyectoServiceAPI {

    private final EmpleadoProyectoRepository rep;
    private final EmpleadoRepository empRep;
    private final ProyectoRepository proyRep;
    private final RolEmpleadoRepository rolRep;

    public EmpleadoProyectoServiceImpl(EmpleadoProyectoRepository rep,
                                       EmpleadoRepository empRep,
                                       ProyectoRepository proyRep,
                                       RolEmpleadoRepository rolRep) {
        this.rep = rep; this.empRep = empRep; this.proyRep = proyRep; this.rolRep = rolRep;
    }

    @Override public JpaRepository<EmpleadoProyecto, Integer> getDao() { return rep; }

    private EmpleadoProyecto mustGet(Integer id){
        return rep.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe Empleado_Proyecto id=" + id));
    }

    private EmpleadoProyectoDTO toDTO(EmpleadoProyecto ep){
        var e = ep.getEmpleado();
        var p = ep.getProyecto();
        var r = ep.getRolEmpleado();
        String nombreEmpleado = (e != null)
                ? (e.getPrimerNombre() + " " + e.getPrimerApellido())
                : null;
        return new EmpleadoProyectoDTO(
            ep.getIdEmpleadoProyecto(),
            e != null ? e.getIdEmpleado() : null,
            nombreEmpleado,
            p != null ? p.getIdProyecto() : null,
            p != null ? p.getCodigo() : null,
            p != null ? p.getNombre() : null,
            r != null ? r.getIdRolEmpleado() : null,
            r != null ? r.getRolEmpleado() : null,
            ep.getFechaInicio(),
            ep.getFechaFin(),
            ep.getHorasTrabajadas(),
            true
        );
    }

    @Override
    public EmpleadoProyectoDTO crear(CrearEmpleadoProyectoRequest req) {
        var e = empRep.findById(req.idEmpleado())
            .orElseThrow(() -> new ResourceNotFoundException("Empleado no existe: " + req.idEmpleado()));
        var p = proyRep.findById(req.idProyecto())
            .orElseThrow(() -> new ResourceNotFoundException("Proyecto no existe: " + req.idProyecto()));
        var r = rolRep.findById(req.idRol())
            .orElseThrow(() -> new ResourceNotFoundException("RolEmpleado no existe: " + req.idRol()));

        // Evitar duplicado activo Empleado-Proyecto
        if (rep.existsByEmpleado_IdEmpleadoAndProyecto_IdProyectoAndEstadoTrue(e.getIdEmpleado(), p.getIdProyecto())) {
            throw new IllegalArgumentException("Ya existe una asignación ACTIVA para este empleado en el proyecto.");
        }

        var ep = new EmpleadoProyecto();
        ep.setEmpleado(e);
        ep.setProyecto(p);
        ep.setRolEmpleado(r);
        ep.setFechaInicio(req.fechaInicio());
        ep.setFechaFin(req.fechaFin());
        ep.setHorasTrabajadas(req.horasTrabajadas());
        ep.setEstado(true);

        return toDTO(rep.save(ep));
    }

    @Override @Transactional(readOnly = true)
    public EmpleadoProyectoDTO obtener(Integer id) { return toDTO(mustGet(id)); }

    @Override @Transactional(readOnly = true)
    public List<EmpleadoProyectoDTO> listar() {
        return rep.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmpleadoProyectoDTO> listarActivos() {
        return rep.findByEstadoTrue().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmpleadoProyectoDTO> listarPorProyecto(Integer idProyecto) {
        return rep.findByProyecto_IdProyecto(idProyecto).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmpleadoProyectoDTO> listarPorEmpleado(Integer idEmpleado) {
        return rep.findByEmpleado_IdEmpleado(idEmpleado).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<EmpleadoProyectoDTO> listarPorFechaInicio(LocalDate desde, LocalDate hasta) {
        return rep.findByFechaInicioBetween(desde, hasta).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoProyectoDTO actualizar(Integer id, ActualizarEmpleadoProyectoRequest req) {
        var ep = mustGet(id);
        if (req.idRol() != null) {
            var r = rolRep.findById(req.idRol())
                .orElseThrow(() -> new ResourceNotFoundException("RolEmpleado no existe: " + req.idRol()));
            ep.setRolEmpleado(r);
        }
        if (req.fechaInicio() != null) ep.setFechaInicio(req.fechaInicio());
        if (req.fechaFin() != null) ep.setFechaFin(req.fechaFin());
        if (req.horasTrabajadas() != null) ep.setHorasTrabajadas(req.horasTrabajadas());
        if (req.estado() != null) ep.setEstado(req.estado());

        return toDTO(rep.save(ep));
    }

    @Override
    public EmpleadoProyectoDTO cambiarEstado(Integer id) {
        var ep = mustGet(id);
        ep.setEstado(!ep.getEstado());
        return toDTO(rep.save(ep));
    }

    @Override
    public void eliminar(Integer id) { rep.deleteById(id); }
}
