package co.edu.unbosque.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.TransaccionDTOs.*;
import co.edu.unbosque.service.api.TransaccionServiceAPI;

@RestController
@RequestMapping("/transacciones")
@CrossOrigin(origins = "http://localhost:4200")
public class TransaccionRestController {

    private final TransaccionServiceAPI service;

    public TransaccionRestController(TransaccionServiceAPI service) {
        this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TransaccionDTO crear(@Validated @RequestBody CrearTransaccionRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransaccionDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TransaccionDTO> listar() {
        return service.listar();
    }

    @GetMapping("/porFactura/{idFacturaVenta}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransaccionDTO> listarPorFactura(@PathVariable Integer idFacturaVenta) {
        return service.listarPorFactura(idFacturaVenta);
    }

    // Rango de fechas: /Transaccion/entreFechas?desde=2025-11-01T00:00:00&hasta=2025-11-30T23:59:59
    @GetMapping("/entreFechas")
    @ResponseStatus(HttpStatus.OK)
    public List<TransaccionDTO> listarEntreFechas(@RequestParam LocalDateTime desde,
                                                  @RequestParam LocalDateTime hasta) {
        return service.listarEntreFechas(desde, hasta);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransaccionDTO actualizar(@PathVariable Integer id,
                                     @Validated @RequestBody ActualizarTransaccionRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public TransaccionDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
