package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EstadoCompraDTOs.*;
import co.edu.unbosque.service.api.EstadoCompraServiceAPI;

@RestController
@RequestMapping("/estado-compra")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoCompraRestController {

	@Autowired
    private EstadoCompraServiceAPI service;

	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoCompraDTO crear( @RequestBody CrearEstadoCompraRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoCompraDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EstadoCompraDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoCompraDTO actualizar(@PathVariable Short id,@RequestBody ActualizarEstadoCompraRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public EstadoCompraDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
