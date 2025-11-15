package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.TipoProyectoDtos.*;
import co.edu.unbosque.service.api.TipoProyectoServiceAPI;

@RestController
@RequestMapping("/TipoProyecto")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoProyectoRestController {

	@Autowired
    private TipoProyectoServiceAPI service;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public TipoProyectoDTO crear( @RequestBody CrearTipoProyectoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoProyectoDTO obtener(@PathVariable Short id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TipoProyectoDTO> listar() {
        return service.listar();
    }
    
    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoProyectoDTO cambiarEstado(@PathVariable Short id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Short id) {
        service.eliminar(id);
    }
}
