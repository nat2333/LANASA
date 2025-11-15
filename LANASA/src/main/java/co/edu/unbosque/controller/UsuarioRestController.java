package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import co.edu.unbosque.dto.UsuarioDTOs.*;
import co.edu.unbosque.service.api.UsuarioServiceAPI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/Usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioServiceAPI service;
	
	@PostMapping(value="/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crear(@RequestBody CrearUsuarioRequest req) {
        return service.crear(req);
    }
	
	@GetMapping(value="/obtener/{id}")
	@ResponseStatus(HttpStatus.OK)
    public UsuarioDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }
	
	@GetMapping(value="/getAll")
	@ResponseStatus(HttpStatus.OK)
	public List<UsuarioDTO> listar() {
		return service.listar();
	}

	@PutMapping("/actualizar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarUsuarioRequest req) {
		return service.actualizar(id, req);
	}
	
	@PostMapping(value="/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }
	
	
	@DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }


}
