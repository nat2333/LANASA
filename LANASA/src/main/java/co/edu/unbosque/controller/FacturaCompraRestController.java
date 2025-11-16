package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.FacturaCompraDTOs.*;
import co.edu.unbosque.service.api.FacturaCompraServiceAPI;

@RestController
@RequestMapping("/facturas-compra")
@CrossOrigin(origins = "http://localhost:4200")
public class FacturaCompraRestController {

	@Autowired
    private FacturaCompraServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaCompraDTO crear( @RequestBody CrearFacturaCompraRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacturaCompraDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FacturaCompraDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacturaCompraDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarFacturaCompraRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public FacturaCompraDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
