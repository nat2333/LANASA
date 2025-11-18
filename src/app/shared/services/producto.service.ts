import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Producto {
  idProducto: number;
  sku: string;
  nombre: string;
  descripcion: string;
  categoria: string;
  precioCompra: number;
  precioVentaSugerido: number;
  stockMinimo: number;
  stockActual: number;
  stockMaximo: number;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private baseUrl = `${environment.apiUrl}/productos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl);
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
