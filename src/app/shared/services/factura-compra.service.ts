import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

/** Coincide con FacturaCompraDTO del backend */
export interface FacturaCompra {
  idFacturaCompra: number;
  numero: string;
  fechaFactura: string;        // LocalDateTime -> ISO string
  montoTotal: number;
  estado: boolean;
  numeroOrden: string;
  nombreEstadoFactura: string;
}

/** Coincide con CrearFacturaCompraRequest */
export interface CrearFacturaCompraRequest {
  idOrdenCompra: number;
  montoTotal: number;
  idEstadoFactura: number;
}

/** Coincide con ActualizarFacturaCompraRequest */
export interface ActualizarFacturaCompraRequest {
  idEstadoFactura: number;
}

@Injectable({ providedIn: 'root' })
export class FacturaCompraService {
  private readonly base = `${environment.apiUrl}/facturas-compra`;

  constructor(private http: HttpClient) {}

  listar(): Observable<FacturaCompra[]> {
    return this.http.get<FacturaCompra[]>(this.base);
  }

  obtener(id: number): Observable<FacturaCompra> {
    return this.http.get<FacturaCompra>(`${this.base}/${id}`);
  }

  crear(payload: CrearFacturaCompraRequest): Observable<FacturaCompra> {
    return this.http.post<FacturaCompra>(this.base, payload);
  }

  actualizar(id: number, payload: ActualizarFacturaCompraRequest): Observable<FacturaCompra> {
    return this.http.put<FacturaCompra>(`${this.base}/actualizar/${id}`, payload);
  }

  cambiarEstado(id: number): Observable<FacturaCompra> {
    return this.http.post<FacturaCompra>(`${this.base}/${id}/estado`, {});
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
