package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EstadoTransaccionDTOs.*;
import co.edu.unbosque.service.api.EstadoTransaccionServiceAPI;

@RestController
@RequestMapping("/EstadoTransaccion")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadoTransaccionRestController {

    private final EstadoTransaccionServiceAPI service;

    public EstadoTransaccionRestController(EstadoTransaccionServiceAPI service) {
        this.service = service;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoTransaccionDTO crear(@Validated @RequestBody CrearEstadoTransaccionRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EstadoTransaccionDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO actualizar(@PathVariable Short id,
                                           @Validated @RequestBody ActualizarEstadoTransaccionRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EstadoTransaccionDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
