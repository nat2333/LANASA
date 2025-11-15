package co.edu.unbosque.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.HistorialEmpleadoDTOs.*;
import co.edu.unbosque.service.api.HistorialServiceAPI;

@RestController
@RequestMapping("/Historial")
@CrossOrigin(origins = "http://localhost:4200")
public class HistorialRestController {

	@Autowired
	private  HistorialServiceAPI service;
	
	 @PostMapping("/crear")
	    @ResponseStatus(HttpStatus.CREATED)
	    public HistorialDTO crear( @RequestBody CrearHistorialRequest req) {
	        return service.crear(req);
	    }

	    @GetMapping("/obtener/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public HistorialDTO obtener(@PathVariable Integer id) {
	        return service.obtener(id);
	    }

	    @GetMapping("/getAll")
	    @ResponseStatus(HttpStatus.OK)
	    public List<HistorialDTO> listar() {
	        return service.listar();
	    }

	    @PutMapping("/actualizar/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public HistorialDTO actualizar(@PathVariable Integer id,
	                                    @RequestBody ActualizarHistorialRequest req) {
	        return service.actualizar(id, req);
	    }

	    @PostMapping("/cerrar/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public HistorialDTO cerrar(@PathVariable Integer id,
	                               @RequestParam(required = false) LocalDate fechaFin) {
	        return service.cambiarEstado(id, fechaFin);
	    }

	    @GetMapping("/porEmpleado/{idEmpleado}")
	    @ResponseStatus(HttpStatus.OK)
	    public List<HistorialDTO> listarPorEmpleado(@PathVariable Integer idEmpleado) {
	        return service.listarPorEmpleado(idEmpleado);
	    }

	    @DeleteMapping("/eliminar/{id}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void eliminar(@PathVariable Integer id) {
	        service.eliminar(id);
	    }
}
