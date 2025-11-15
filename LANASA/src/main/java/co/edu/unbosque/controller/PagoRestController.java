package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.PagoDtos.*;
import co.edu.unbosque.service.api.PagoServiceAPI;

@RestController
@RequestMapping("/Pago")
@CrossOrigin(origins = "http://localhost:4200")
public class PagoRestController {

	@Autowired
    private PagoServiceAPI service;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public PagoDTO crear( @RequestBody CrearPagoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagoDTO obtener(@PathVariable Integer id) { return service.obtener(id); }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<PagoDTO> listar() { return service.listar(); }


    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagoDTO cambiarEstado(@PathVariable Integer id) { return service.cambiarEstado(id); }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }

}
