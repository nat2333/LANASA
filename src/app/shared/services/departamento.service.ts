import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Departamento {
  id_departamento: number;
  nombre: string;
  estado: number; // 1 activo, 0 inactivo
}

@Injectable({ providedIn: 'root' })
export class DepartamentoService {
  private readonly base = `${environment.apiUrl}/departamento`;
  constructor(private http: HttpClient) {}
  getAll(): Observable<Departamento[]> { return this.http.get<Departamento[]>(this.base); }
  getById(id: number): Observable<Departamento> { return this.http.get<Departamento>(`${this.base}/${id}`); }
  create(data: Partial<Departamento>) { return this.http.post<Departamento>(this.base, data); }
  update(id: number, data: Partial<Departamento>) { return this.http.put<Departamento>(`${this.base}/${id}`, data); }
  delete(id: number) { return this.http.delete<void>(`${this.base}/${id}`); }
}
