import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface PagoCompra {
  idPago: number;
  numeroFactura: string;
  nombreMetodoPago: string;
  fechaPago: string;
  monto: number;
  estado: boolean;
  // opcionales para edición en el front:
  idFacturaCompra?: number;
  idMetodoPago?: number;
}

@Injectable({ providedIn: 'root' })
export class CompraPagosService {
  private baseUrl = `${environment.apiUrl}/pagos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<PagoCompra[]> {
    return this.http.get<PagoCompra[]>(this.baseUrl);
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
