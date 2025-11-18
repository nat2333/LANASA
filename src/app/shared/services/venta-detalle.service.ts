import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface DetalleFacturaVenta {
  idDetalleFacturaVenta: number;
  idFacturaVenta: number;
  idProducto: number;
  skuProducto: string;
  nombreProducto: string;
  cantidad: number;
  precioUnitario: number;
  subtotalLinea: number;
  tipo: string | null;
  estado: boolean
}

@Injectable({ providedIn: 'root' })
export class VentaDetalleService {
  private baseUrl = `${environment.apiUrl}/detalles-venta`;

  constructor(private http: HttpClient) {}


  getByFactura(idFacturaVenta: number): Observable<DetalleFacturaVenta[]> {
  return this.http.get<DetalleFacturaVenta[]>(
    `${this.baseUrl}/factura/${idFacturaVenta}`
  );
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
