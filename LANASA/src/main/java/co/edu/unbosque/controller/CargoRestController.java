package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.CargoDTOs.*;
import co.edu.unbosque.service.api.CargoServiceAPI;

@RestController
@RequestMapping("/Cargo")
@CrossOrigin(origins = "http://localhost:4200")
public class CargoRestController {

	@Autowired
	private  CargoServiceAPI service;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public CargoDTO crear(@RequestBody CrearCargoRequest req) {
		return service.crear(req);
	}

	@GetMapping("/obtener/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO obtener(@PathVariable Short id) {
		return service.obtener(id);
	}

	@GetMapping("/getAll")
	@ResponseStatus(HttpStatus.OK)
	public List<CargoDTO> listar() {
		return service.listar();
	}

	@PutMapping("/actualizar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO actualizar(@PathVariable Short id, @RequestBody ActualizarCargoRequest req) {
		return service.actualizar(id, req);
	}

	@PostMapping("/cambiarEstado/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CargoDTO cambiarEstado(@PathVariable Short id) {
		return service.cambiarEstado(id);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Short id) {
		service.eliminar(id);
	}
}
