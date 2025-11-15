package co.edu.unbosque.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.DetalleFacturaVentaDTOs.*;
import co.edu.unbosque.service.api.DetalleFacturaVentaServiceAPI;

@RestController
@RequestMapping("/DetalleFacturaVenta")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleFacturaVentaRestController {

    private final DetalleFacturaVentaServiceAPI service;

    public DetalleFacturaVentaRestController(DetalleFacturaVentaServiceAPI service) {
        this.service = service;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleFacturaVentaDTO crear(@Validated @RequestBody CrearDetalleFacturaVentaRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleFacturaVentaDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/porFactura/{idFacturaVenta}")
    @ResponseStatus(HttpStatus.OK)
    public List<DetalleFacturaVentaDTO> listarPorFactura(@PathVariable Integer idFacturaVenta) {
        return service.listarPorFactura(idFacturaVenta);
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetalleFacturaVentaDTO actualizar(@PathVariable Integer id,
                                             @Validated @RequestBody ActualizarDetalleFacturaVentaRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
