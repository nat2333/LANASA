package co.edu.unbosque.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.ProveedorDTOs.*;
import co.edu.unbosque.service.api.ProveedorServiceAPI;

@RestController
@RequestMapping("/Proveedor")
@CrossOrigin(origins = "http://localhost:4200")
public class ProveedorRestController {

	@Autowired
    private ProveedorServiceAPI service;

	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorDTO crear(@RequestBody CrearProveedorRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProveedorDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarProveedorRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
