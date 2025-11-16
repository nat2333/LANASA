package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.MetodoPagoDTOs.*;
import co.edu.unbosque.service.api.MetodoPagoServiceAPI;

@RestController
@RequestMapping("/metodo-pago")
@CrossOrigin(origins = "http://localhost:4200")
public class MetodoPagoRestController {

	@Autowired
	private MetodoPagoServiceAPI service;

	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MetodoPagoDTO crear(@RequestBody CrearMetodoPagoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetodoPagoDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<MetodoPagoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MetodoPagoDTO actualizar(@PathVariable Short id, @RequestBody ActualizarMetodoPagoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public MetodoPagoDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
