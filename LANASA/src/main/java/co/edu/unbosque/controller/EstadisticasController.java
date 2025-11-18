package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.dto.CargoCostoDTO;
import co.edu.unbosque.dto.CategoriaUtilidadDTO;
import co.edu.unbosque.dto.ClienteTopVentasDTO;
import co.edu.unbosque.dto.ContratoDistribucionDTO;
import co.edu.unbosque.dto.DepartamentoEstadisticasDTO;
import co.edu.unbosque.dto.EmpleadoSalarioDTO;
import co.edu.unbosque.dto.ProductoMasVendidoDTO;
import co.edu.unbosque.dto.ProductoUtilidadDTO;
import co.edu.unbosque.dto.ProveedorCalificacionDTO;
import co.edu.unbosque.dto.ProveedorProductoDTO;
import co.edu.unbosque.dto.ProveedorPuntualidadDTO;
import co.edu.unbosque.dto.ProveedorRelacionProductoDTO;
import co.edu.unbosque.dto.ProyectoEstadoDepartamentoDTO;
import co.edu.unbosque.dto.ProyectoPorClienteDTO;
import co.edu.unbosque.dto.ProyectoPorDepartamentoDTO;
import co.edu.unbosque.dto.ProyectoPresupuestoDTO;
import co.edu.unbosque.dto.VentasMensualesDTO;
import co.edu.unbosque.service.api.DepartamentoServiceAPI;
import co.edu.unbosque.service.api.DetalleFacturaVentaServiceAPI;
import co.edu.unbosque.service.api.FacturaVentaServiceAPI;
import co.edu.unbosque.service.api.ProductoServiceAPI;
import co.edu.unbosque.service.api.ProveedorServiceAPI;
import co.edu.unbosque.service.api.ProyectoServiceAPI;
import co.edu.unbosque.service.impl.EstadisticasEmpleadoService;

@RestController
@RequestMapping("/estadisticas")
@CrossOrigin(origins = "http://localhost:4200")
public class EstadisticasController {

	@Autowired
	private EstadisticasEmpleadoService service;
	
	@Autowired
    private DepartamentoServiceAPI departamentoService;
	
	@Autowired
	private ProductoServiceAPI productoService;
	
	@Autowired
    private ProveedorServiceAPI proveedorService;
	
	@Autowired
    private FacturaVentaServiceAPI factVentaService;
	
	@Autowired
    private DetalleFacturaVentaServiceAPI detVentaService;
	
	@Autowired
	private ProyectoServiceAPI proyectoService;

	@GetMapping("/empleados/top-salarios")
	public ResponseEntity<List<EmpleadoSalarioDTO>> getTopSalarios(@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(service.getTopEmpleadosPorSalario(limit));
	}

	@GetMapping("/empleados/distribucion-contratos")
	public ResponseEntity<List<ContratoDistribucionDTO>> getDistribucionContratos() {
		return ResponseEntity.ok(service.getDistribucionContratos());
	}

	@GetMapping("/empleados/costos-cargo")
	public ResponseEntity<List<CargoCostoDTO>> getCostosPorCargo() {
		return ResponseEntity.ok(service.getCostosPorCargo());
	}
	
	@GetMapping("/departamentos/nomina")
    public ResponseEntity<List<DepartamentoEstadisticasDTO>> getEstadisticasDepartamentos() {
        return ResponseEntity.ok(departamentoService.obtenerNomina());
    }
	
	@GetMapping("/productos/utilidad")
	public ResponseEntity<List<ProductoUtilidadDTO>> getMargenRealPotencialProductos() {
	    return ResponseEntity.ok(productoService.obtenerUtilidadProductos());
	}

	@GetMapping("/productos/utilidad-categoria")
	public ResponseEntity<List<CategoriaUtilidadDTO>> getMargenPorCategoria() {
	    return ResponseEntity.ok(productoService.obtenerUtilidadCategoria());
	}
	
	@GetMapping("/proveedores/top-calificacion")
    public ResponseEntity<List<ProveedorCalificacionDTO>> getProveedoresTopCalificacion() {
        return ResponseEntity.ok(proveedorService.obtenerTopCalificacion());
    }

    @GetMapping("/proveedores/mejores-precios-producto")
    public ResponseEntity<List<ProveedorProductoDTO>> getMejoresProveedoresPorProducto( @RequestParam Integer idProducto) {
        return ResponseEntity.ok(proveedorService.obtenerMejoresPrecioProducto(idProducto));
    }

    @GetMapping("/proveedores/puntualidad")
    public ResponseEntity<List<ProveedorPuntualidadDTO>> getPuntualidadProveedores() {
        return ResponseEntity.ok(proveedorService.obtenerPuntualidad());
    }
    
    @GetMapping("/proveedores/relacion-producto")
    public ResponseEntity<List<ProveedorRelacionProductoDTO>> getRelacionProveedoresPorProducto( @RequestParam Integer idProducto) {

        return ResponseEntity.ok(proveedorService.obtenerRelacionProducto(idProducto));
    }

    @GetMapping("/ventas/mensuales")
    public ResponseEntity<List<VentasMensualesDTO>> getVentasMensuales() {
        return ResponseEntity.ok(factVentaService.obtenerVentasMensuales());
    }

    @GetMapping("/ventas/top-clientes")
    public ResponseEntity<List<ClienteTopVentasDTO>> getTopClientes() {
        return ResponseEntity.ok(factVentaService.obtenerTopClientes());
    }

    @GetMapping("/ventas/productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> getProductosMasVendidos() {
        return ResponseEntity.ok(detVentaService.obtenerProductosMasVendidos());
    }
    
    @GetMapping("/proyectos/presupuestos")
    public ResponseEntity<List<ProyectoPresupuestoDTO>> getResumenPortafolioProyectos() {
        return ResponseEntity.ok(proyectoService.obtenerPresupuestos());
    }

    @GetMapping("/proyectos/por-departamento")
    public ResponseEntity<List<ProyectoPorDepartamentoDTO>> getProyectosPorDepartamento() {
        return ResponseEntity.ok(proyectoService.obtenerProyectosPorDepartamento());
    }

    @GetMapping("/proyectos/por-cliente")
    public ResponseEntity<List<ProyectoPorClienteDTO>> getProyectosPorCliente() {
        return ResponseEntity.ok(proyectoService.obtenerProyectosPorCliente());
    }
    
    @GetMapping("/proyectos/departamento/{idDepartamento}")
    public ResponseEntity<List<ProyectoEstadoDepartamentoDTO>> getEstadoProyectosDepartamento( @PathVariable Integer idDepartamento) {

        return ResponseEntity.ok(proyectoService.obtenerEstadoProyectosPorDepartamento(idDepartamento));
    }

}
