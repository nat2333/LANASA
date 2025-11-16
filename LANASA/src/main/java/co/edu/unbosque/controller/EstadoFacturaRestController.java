package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EstadoFacturaDtos.*;
import co.edu.unbosque.service.api.EstadoFacturaServiceAPI;

@RestController
@RequestMapping("/estado-factura")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoFacturaRestController {

	@Autowired
    private EstadoFacturaServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoFacturaDTO crear(@RequestBody CrearEstadoFacturaRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoFacturaDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EstadoFacturaDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoFacturaDTO actualizar(@PathVariable Short id, @RequestBody ActualizarEstadoFacturaRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public EstadoFacturaDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
