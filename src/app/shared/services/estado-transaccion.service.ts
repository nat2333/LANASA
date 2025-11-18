import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface EstadoTransaccion {
  id: number;
  nombre: string;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class EstadoTransaccionService {
  private baseUrl = `${environment.apiUrl}/estado-transaccion`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<EstadoTransaccion[]> {
    return this.http.get<EstadoTransaccion[]>(this.baseUrl);
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
