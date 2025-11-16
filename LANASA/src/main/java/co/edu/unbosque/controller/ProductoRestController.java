package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.ProductoDTOs.*;
import co.edu.unbosque.service.api.ProductoServiceAPI;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoRestController {

	@Autowired
    private ProductoServiceAPI service;

	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO crear(@RequestBody CrearProductoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO obtener(@PathVariable Integer id) {
    	return service.obtener(id); 
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductoDTO> listar() { 
    	return service.listar(); 
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarProductoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public ProductoDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
