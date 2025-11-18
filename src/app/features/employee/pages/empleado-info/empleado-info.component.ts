import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmpleadoService, Empleado } from '../../../../shared/services/empleado.service';
import { AuthService } from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-empleado-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './empleado-info.component.html',
  styleUrls: ['./empleado-info.component.scss']
})
export class EmpleadoInfoComponent implements OnInit {

  private api  = inject(EmpleadoService);
  private auth = inject(AuthService);

  empleados: Empleado[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit(): void {
    const userId = this.auth.getUserId();
    const role   = this.auth.getRole();

    console.log('[EmpleadoInfo] init - userId:', userId, 'role:', role);

    // Si es admin, ve todos los empleados
    if (role === 'admin') {
      this.cargarTodos();
      return;
    }

    // Si es jefe de departamento: mapeamos id_usuario -> id_departamento
    const deptoId = this.mapUserToDepto(userId);
    console.log('[EmpleadoInfo] deptoId calculado:', deptoId);

    if (deptoId != null) {
      this.cargarPorDepartamento(deptoId);
    } else {
      this.error = 'No se pudo determinar el departamento del usuario.';
    }
  }

  /** Cargar TODOS los empleados (solo admins) */
  private cargarTodos() {
    this.loading = true;
    this.error = null;

    this.api.getAll().subscribe({
      next: (lista) => {
        console.log('[EmpleadoInfo] respuesta TODOS:', lista);
        this.empleados = lista ?? [];
        this.loading = false;
      },
      error: (e) => {
        console.error('[EmpleadoInfo] error TODOS:', e);
        this.error = 'No se pudieron cargar los empleados.';
        this.empleados = [];
        this.loading = false;
      }
    });
  }

  /** Cargar empleados de un departamento concreto */
  private cargarPorDepartamento(idDepto: number) {
    this.loading = true;
    this.error = null;

    this.api.getByDepartamento(idDepto).subscribe({
      next: (lista) => {
        console.log('[EmpleadoInfo] respuesta depto', idDepto, ':', lista);
        this.empleados = lista ?? [];
        this.loading = false;
      },
      error: (e) => {
        console.error('[EmpleadoInfo] error depto', idDepto, ':', e);
        this.error = 'No se pudieron cargar los empleados del departamento.';
        this.empleados = [];
        this.loading = false;
      }
    });
  }

  /** Hardcode: userId -> idDepartamento */
  private mapUserToDepto(userId: number | null): number | null {
    if (userId == null) return null;

    const mapping: Record<number, number> = {
      2: 1, // jefe_dev         -> Desarrollo de Software
      3: 2, // jefe_cloud       -> Soluciones Cloud
      4: 3, // jefe_consultoria -> Consultoría IT
      5: 4, // jefe_soporte     -> Soporte y Mantenimiento
      6: 5  // jefe_infra       -> Infraestructura y Redes
    };

    return mapping[userId] ?? null;
  }

  fullName(e: Empleado): string {
    return [
      e.primerNombre,
      e.segundoNombre,
      e.primerApellido,
      e.segundoApellido
    ].filter(Boolean).join(' ');
  }
}
