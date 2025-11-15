package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EmpleadoDTOs.*;
import co.edu.unbosque.service.api.EmpleadoServiceAPI;

@RestController
@RequestMapping("/Empleado")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoRestController {

	@Autowired
	private EmpleadoServiceAPI service;
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoDTO crear(@RequestBody CrearEmpleadoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarEmpleadoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
