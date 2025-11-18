import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Proveedor {
  idProveedor: number;
  rut: string;
  nombreComercial: string;
  telefono: string;
  correo: string;
  direccion: string;
  ciudad: string;
  pais: string;
  categoria: string;
  calificacion: number;
  estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProveedorService {
  private baseUrl = `${environment.apiUrl}/proveedores`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(this.baseUrl);
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
