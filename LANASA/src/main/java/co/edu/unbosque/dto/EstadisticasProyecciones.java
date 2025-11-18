package co.edu.unbosque.dto;

import java.math.BigDecimal;

public class EstadisticasProyecciones {

	public interface ProyeccionDepartamentoEstadistica {
		String getNombreDepartamento();
		String getCodigo();
		Long getCantidadEmpleados();
		BigDecimal getNominaTotal();
		BigDecimal getPresupuestoAnual();
		BigDecimal getDiferencia();
	}

	public interface ProyeccionUtilidadProducto {
		String getSku();
		String getNombre();
		String getCategoria();
		BigDecimal getPrecioCompra();
		BigDecimal getPrecioVentaSugerido();

		BigDecimal getPrecioVentaPromedio();       

		BigDecimal getUtilidadReal();           
		BigDecimal getUtilidadRealPorcentaje();       

		BigDecimal getUtilidadPotencial();      
		BigDecimal getUtilidadPotencialPorcentaje(); 
	}

	public interface ProyeccionUtilidadCategoria {
		String getCategoria();
		Long getCantidadProductos();
		BigDecimal getPrecioCompraPromedio();
		BigDecimal getPrecioVentaSugeridoPromedio();
		BigDecimal getUtilidadPromedio();
		BigDecimal getUtilidadPorcentajePromedio();
	}

	public interface ProyeccionProveedorCalificacion {
		String getNombreComercial();
		BigDecimal getCalificacion();
	}

	public interface ProyeccionProveedorProducto {
		String getNombreComercial();
		BigDecimal getCalificacion();
		String getSkuProducto();
		String getNombreProducto();
		BigDecimal getPrecioPromedioCompra();
		BigDecimal getPrecioMinimoCompra();
		BigDecimal getPrecioMaximoCompra();
		Long getNumeroOrdenes();
	}

	public interface ProyeccionProveedorPuntualidad {
		String getNombreComercial();
		BigDecimal getCalificacion();
		Long getTotalOrdenes();
		BigDecimal getDiasRetraso();      
		Long getOrdenesATiempo();    
	}

	public interface ProyeccionCalificacionPrecio {
		String getNombreComercial();
		BigDecimal getCalificacion();
		String getSkuProducto();
		String getNombreProducto();
		BigDecimal getPrecioPromedioCompra();
		Long getNumeroOrdenes();
		BigDecimal getRelacion();
	}


	public interface ProyeccionVentasMensuales {
		Integer getAnio();
		Integer getMes();
		BigDecimal getTotalVentas();
		Long getNumeroFacturas();
	}

	public interface ProyeccionTopCliente {
		String getCorreo();
		String getTelefono();
		String getPais();
		String getCiudad();
		BigDecimal getTotalComprado();
		Long getNumeroFacturas();
	}

	public interface ProyeccionProductoMasVendido {
		String getNombreProducto();
		String getCategoria();
		Long getCantidadVendida();
		BigDecimal getTotalVendido();
	}

	public interface ProyeccionProyectosPresupuesto {
        Integer getIdProyecto();
        String getCodigo();
        String getNombreProyecto();
        String getNombreDepartamento();
        String getCorreoCliente();
        String getTipoProyecto();
        BigDecimal getPresupuestoAprobado();
        BigDecimal getPresupuestoUtilizado();
        BigDecimal getSaldoPresupuesto();
        BigDecimal getPorcentajeUtilizado();
        Short getSobrepasa(); 
    }
	
	public interface ProyeccionProyectoPorDepartamento {
        Integer getIdDepartamento();
        String getNombreDepartamento();
        Long getNumeroProyectos();
        BigDecimal getPresupuestoTotalAprobado();
        BigDecimal getPresupuestoTotalUtilizado();
    }
	
	public interface ProyeccionProyectoPorCliente{
        Integer getIdCliente();
        String getCorreoCliente();
        String getPais();
        String getCiudad();
        Long getNumeroProyectos();
        BigDecimal getPresupuestoTotalAprobado();
        BigDecimal getPresupuestoTotalUtilizado();
    }
	
	public interface ProyeccionProyectoEstadoDepartamento {
	    Integer getIdProyecto();
	    String getCodigo();
	    String getNombreProyecto();
	    String getNombreDepartamento();

	    BigDecimal getPresupuestoAprobado();
	    BigDecimal getPresupuestoUtilizado();
	    BigDecimal getDiferenciaPresupuesto();

	    String getEstadoPresupuesto(); 
	    String getEstadoEntrega();    

	    Integer getDiasRetraso();      
	}

}
