package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.CargoDTOs.*;
import co.edu.unbosque.service.api.CargoServiceAPI;

@RestController
@RequestMapping("/cargo")
@CrossOrigin(origins = "http://localhost:4200")
public class CargoRestController {

	@Autowired
	private  CargoServiceAPI service;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public CargoDTO crear(@RequestBody CrearCargoRequest req) {
		return service.crear(req);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO obtener(@PathVariable Short id) {
		return service.obtener(id);
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<CargoDTO> listar() {
		return service.listar();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO actualizar(@PathVariable Short id, @RequestBody ActualizarCargoRequest req) {
		return service.actualizar(id, req);
	}

	@PostMapping("/{id}/estado")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO cambiarEstado(@PathVariable Short id) {
		return service.cambiarEstado(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Short id) {
		service.eliminar(id);
	}
}
