import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface TipoCliente {
  id_tipo_cliente: number;
  tipo: string;
  estado: number; // 1 activo, 0 inactivo
}

@Injectable({ providedIn: 'root' })
export class TipoClienteService {
  private base = `${environment.apiUrl}/tipo_cliente`;

  constructor(private http: HttpClient) {}

  getAll(opts?: { page?: number; size?: number; sort?: string }): Observable<TipoCliente[]> {
    let params = new HttpParams();
    if (opts?.page !== undefined) params = params.set('page', String(opts.page));
    if (opts?.size !== undefined) params = params.set('size', String(opts.size));
    if (opts?.sort) params = params.set('sort', opts.sort);
    return this.http.get<TipoCliente[]>(this.base, { params });
  }

  getById(id: number): Observable<TipoCliente> {
    return this.http.get<TipoCliente>(`${this.base}/${id}`);
  }

  create(data: Partial<TipoCliente>): Observable<TipoCliente> {
    return this.http.post<TipoCliente>(this.base, data);
  }

  update(id: number, data: Partial<TipoCliente>): Observable<TipoCliente> {
    return this.http.put<TipoCliente>(`${this.base}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
