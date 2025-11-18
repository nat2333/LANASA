import { Routes } from '@angular/router';
import { authGuard } from './shared/guards/auth.guard';
import { roleGuard } from './shared/guards/role.guard';

export default [
  { path: 'auth', loadChildren: () => import('./features/auth/auth.routes') },

  {
    path: 'admin',
    canActivate: [authGuard, roleGuard(['admin'])],
    loadChildren: () => import('./features/admin/admin.routes'),
  },
  {
    path: 'employee',
    canActivate: [authGuard, roleGuard(['employee','admin'])],
    loadChildren: () => import('./features/employee/employee.routes'),
  },

  { path: '', pathMatch: 'full', redirectTo: 'auth/login' },
  { path: '**', redirectTo: 'auth/login' },
] as Routes;
