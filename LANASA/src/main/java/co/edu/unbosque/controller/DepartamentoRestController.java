package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.DepartamentoDTOs.*;
import co.edu.unbosque.service.api.DepartamentoServiceAPI;

@RestController
@RequestMapping("/departamentos")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartamentoRestController {

	@Autowired
	private DepartamentoServiceAPI service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepartamentoDTO crear(@RequestBody CrearDepartamentoRequest req) {
		return service.crear(req);
	}
	
	@GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartamentoDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DepartamentoDTO> listar() {
        return service.listar();
    }
	
	@PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartamentoDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarDepartamentoRequest req) {
        return service.actualizar(id, req);
    }
	
	@PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public DepartamentoDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

}
