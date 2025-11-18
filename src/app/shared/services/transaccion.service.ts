import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Transaccion {
  idTransaccion: number;
  idFacturaVenta: number;
  numeroFactura: string;
  idMetodoPago: number;
  metodoPago: string;
  idEstadoTransaccion: number;
  estadoTransaccion: string;
  valor: number;
  fechaHora: string;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class TransaccionService {
  private baseUrl = `${environment.apiUrl}/transacciones`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Transaccion[]> {
    return this.http.get<Transaccion[]>(this.baseUrl);
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
