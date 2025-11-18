import { Routes } from '@angular/router';
import { EmployeeLayoutComponent } from './layout/employee-layout.component';

// Páginas de employee
import { EmpleadoInfoComponent } from './pages/empleado-info/empleado-info.component';
import { ProyectosListComponent } from './pages/proyectos-list/proyectos-list.component';

// Páginas del proyecto (detalle)
import { ProyectoInfoComponent } from './pages/proyecto/pages/proyecto-info/proyecto-info.component';

// Reutilizamos componentes de ADMIN
import { ProyectoTipoComponent } from '../admin/pages/proyecto-tipo/proyecto-tipo.component';
import { ProyectoRolEmpleadoComponent } from '../admin/pages/proyecto-rol-empleado/proyecto-rol-empleado.component';

import { TablePageComponent } from '../../shared/components/table-page/table-page.component';

const routes: Routes = [
  {
    path: '',
    component: EmployeeLayoutComponent,
    children: [
      /** ===============================
       *   INFORMACIÓN DEL EMPLEADO
       *  =============================== */
      {
        path: 'info',
        component: EmpleadoInfoComponent,
        data: { title: 'Información del empleado' },
      },

      /** ===============================
       *   PROYECTOS DEL EMPLEADO
       *  =============================== */
      {
        path: 'proyectos/lista',
        component: ProyectosListComponent,
        data: { title: 'Mis proyectos' },
      },
      {
        path: 'proyectos/tipo',
        component: ProyectoTipoComponent,
        data: { title: 'Tipos de proyecto' },
      },
      {
        path: 'proyectos/rol-empleado',
        component: ProyectoRolEmpleadoComponent,
        data: { title: 'Roles de empleado' },
      },

      /** ===============================
       *   DETALLES DEL PROYECTO
       *  =============================== */
      {
        path: 'proyecto/:id/info',
        component: ProyectoInfoComponent,
        data: { title: 'Información del proyecto' },
      },

      /** ===============================
       *   TABLA GENÉRICA — DEPARTAMENTOS
       *  =============================== */
      {
        path: 'departamentos',
        component: TablePageComponent,
        data: { title: 'Departamentos', endpoint: 'departamentos' },
      },

      /** DEFAULT */
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'info',
      },
    ],
  },
];

export default routes;
