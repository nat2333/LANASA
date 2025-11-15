package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.TipoContratoDTOs.*;
import co.edu.unbosque.service.api.TipoContratoServiceAPI;

@RestController
@RequestMapping("/tipo-contrato")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoContratoRestController {

	@Autowired
	private  TipoContratoServiceAPI service;
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoContratoDTO crear( @RequestBody CrearTipoContratoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoContratoDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TipoContratoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoContratoDTO actualizar(@PathVariable Short id, @RequestBody ActualizarTipoContratoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public TipoContratoDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
