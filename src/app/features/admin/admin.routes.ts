import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layout/admin-layout.component';

// Empleado
import { EmpleadosListComponent } from './pages/empleados-list/empleados-list.component';
import { HistoricoEmpleadoComponent } from './pages/historico-empleado/historico-empleado.component';
import { TipoContratoComponent } from './pages/tipo-contrato/tipo-contrato.component';
import { CargoComponent } from './pages/carga-empleado/carga-empleado.component';

// Departamentos
import { DepartamentosComponent } from './pages/departamentos/departamentos.component';

// Cliente
import { TipoClienteComponent } from './pages/tipo-cliente/tipo-cliente.component';
import { ClientesListComponent } from './pages/clientes-list/clientes-list.component';

// Proyectos
import { ProyectosListComponent } from './pages/proyectos-list/proyectos-list.component';
import { ProyectoInfoComponent } from './pages/proyecto-info/proyecto-info.component';
import { ProyectoEmpleadosComponent } from './pages/proyecto-empleados/proyecto-empleados.component';
import { ProyectoTipoComponent } from './pages/proyecto-tipo/proyecto-tipo.component';
import { ProyectoRolEmpleadoComponent } from './pages/proyecto-rol-empleado/proyecto-rol-empleado.component';

// Tabla genérica
import { TablePageComponent } from '../../shared/components/table-page/table-page.component';

// INVENTARIO
import { ProductosComponent } from './pages/productos/productos.component';
import { ProveedoresComponent } from './pages/proveedores/proveedores.component';
import { ProveedoresProductosComponent } from './pages/proveedores-productos/proveedores-productos.component';

// COMPRAS
import { OrdenCompraListaComponent } from './pages/orden-compra-lista/orden-compra-lista.component';
import { CompraDetalleComponent } from './pages/compra-detalle/compra-detalle.component';
import { CompraEstadoComponent } from './pages/compra-estado/compra-estado.component';
import { CompraPagosComponent } from './pages/compra-pagos/compra-pagos.component';
import { MetodosPagoComponent } from './pages/metodos-pago/metodos-pago.component';

// VENTAS
import { VentasListaComponent } from './pages/ventas-lista/ventas-lista.component';
import { VentaDetalleComponent } from './pages/venta-detalle/venta-detalle.component';
import { TransaccionesComponent } from './pages/transacciones/transacciones.component';
import { EstadosTransaccionComponent } from './pages/estados-transaccion/estados-transaccion.component';
import { EstadoFacturaComponent } from './pages/estado-factura/estado-factura.component';

