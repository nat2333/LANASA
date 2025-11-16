package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EstadoTransaccionDTOs.*;
import co.edu.unbosque.service.api.EstadoTransaccionServiceAPI;

@RestController
@RequestMapping("/estado-transaccion")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoTransaccionRestController {

	@Autowired
    private EstadoTransaccionServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoTransaccionDTO crear(@Validated @RequestBody CrearEstadoTransaccionRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EstadoTransaccionDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO actualizar(@PathVariable Short id,
                                           @Validated @RequestBody ActualizarEstadoTransaccionRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
