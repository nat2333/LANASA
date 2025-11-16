package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.dto.RolEmpleadoDtos.*;
import co.edu.unbosque.service.api.RolEmpleadoServiceAPI;

@RestController
@RequestMapping("/rol-empleado")
@CrossOrigin(origins = "http://localhost:4200")
public class RolEmpleadoRestController {

	@Autowired
    private RolEmpleadoServiceAPI service;

	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RolEmpleadoDTO crear(@RequestBody CrearRolEmpleadoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RolEmpleadoDTO obtener(@PathVariable short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RolEmpleadoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RolEmpleadoDTO actualizar(@PathVariable short id, @RequestBody ActualizarRolEmpleadoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public RolEmpleadoDTO cambiarEstado(@PathVariable short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable short id) {
        service.eliminar(id);
    }
}
