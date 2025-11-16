package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.DetalleOrdenCompraDTOs.*;
import co.edu.unbosque.service.api.DetalleOrdenCompraServiceAPI;

@RestController
@RequestMapping("/detalle-orden-compra")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleOrdenCompraRestController {

	@Autowired
	private DetalleOrdenCompraServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleOrdenCompraDTO crear(@RequestBody CrearDetalleOCRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleOrdenCompraDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DetalleOrdenCompraDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleOrdenCompraDTO actualizar(@PathVariable Integer id,@RequestBody ActualizarDetalleOCRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public DetalleOrdenCompraDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
