package co.edu.unbosque.service.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionCalificacionPrecio;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorCalificacion;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorProducto;
import co.edu.unbosque.dto.EstadisticasProyecciones.ProyeccionProveedorPuntualidad;
import co.edu.unbosque.dto.ProveedorCalificacionDTO;
import co.edu.unbosque.dto.ProveedorDTOs.*;
import co.edu.unbosque.dto.ProveedorProductoDTO;
import co.edu.unbosque.dto.ProveedorPuntualidadDTO;
import co.edu.unbosque.dto.ProveedorRelacionProductoDTO;
import co.edu.unbosque.entity.Proveedor;
import co.edu.unbosque.repository.ProveedorRepository;
import co.edu.unbosque.service.api.ProveedorServiceAPI;
import co.edu.unbosque.utils.GenericServiceImpl;
import co.edu.unbosque.utils.exception.ResourceNotFoundException;

@Service
@Transactional
public class ProveedorServiceImpl extends GenericServiceImpl<Proveedor, Integer> implements ProveedorServiceAPI {

	@Autowired
	private  ProveedorRepository repo;

	@Override
	public ProveedorDTO crear(CrearProveedorRequest req) {
		String rut = req.rut().trim();
        if (repo.existsByRut(rut)) throw new IllegalArgumentException("El RUT ya existe: " + rut);

        Proveedor p = new Proveedor();
        p.setRut(rut);
        p.setNombreComercial(req.nombreComercial().trim());
        p.setTelefono(req.telefono());
        p.setCorreo(req.correo());
        p.setDireccion(req.direccion());
        p.setCiudad(req.ciudad());
        p.setPais(req.pais());
        p.setCategoria(req.categoria());
        p.setCalificacion(req.calificacion());
        p.setEstado(true);

        return toDTO(repo.save(p));
	}

	@Override
	public ProveedorDTO obtener(Integer id) {
		return toDTO(obtenerEntidad(id));
	}

	@Override
	public List<ProveedorDTO> listar() {
		return getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public ProveedorDTO actualizar(Integer id, ActualizarProveedorRequest req) {
		Proveedor p = obtenerEntidad(id);
        if (req.telefono() != null)         p.setTelefono(req.telefono());
        if (req.correo() != null)           p.setCorreo(req.correo().trim().toLowerCase());
        if (req.direccion() != null)        p.setDireccion(req.direccion());
        if (req.ciudad() != null)           p.setCiudad(req.ciudad());
        if (req.pais() != null)             p.setPais(req.pais());
        if (req.calificacion() != null)     p.setCalificacion(req.calificacion());

        return toDTO(repo.save(p));

	}

	@Override
	public void eliminar(Integer id) {
		delete(id);
		
	}

	@Override
	public ProveedorDTO cambiarEstado(Integer id) {
		Proveedor p = obtenerEntidad(id);
        p.setEstado(!p.getEstado());
        return toDTO(repo.save(p));
	}

	@Override
	public JpaRepository<Proveedor, Integer> getDao() {
		return repo;
	}
	
	private ProveedorDTO toDTO(Proveedor p) {
        return new ProveedorDTO(
            p.getIdProveedor(),
            p.getRut(),
            p.getNombreComercial(),
            p.getTelefono(),
            p.getCorreo(),
            p.getDireccion(),
            p.getCiudad(),
            p.getPais(),
            p.getCategoria(),
            p.getCalificacion(),
            p.getEstado()
        );
    }

    private Proveedor obtenerEntidad(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe Proveedor id=" + id));
    }

	@Override
	public List<ProveedorCalificacionDTO> obtenerTopCalificacion() {
		List<ProyeccionProveedorCalificacion> proyecciones = repo.obtenerProveedoresPorCalificacion();
		List<ProveedorCalificacionDTO> dtos = new ArrayList<>();
		for(ProyeccionProveedorCalificacion p : proyecciones) {
			ProveedorCalificacionDTO dto = new ProveedorCalificacionDTO(
                    p.getNombreComercial(),
                    p.getCalificacion()
            );
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ProveedorProductoDTO> obtenerMejoresPrecioProducto(Integer idProducto) {
		List<ProyeccionProveedorProducto> proyecciones = repo.obtenerMejoresProveedoresPorProducto(idProducto);
		List<ProveedorProductoDTO> dtos = new ArrayList<>();
		for(ProyeccionProveedorProducto p : proyecciones) {
			ProveedorProductoDTO dto = new ProveedorProductoDTO(
                    p.getNombreComercial(),
                    p.getCalificacion(),
                    p.getSkuProducto(),
                    p.getNombreProducto(),
                    p.getPrecioPromedioCompra(),
                    p.getPrecioMinimoCompra(),
                    p.getPrecioMaximoCompra(),
                    p.getNumeroOrdenes()
            );
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ProveedorPuntualidadDTO> obtenerPuntualidad() {
		List<ProyeccionProveedorPuntualidad> proyecciones = repo.obtenerPuntualidadProveedores();
		List<ProveedorPuntualidadDTO> dtos = new ArrayList<>();
		for(ProyeccionProveedorPuntualidad p : proyecciones) {
			 BigDecimal porcentaje = BigDecimal.valueOf(p.getOrdenesATiempo() * 100.0 / p.getTotalOrdenes())
                     .setScale(2, RoundingMode.HALF_UP);
			
			ProveedorPuntualidadDTO dto = new ProveedorPuntualidadDTO(
                     p.getNombreComercial(),
                     p.getCalificacion(),
                     p.getTotalOrdenes(),
                     p.getDiasRetraso(),
                     porcentaje
            );
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ProveedorRelacionProductoDTO> obtenerRelacionProducto(Integer idProducto) {

		List<ProyeccionCalificacionPrecio> proyecciones = repo.obtenerRelacionProveedoresPorProducto(idProducto);
		List<ProveedorRelacionProductoDTO> dtos = new ArrayList<>();
		for(ProyeccionCalificacionPrecio p : proyecciones) {
			ProveedorRelacionProductoDTO dto = new ProveedorRelacionProductoDTO(
                     p.getNombreComercial(),
                     p.getCalificacion(),
                     p.getSkuProducto(),
                     p.getNombreProducto(),
                     p.getPrecioPromedioCompra(),
                     p.getNumeroOrdenes(),
                     p.getRelacion()
            );
			dtos.add(dto);
		}
		return dtos;
	}
	
	
}
