import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Venta {
  idFacturaVenta: number;
  numero: string;
  fechaFacturaVenta: string;
  subtotal: number;
  impuestos: number;
  total: number;
  estado: boolean;
  idCliente: number;
  correoCliente: string;
  idProyecto?: number;
  codigoProyecto?: string;
  idEstadoFactura?: number;
  nombreEstadoFactura?: string;
}

@Injectable({ providedIn: 'root' })
export class VentasService {
  private baseUrl = `${environment.apiUrl}/ventas`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.baseUrl);
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
