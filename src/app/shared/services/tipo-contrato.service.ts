import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface TipoContrato {
  id_tipo_contrato: number;
  tipo: string;
  estado: number; // 1 activo, 0 inactivo
}

@Injectable({ providedIn: 'root' })
export class TipoContratoService {
  private base = `${environment.apiUrl}/tipo_contrato`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<TipoContrato[]> {
    return this.http.get<TipoContrato[]>(this.base);
  }

  getById(id: number): Observable<TipoContrato> {
    return this.http.get<TipoContrato>(`${this.base}/${id}`);
  }

  create(data: Partial<TipoContrato>): Observable<TipoContrato> {
    return this.http.post<TipoContrato>(this.base, data);
  }

  update(id: number, data: Partial<TipoContrato>): Observable<TipoContrato> {
    return this.http.put<TipoContrato>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
