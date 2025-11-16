package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.dto.ProyectoDtos.CrearProyectoRequest;
import co.edu.unbosque.dto.ProyectoDtos.*;
import co.edu.unbosque.service.api.ProyectoServiceAPI;

@RestController
@RequestMapping("/proyectos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProyectoRestController {

	@Autowired 
	private ProyectoServiceAPI service;
	
	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProyectoDTO crear(@RequestBody CrearProyectoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProyectoDTO obtener(@PathVariable Integer id) { return service.obtener(id); }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProyectoDTO> listar() { return service.listar(); }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProyectoDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarProyectoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public ProyectoDTO cambiarEstado(@PathVariable Integer id) { return service.cambiarEstado(id); }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
