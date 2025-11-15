package co.edu.unbosque.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import co.edu.unbosque.dto.TipoUsuarioDTOs.*;
import co.edu.unbosque.service.api.TipoUsuarioServiceAPI;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/TipoUsuario")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoUsuarioRestController {

	@Autowired
	private TipoUsuarioServiceAPI service;
	
	@PostMapping(value="/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public TipoUsuarioDTO crear(@RequestBody CrearTipoUsuarioRequest req) {
        return service.crear(req);
    }
	
	@GetMapping(value="/obtener/{id}")
	@ResponseStatus(HttpStatus.OK)
    public TipoUsuarioDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }
	
	@GetMapping(value="/getAll")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoUsuarioDTO> listar() {
		return service.listar();
	}

	@PutMapping("/actualizar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TipoUsuarioDTO actualizar(@PathVariable Short id, @RequestBody ActualizarTipoUsuarioRequest req) {
		return service.actualizar(id, req);
	}
	
	@PostMapping(value="/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoUsuarioDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }
	
	
	@DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
	
	
}
