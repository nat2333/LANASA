import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Empleado {
  id: number;
  cedula: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  correo: string;
  fechaNacimiento: string;       // o Date si luego lo parseas
  direccion: string;
  ciudad: string;
  pais: string;
  fechaIngreso: string;
  salario: number;
  estado: boolean;
  idCargo: number;
  idTipoContrato: number;
  idDepartamento: number;
  nombreCargo: string;
  nombreTipoContrato: string;
  nombreDepartamento: string;
  telefono?: string;             // opcional, por si luego lo agregas
}

@Injectable({ providedIn: 'root' })
export class EmpleadoService {
  private base = `${environment.apiUrl}/empleados`; 

  constructor(private http: HttpClient) {}

  getAll(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.base);
  }
  getById(id: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.base}/${id}`);
  }
  create(data: Partial<Empleado>): Observable<Empleado> {
    return this.http.post<Empleado>(this.base, data);
  }
  update(id: number, data: Partial<Empleado>): Observable<Empleado> {
    return this.http.put<Empleado>(`${this.base}/${id}`, data);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  getByDepartamento(idDepartamento: number): Observable<Empleado[]> {
  return this.http.get<Empleado[]>(`${this.base}/departamento/${idDepartamento}`);
}

}
