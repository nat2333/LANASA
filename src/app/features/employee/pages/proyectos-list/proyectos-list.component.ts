import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  ProyectoService,
  ProyectoEstadoDepartamento
} from '../../../../shared/services/proyecto.service';
import { AuthService } from '../../../../shared/services/auth.service';

@Component({
  selector: 'app-employee-proyectos-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './proyectos-list.component.html',
  styleUrls: ['./proyectos-list.component.scss']
})
export class ProyectosListComponent implements OnInit {

  private auth = inject(AuthService);
  private proyectosApi = inject(ProyectoService);

  proyectos: ProyectoEstadoDepartamento[] = [];
  loading = false;
  error: string | null = null;

  ngOnInit(): void {
    const userId = this.auth.getUserId();
    const role   = this.auth.getRole();

    console.log('[ProyectosDepto] init - userId:', userId, 'role:', role);

    const deptoId = this.mapUserToDepto(userId);
    console.log('[ProyectosDepto] deptoId:', deptoId);

    if (deptoId != null) {
      this.cargarEstadoProyectos(deptoId);
    } else {
      this.error = 'No se pudo determinar el departamento del usuario.';
    }
    
  }

  private cargarEstadoProyectos(idDepto: number) {
    this.loading = true;
    this.error = null;

    this.proyectosApi.getEstadoProyectosDepartamento(idDepto)
      .subscribe({
        next: (lista) => {
          console.log('[ProyectosDepto] respuesta:', lista);
          this.proyectos = lista ?? [];
          this.loading = false;
        },
        error: (e) => {
          console.error('[ProyectosDepto] error:', e);
          this.error = 'No se pudieron cargar los proyectos del departamento.';
          this.proyectos = [];
          this.loading = false;
        }
      });
  }

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

  getPresupuestoLabel(p: ProyectoEstadoDepartamento): string {
    switch (p.estadoPresupuesto) {
      case 'EN_PRESUPUESTO':    return 'En presupuesto';
      case 'SOBREPRESUPUESTO':  return 'Sobre presupuesto';
      case 'SIN_EJECUCION':     return 'Sin ejecución';
      default:                  return p.estadoPresupuesto;
    }
  }

  getEntregaLabel(p: ProyectoEstadoDepartamento): string {
    switch (p.estadoEntrega) {
      case 'EN_CURSO':           return 'En curso';
      case 'A_TIEMPO':           return 'A tiempo';
      case 'CON_RETRASO':        return 'Con retraso';
      case 'SIN_FECHA_ESTIMADA': return 'Sin fecha estimada';
      default:                   return p.estadoEntrega;
    }
  }
}