const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      // EMPLEADO
      {
        path: 'empleado/lista',
        component: EmpleadosListComponent,
        data: { title: 'Lista empleados' },
      },
      {
        path: 'empleado/tipo-contrato',
        component: TipoContratoComponent,
        data: { title: 'Tipo contrato' },
      },
      {
        path: 'empleado/carga',
        component: CargoComponent,
        data: { title: 'Carga laboral' },
      },
      {
        path: 'empleado/historico',
        component: HistoricoEmpleadoComponent,
        data: { title: 'Histórico' },
      },

      // DEPARTAMENTOS
      {
        path: 'departamentos',
        component: DepartamentosComponent,
        data: { title: 'Departamentos' },
      },

      // CLIENTE
      {
        path: 'cliente/tipo',
        component: TipoClienteComponent,
        data: { title: 'Tipo cliente' },
      },
      {
        path: 'cliente/lista',
        component: ClientesListComponent,
        data: { title: 'Lista cliente' },
      },

      // PROYECTOS
      {
        path: 'proyectos/lista',
        component: ProyectosListComponent,
        data: { title: 'Proyectos' },
      },
      {
        path: 'proyectos/:id/info',
        component: ProyectoInfoComponent,
        data: { title: 'Info proyecto' },
      },
      {
        path: 'proyectos/:id/empleados',
        component: ProyectoEmpleadosComponent,
        data: { title: 'Empleados del proyecto' },
      },
      {
        path: 'proyectos/:id/tipo',
        component: TablePageComponent,
        data: { title: 'Tipo proyecto', endpoint: 'proyecto_tipo' },
      },
      {
        path: 'proyectos/:id/rol-empleado',
        component: TablePageComponent,
        data: { title: 'Rol empleado', endpoint: 'proyecto_rol_empleado' },
      },
      {
        path: 'proyectos/tipo',
        component: ProyectoTipoComponent,
        data: { title: 'Tipo proyecto' },
      },
      {
        path: 'proyectos/rol-empleado',
        component: ProyectoRolEmpleadoComponent,
        data: { title: 'Rol empleado' },
      },

      // INVENTARIO
      {
        path: 'inventario/productos',
        component: ProductosComponent,
        data: { title: 'Productos' },
      },
      {
        path: 'inventario/proveedores',
        component: ProveedoresComponent,
        data: { title: 'Proveedores' },
      },
      {
        path: 'inventario/proveedores-productos',
        component: ProveedoresProductosComponent,
        data: { title: 'Proveedores de productos' },
      },

      // COMPRAS

      {
        path: 'orden-compra/lista',
        component: OrdenCompraListaComponent,
        data: { title: 'Lista orden de compra' },
      },
      {
        path: 'compras/detalle',
        component: CompraDetalleComponent,
        data: { title: 'Detalle compra' },
      },
      {
        path: 'compras/estado',
        component: CompraEstadoComponent,
        data: { title: 'Estado compra' },
      },
      {
        path: 'compras/pagos',
        component: CompraPagosComponent,
        data: { title: 'Pagos (compras)' },
      },
      {
        path: 'compras/metodos-pago',
        component: MetodosPagoComponent,
        data: { title: 'Métodos de pago' },
      },
      
  {
  path: 'factura-compra',
  loadComponent: () =>
    import('./pages/factura-compra/factura-compra-list.component')
      .then(m => m.FacturaCompraListComponent)
 },

      // VENTAS
      {
        path: 'ventas/lista',
        component: VentasListaComponent,
        data: { title: 'Ventas' },
      },
      {
        path: 'ventas/detalle',
        component: VentaDetalleComponent,
        data: { title: 'Detalle ventas' },
      },
      {
        path: 'ventas/transacciones',
        component: TransaccionesComponent,
        data: { title: 'Transacciones' },
      },
      {
        path: 'ventas/estados-transaccion',
        component: EstadosTransaccionComponent,
        data: { title: 'Estados de transacciones' },
      },
      {
        path: 'ventas/metodos-pago',
        component: MetodosPagoComponent,
        data: { title: 'Métodos de pago' },
      },
      {
        path: 'ventas/estado-factura',
        component: EstadoFacturaComponent,
        data: { title: 'Estado factura' },
      },

      // ESTADÍSTICAS
      {
        path: 'estadisticas/empleados',
        loadComponent: () =>
          import('./pages/estadisticas/empleados-stats/empleados-stats.component')
            .then(m => m.EmpleadosStatsComponent)
      },
      {
        path: 'estadisticas/departamentos',
        loadComponent: () =>
          import('./pages/estadisticas/departamentos-stats/departamentos-stats.component')
            .then(m => m.DepartamentosStatsComponent)
      },
      {
        path: 'estadisticas/productos',
        loadComponent: () =>
          import('./pages/estadisticas/productos-stats/productos-stats.component')
            .then(m => m.ProductosStatsComponent)
      },
      {
        path: 'estadisticas/proveedores',
        loadComponent: () =>
          import('./pages/estadisticas/proveedores-stats/proveedores-stats.component')
            .then(m => m.ProveedoresStatsComponent)
      },
      {
        path: 'estadisticas/ventas',
        loadComponent: () =>
          import('./pages/estadisticas/ventas-stats/ventas-stats.component')
            .then(m => m.VentasStatsComponent)
      },
      {
        path: 'estadisticas/proyectos',
        loadComponent: () =>
          import('./pages/estadisticas/proyectos-stats/proyectos-stats.component')
            .then(m => m.ProyectosStatsComponent)
      },
      
      // DEFAULT
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'empleado/lista',
      },
    ],
  },
];

export default routes;
