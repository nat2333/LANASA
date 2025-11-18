import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface EstadoCompra {
  id: number;
  estadoCompra: string;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class CompraEstadoService {
  private baseUrl = `${environment.apiUrl}/estado-compra`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<EstadoCompra[]> {
    return this.http.get<EstadoCompra[]>(this.baseUrl);
  }

  create(body: any) {
    return this.http.post(this.baseUrl, body);
  }

  update(id: number, body: any) {
    return this.http.put(`${this.baseUrl}/${id}`, body);
  }

  delete(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  toggleEstado(id: number) {
    return this.http.post(`${this.baseUrl}/${id}/estado`, {});
  }
}
