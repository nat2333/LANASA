package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.dto.ProveedorProductoDTOs.*;
import co.edu.unbosque.service.api.ProveedorProductoServiceAPI;

@RestController
@RequestMapping("/ProveedorProducto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProveedorProductoRestController {

	@Autowired
	private ProveedorProductoServiceAPI service;
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorProductoDTO crear(@RequestBody CrearProveedorProductoRequest req) {
        return service.crear(req);
    }

    @GetMapping("/obtener/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorProductoDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProveedorProductoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/actualizar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorProductoDTO actualizar(@PathVariable Integer id,@RequestBody ActualizarProveedorProductoRequest req) {
        return service.actualizar(id, req);
    }

    @PostMapping("/cambiarEstado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProveedorProductoDTO cambiarEstado(@PathVariable Integer id) {
        return service.cambiarEstado(id);
    }

    @GetMapping("/porProducto/{idProducto}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProveedorProductoDTO> porProducto(@PathVariable Integer idProducto) {
        return service.listarPorProducto(idProducto);
    }

    @GetMapping("/porProveedor/{idProveedor}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProveedorProductoDTO> porProveedor(@PathVariable Integer idProveedor) {
        return service.listarPorProveedor(idProveedor);
    }
}
