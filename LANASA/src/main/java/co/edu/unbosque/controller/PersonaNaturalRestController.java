package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.PersonaNaturalDTOs.*;
import co.edu.unbosque.service.api.PersonaNaturalServiceAPI;

@RestController
@RequestMapping("/PersonaNatural")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaNaturalRestController {

	@Autowired
    private PersonaNaturalServiceAPI service;

	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaNaturalDTO crear(@RequestBody CrearPersonaNaturalRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public PersonaNaturalDTO obtener(@PathVariable Integer idCliente) {
        return service.obtener(idCliente);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonaNaturalDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public PersonaNaturalDTO actualizar(@PathVariable Integer idCliente,@RequestBody ActualizarPersonaNaturalRequest req) {
        return service.actualizar(idCliente, req);
    }

    @PostMapping("/cambiarEstado/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public PersonaNaturalDTO cambiarEstado(@PathVariable Integer idCliente) {
        return service.cambiarEstado(idCliente);
    }

    @DeleteMapping("/eliminar/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer idCliente) {
        service.eliminar(idCliente);
    }
}
