import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface ProveedorProducto {
  idProveedorProducto: number;
  idProducto: number;
  skuProducto: string;
  nombreProducto: string;
  idProveedor: number;
  rutProveedor: string;
  nombreComercialProveedor: string;
  calificacion: number;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProveedorProductoService {
  private baseUrl = `${environment.apiUrl}/proveedor-producto`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProveedorProducto[]> {
    return this.http.get<ProveedorProducto[]>(this.baseUrl);
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
