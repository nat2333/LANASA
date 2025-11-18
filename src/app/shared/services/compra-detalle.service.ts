import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface DetalleCompra {
  idDetalleOrdenCompra: number;
  idOrdenCompra: number;
  numeroOrden: string;
  idProducto: number;
  skuProducto: string;
  nombreProducto: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class CompraDetalleService {
  private baseUrl = `${environment.apiUrl}/detalle-orden-compra`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<DetalleCompra[]> {
    return this.http.get<DetalleCompra[]>(this.baseUrl);
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
