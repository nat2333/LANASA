package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.dto.FacturaVentaDTOs.*;
import co.edu.unbosque.service.api.FacturaVentaServiceAPI;

@RestController
@RequestMapping("/FacturaVenta")
@CrossOrigin(origins = "http://localhost:4200")
public class FacturaVentaRestController {

	@Autowired
	private FacturaVentaServiceAPI service;
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public FacturaVentaDTO crear(@RequestBody CrearFacturaVentaRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacturaVentaDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<FacturaVentaDTO> listar() { return service.listar(); }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacturaVentaDTO actualizar(@PathVariable Integer id,
                                       @RequestBody ActualizarFacturaVentaRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FacturaVentaDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
