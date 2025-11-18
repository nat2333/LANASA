import { inject } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService, Role } from './auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const need = route.data?.['role'] as Role | Role[] | undefined;

  if (auth.isLoggedIn() && (!need || auth.hasRole(need))) return true;

  // Redirige por rol si está logueado, o al login si no.
  const r = auth.getRole();
  if (r === 'admin') router.navigate(['/admin']);
  else if (r === 'employee') router.navigate(['/employee']);
  else router.navigate(['/auth/login']);
  return false;
};

