package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.DetalleFacturaVentaDTOs.*;
import co.edu.unbosque.service.api.DetalleFacturaVentaServiceAPI;

@RestController
@RequestMapping("/detalles-venta")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleFacturaVentaRestController {

	
	@Autowired
    private DetalleFacturaVentaServiceAPI service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleFacturaVentaDTO crear(@Validated @RequestBody CrearDetalleFacturaVentaRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleFacturaVentaDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DetalleFacturaVentaDTO> listarPorFactura(@PathVariable Integer idFacturaVenta) {
        return service.listarPorFactura(idFacturaVenta);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleFacturaVentaDTO actualizar(@PathVariable Integer id,
                                             @Validated @RequestBody ActualizarDetalleFacturaVentaRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
    
    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public DetalleFacturaVentaDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }
}
