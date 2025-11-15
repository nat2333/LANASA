package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.OrdenCompraDTOs.*;
import co.edu.unbosque.service.api.OrdenCompraServiceAPI;

@RestController
@RequestMapping("/OrdenCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenCompraRestController {

	@Autowired
	private OrdenCompraServiceAPI service;
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public OrdenCompraDTO crear( @RequestBody CrearOrdenCompraRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrdenCompraDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<OrdenCompraDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrdenCompraDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarOrdenCompraRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrdenCompraDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
	
}
