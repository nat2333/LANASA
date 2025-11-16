package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.ClienteDTOs.*;
import co.edu.unbosque.service.api.ClienteServiceAPI;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteRestController {

	@Autowired
	private ClienteServiceAPI service;
	
	@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crear(@RequestBody CrearClienteRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> listar() {
        return service.listar();
    }
    
    @GetMapping("/tipo/{idTipoCliente}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> listarPorTipo(@PathVariable Short idTipoCliente) {
        return service.listarPorTipo(idTipoCliente);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO actualizar(@PathVariable Integer id, @RequestBody ActualizarClienteRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
