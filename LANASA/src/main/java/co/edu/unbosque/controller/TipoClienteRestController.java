package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.TipoClienteDTOs.*;
import co.edu.unbosque.service.api.TipoClienteServiceAPI;

@RestController
@RequestMapping("/tipo-cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoClienteRestController {

	@Autowired
	private TipoClienteServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TipoClienteDTO crear(@RequestBody CrearTipoClienteRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoClienteDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TipoClienteDTO> listar() {
        return service.listar();
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public TipoClienteDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}/eliminar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
	
}
