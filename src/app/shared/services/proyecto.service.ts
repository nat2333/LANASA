import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Proyecto {
  id_proyecto?: number; // opcional
  nombre: string;
  descripcion?: string;
  fecha_inicio?: string;
  fecha_fin?: string;
  estado?: number;
}


export interface ProyectoEmpleado {
  id_proyecto: number;
  id_empleado: number;
  id_rol_empleado: number;
  // Campos opcionales que puede devolver tu API para pintar tablas:
  nombre?: string;   // nombre del empleado
  rol?: string;      // nombre del rol
}

export interface ProyectoEstadoDepartamento {
  idProyecto: number;
  codigo: string;
  nombreProyecto: string;
  nombreDepartamento: string;
  presupuestoAprobado: number;
  presupuestoUtilizado: number;
  diferenciaPresupuesto: number;
  estadoPresupuesto: string; // EN_PRESUPUESTO / SOBREPRESUPUESTO / SIN_EJECUCION
  estadoEntrega: string;     // EN_CURSO / A_TIEMPO / CON_RETRASO / SIN_FECHA_ESTIMADA
  diasRetraso: number | null;
}

export interface AddEmpleadoPayload {
  id_empleado: number;
  id_rol_empleado: number;
}

@Injectable({ providedIn: 'root' })
export class ProyectoService {
  private readonly base = `${environment.apiUrl}/proyectos`;

  constructor(private http: HttpClient) {}

  // CRUD Proyecto
  getAll(): Observable<Proyecto[]> {
    return this.http.get<Proyecto[]>(this.base);
  }

  getById(id: number): Observable<Proyecto> {
    return this.http.get<Proyecto>(`${this.base}/${id}`);
  }

  create(data: Partial<Proyecto>): Observable<Proyecto> {
    return this.http.post<Proyecto>(this.base, data);
  }

  update(id: number, data: Partial<Proyecto>): Observable<Proyecto> {
    return this.http.put<Proyecto>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

   toggleEstado(id: number) {
    return this.http.post(`${this.base}/${id}/estado`, {});
  }

  // ── Empleados por proyecto (endpoints anidados) ──────────────────────────────
  getEmpleados(id_proyecto: number): Observable<ProyectoEmpleado[]> {
    return this.http.get<ProyectoEmpleado[]>(`${this.base}/${id_proyecto}/empleados`);
  }

  addEmpleado(id_proyecto: number, data: AddEmpleadoPayload): Observable<void> {
    // el backend puede devolver 201/204; tipamos void para no forzar un cuerpo
    return this.http.post<void>(`${this.base}/${id_proyecto}/empleados`, data);
  }

  removeEmpleado(id_proyecto: number, id_empleado: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id_proyecto}/empleados/${id_empleado}`);
  }

   getEstadoProyectosDepartamento(idDepartamento: number)
    : Observable<ProyectoEstadoDepartamento[]> {
    const url = `${environment.apiUrl}/estadisticas/proyectos/departamento/${idDepartamento}`;
    return this.http.get<ProyectoEstadoDepartamento[]>(url);
  }
}
