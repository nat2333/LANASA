package co.edu.unbosque.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EmpleadoProyectoDTOs.*;
import co.edu.unbosque.service.api.EmpleadoProyectoServiceAPI;

@RestController
@RequestMapping("/EmpleadoProyecto")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoProyectoRestController {

    private final EmpleadoProyectoServiceAPI service;

    public EmpleadoProyectoRestController(EmpleadoProyectoServiceAPI service) {
        this.service = service;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoProyectoDTO crear(@Validated @RequestBody CrearEmpleadoProyectoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoProyectoDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoProyectoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoProyectoDTO> activos() {
        return service.listarActivos();
    }

    @GetMapping("/porProyecto/{idProyecto}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoProyectoDTO> porProyecto(@PathVariable Integer idProyecto) {
        return service.listarPorProyecto(idProyecto);
    }

    @GetMapping("/porEmpleado/{idEmpleado}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoProyectoDTO> porEmpleado(@PathVariable Integer idEmpleado) {
        return service.listarPorEmpleado(idEmpleado);
    }

    // /EmpleadoProyecto/porFecha?desde=2025-11-01&hasta=2025-11-30
    @GetMapping("/porFecha")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoProyectoDTO> porFecha(@RequestParam LocalDate desde,
                                              @RequestParam LocalDate hasta) {
        return service.listarPorFechaInicio(desde, hasta);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoProyectoDTO actualizar(@PathVariable Integer id,
                                          @Validated @RequestBody ActualizarEmpleadoProyectoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoProyectoDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
