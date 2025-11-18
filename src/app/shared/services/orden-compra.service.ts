import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface OrdenCompra {
  idOrdenCompra: number;
  numero: string;
  fechaOrden: string;
  fechaEntregaEsperada: string | null;
  fechaEntregaReal: string | null;
  idProyecto: number | null;
  nombreComercialProveedor: string;
  nombreEstadoCompra: string;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class OrdenCompraService {
  private baseUrl = `${environment.apiUrl}/orden-compra`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<OrdenCompra[]> {
    return this.http.get<OrdenCompra[]>(this.baseUrl);
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
