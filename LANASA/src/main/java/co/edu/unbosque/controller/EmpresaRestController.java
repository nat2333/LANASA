package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.EmpresaDTOs.*;
import co.edu.unbosque.service.api.EmpresaServiceAPI;

@RestController
@RequestMapping("/Empresa")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpresaRestController {

	@Autowired
    private EmpresaServiceAPI service;

	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaDTO crear(@RequestBody CrearEmpresaRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public EmpresaDTO obtener(@PathVariable Integer idCliente) {
        return service.obtener(idCliente);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpresaDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public EmpresaDTO actualizar(@PathVariable Integer idCliente, @RequestBody ActualizarEmpresaRequest req) {
        return service.actualizar(idCliente, req);
    }

    @PostMapping("/cambiarEstado/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public EmpresaDTO cambiarEstado(@PathVariable Integer idCliente) {
        return service.cambiarEstado(idCliente);
    }

    @DeleteMapping("/eliminar/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer idCliente) {
        service.eliminar(idCliente);
    }
}
