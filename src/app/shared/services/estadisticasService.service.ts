import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EmpleadoSalario {
  nombreCompleto: string;
  cargo: string;
  salario: number;
}

export interface ContratoDistribucion {
  tipoContrato: string;
  cantidad: number;
  porcentaje: number;
}

export interface CargoCosto {
  cargo: string;
  cantidadEmpleados: number;
  costoTotal: number;
  costoPromedio: number;
}

export interface NominaDepartamento {
  codigo : string;
  cantidadEmpleados : number;
  nominaTotal : number;
  presupuestoAnual : number;
  diferencia : number;
}

export interface ProductoUtilidad {
  sku: string;
  nombre: string;
  categoria: string;
  precioCompra: number;
  precioVentaSugerido: number;
  precioVentaPromedio: number;
  utilidadReal: number;
  utilidadRealPorcentaje: number;
  utilidadPotencial: number;
  utilidadmPotencialPorcentaje: number; 
}

export interface CategoriaUtilidad {
  categoria: string;
  cantidadProductos: number;
  precioCompraPromedio: number;
  precioVentaSugeridoPromedio: number;
  utilidadPromedio: number;
  utilidadPromedioPorcentaje: number;
}

export interface ProveedorCalificacion {
  nombreComercial: string;
  calificacion: number;
}

export interface ProveedorProducto {
  nombreComercial: string;
  calificacion: number;
  skuProducto: string;
  nombreProducto: string;
  precioPromedioCompra: number;
  precioMinimoCompra: number;
  precioMaximoCompra: number;
  numeroOrdenes: number;
}

export interface ProveedorPuntualidad {
  nombreComercial: string;
  calificacion: number;
  totalOrdenes: number;
  promedioDiasRetraso: number;
  porcentajeOrdenesATiempo: number;
}

export interface ProveedorRelacionProducto {
  nombreComercial: string;
  calificacion: number;
  skuProducto: string;
  nombreProducto: string;
  precioPromedioCompra: number;
  numeroOrdenes: number;
  relacion: number;
}

export interface ProveedorRelacionCategoria {
  nombreComercial: string;
  calificacion: number;
  categoria: string;
  precioPromedioCompra: number;
  numeroOrdenes: number;
  relacion: number;
}

export interface VentasMensuales {
  anio: number;
  mes: number;               
  totalVentas: number;     
  numeroFacturas: number;    
}

export interface ClienteTopVentas {
  correo: string;
  telefono: string;
  pais: string;
  ciudad: string;
  totalComprado: number;
  numeroFacturas: number;
}

export interface ProductoMasVendido {
  nombreProducto: string;
  categoria: string;
  cantidadVendida: number;
  totalVendido: number;
}

export interface ProyectoPresupuesto {
  idProyecto: number;
  codigo: string;
  nombreProyecto: string;
  nombreDepartamento: string;
  correoCliente: string;
  tipoProyecto: string;
  presupuestoAprobado: number;
  presupuestoUtilizado: number;
  saldoPresupuesto: number;
  porcentajeUtilizado: number;
  sobrepasa: boolean;
}

export interface ProyectoPorDepartamento {
  idDepartamento: number;
  nombreDepartamento: string;
  numeroProyectos: number;
  presupuestoTotalAprobado: number;
  presupuestoTotalUtilizado: number;
}

export interface ProyectoPorCliente {
  idCliente: number;
  correoCliente: string;
  pais: string;
  ciudad: string;
  numeroProyectos: number;
  presupuestoTotalAprobado: number;
  presupuestoTotalUtilizado: number;
}


@Injectable({
  providedIn: 'root'
})
export class EstadisticasService {
  private apiUrl = 'http://localhost:8181/estadisticas';

  constructor(private http: HttpClient) { }

  getTopSalarios(limit: number = 10): Observable<EmpleadoSalario[]> {
    return this.http.get<EmpleadoSalario[]>(
      `${this.apiUrl}/empleados/top-salarios?limit=${limit}`
    );
  }

  getDistribucionContratos(): Observable<ContratoDistribucion[]> {
    return this.http.get<ContratoDistribucion[]>(
      `${this.apiUrl}/empleados/distribucion-contratos`
    );
  }

  getCostosPorCargo(): Observable<CargoCosto[]> {
    return this.http.get<CargoCosto[]>(
      `${this.apiUrl}/empleados/costos-cargo`
    );
  }

  getNominaDepartamento(): Observable<NominaDepartamento[]>{
    return this.http.get<NominaDepartamento[]>(
      `${this.apiUrl}/departamentos/nomina`
    );
  }

  getUtilidadProductos() {
    return this.http.get<ProductoUtilidad[]>(
      `${this.apiUrl}/productos/utilidad`
    );
  }

  getUtilidadPorCategoria() {
    return this.http.get<CategoriaUtilidad[]>(
      `${this.apiUrl}/productos/utilidad-categoria`
    );
  }

  getProvTopCalificacion() {
  return this.http.get<ProveedorCalificacion[]>(
    `${this.apiUrl}/proveedores/top-calificacion`
  );
}

getProvPuntualidad() {
  return this.http.get<ProveedorPuntualidad[]>(
    `${this.apiUrl}/proveedores/puntualidad`
  );
}

getProvMejoresPreciosProducto(idProducto: number) {
  return this.http.get<ProveedorProducto[]>(
    `${this.apiUrl}/proveedores/mejores-precios-producto`,
    { params: { idProducto } as any }
  );
}

getProvRelacionProducto(idProducto: number) {
  return this.http.get<ProveedorRelacionProducto[]>(
    `${this.apiUrl}/proveedores/relacion-producto`,
    { params: { idProducto } as any }
  );
}

getVentasMensuales(): Observable<VentasMensuales[]> {
  return this.http.get<VentasMensuales[]>(`${this.apiUrl}/ventas/mensuales`);
}

getTopClientesVentas(): Observable<ClienteTopVentas[]> {
  return this.http.get<ClienteTopVentas[]>(`${this.apiUrl}/ventas/top-clientes`);
}

getProductosMasVendidos(): Observable<ProductoMasVendido[]> {
  return this.http.get<ProductoMasVendido[]>(`${this.apiUrl}/ventas/productos-mas-vendidos`);
}

getProyectosPresupuestos() {
  return this.http.get<ProyectoPresupuesto[]>(`${this.apiUrl}/proyectos/presupuestos`);
}
getProyectosPorDepartamento() {
  return this.http.get<ProyectoPorDepartamento[]>(`${this.apiUrl}/proyectos/por-departamento`);
}
getProyectosPorCliente() {
  return this.http.get<ProyectoPorCliente[]>(`${this.apiUrl}/proyectos/por-cliente`);
}

}
